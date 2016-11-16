package com.example.daniel.mts;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Created by Isaac on 10/22/16.
 */

public class RemoteFetch {
    private static Date date = new Date();

    private static long lastTime = date.getTime();

    private static final String API_KEY = "393bdfb5-b145-4149-a45e-067d8acb6246";

    private static final String REQUEST = "http://realtime.sdmts.com/api/api/where/%s.json?key=" + API_KEY;

    private static final String REQUEST_END_LOC = "&lat=%s&lon=%s";

    private static final String REQUEST_LIST_OF_ROUTE = "route-ids-for-agency/%S";  // Need to add agency name
    private static final String REQUEST_MTS = "MTS";
    private static final String REQUEST_NCTD = "NCTD";

    private static final String REQUEST_STOP_INFO = "stop/%S";                      // Need stop id

    private static final String REQUEST_ROUTE_INFO = "route/%S";                    // Need route id

    private static final String REQUEST_ROUTE_NEARBY = "routes-for-location";       // need lat and lon

    private static final String REQUEST_STOP_NEARBY = "stops-for-location";         // need lat and lon

    private static final String REQUEST_ROUTE_STOP_LIST = "stops-for-route/%S";     // need route id

    private static final String REQUEST_PREDICTION = "arrivals-and-departures-for-stop/%S"; // need stop id

    private static final int REQUEST_SUCCESS_CODE = 200;
    private static final String REQUEST_DATA = "data";
    private static final String REQUEST_LIST = "list";
    private static final String REQUEST_ENTRY = "entry";

    private static final int TIMER = 100; // Min is 80

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        Log.d("API Usage", url);

        long time =  date.getTime() - lastTime;
        if( time < TIMER) {
            try {
                Thread.sleep(TIMER - time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            lastTime = date.getTime();

            return new JSONObject(jsonText);
        }
        finally {
            is.close();
        }
    }

    public static Line[] getListOfAllLines() {
        try {
            Line[] mtsLines = getListOfLines(REQUEST_MTS);
            Line[] nctdLines = getListOfLines(REQUEST_NCTD);

            int length = mtsLines.length + nctdLines.length;
            Line[] allLines = new Line[length];

            System.arraycopy(mtsLines, 0, allLines, 0, mtsLines.length);
            System.arraycopy(nctdLines, 0, allLines, mtsLines.length, nctdLines.length);

            return allLines;
        }
        catch (NullPointerException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
    }

    public static Line[] getListOfLines(String agency) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_LIST_OF_ROUTE, agency)));
            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.getListOfLines()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_LIST_OF_ROUTE, agency)));
            }

            JSONArray list = json.getJSONObject(REQUEST_DATA).getJSONArray(REQUEST_LIST);

            Line[] line = new Line[list.length()];

            for(int i = 0; i < line.length; i++) {
                line[i] = new Line(list.getString(i));
                Thread.sleep(TIMER);
            }

            Arrays.sort(line);

            return line;
        }
        catch (IOException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
        catch (JSONException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
        catch (InterruptedException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
    }

    public static LineInfo[] getListOfAllLinesInfo() {
        try {
            LineInfo[] mtsLines = getListOfLinesInfo(REQUEST_MTS);
            LineInfo[] nctdLines = getListOfLinesInfo(REQUEST_NCTD);

            int length = mtsLines.length + nctdLines.length;
            LineInfo[] allLines = new LineInfo[length];

            System.arraycopy(mtsLines, 0, allLines, 0, mtsLines.length);
            System.arraycopy(nctdLines, 0, allLines, mtsLines.length, nctdLines.length);

            return allLines;
        }
        catch (NullPointerException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
    }

    public static LineInfo[] getListOfLinesInfo(String agency) {
        Log.i("Reading from API", "occured in RemoteFetch.getListOfLinesInfo()");
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_LIST_OF_ROUTE, agency)));
            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.getListOfLinesInfo()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_LIST_OF_ROUTE, agency)));
            }

            JSONArray list = json.getJSONObject(REQUEST_DATA).getJSONArray(REQUEST_LIST);

            LineInfo[] lineInfo = new LineInfo[list.length()];

            for(int i = 0; i < lineInfo.length; i++) {
                lineInfo[i] = new LineInfo(list.getString(i));
            }

            Arrays.sort(lineInfo);

            return lineInfo;
        }
        catch (IOException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
        catch (JSONException ex) {
            // TODO
            ex.printStackTrace();
            return null;
        }
    }

    public static void fillLineInfo(LineInfo line) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_INFO, line.id)));

            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                Log.w("API Request Fails", "occured in RemoteFetch.fillLineInfo()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_INFO, line.id)));
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            // populate basic info of Line from API
            line.agency = entry.getString("agencyId");
            line.shortName = entry.getString("shortName");
            line.longName = entry.getString("longName");
            line.color = entry.getString("color");
            line.textColor = entry.getString("textColor");

            if(line.shortName.equals("Orange Line")) {
                line.shortName = "O";
            }
            else if(line.shortName.equals("UC San Diego Blue Line")) {
                line.shortName = "B";
            }
            else if(line.shortName.equals("Green Line")) {
                line.shortName = "G";
            }
            else if(line.id.equals("MTS_COR")) {
                line.shortName = "COR";
            }
        }
        catch (IOException ex) {
            Log.i("IOException", "occured in RemoteFetch.fillLineInfo()");
            return;
        }
        catch (JSONException ex) {
            Log.i("JSONException", "occured in RemoteFetch.fillLineInfo()");
            return;
        }
    }

    public static void fillLineDetailInfo(Line line) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_STOP_LIST, line.id))+"&includePolylines=false");

            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.fillLineDetailInfo()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_STOP_LIST, line.id))+"&includePolylines=false");
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            JSONArray directions = entry.getJSONArray("stopGroupings").getJSONObject(0).getJSONArray("stopGroups");

            JSONObject dir = directions.getJSONObject(0);

            line.directionId = dir.getString("id");
            line.directionName = dir.getJSONObject("name").getString("name");

            JSONArray stops = dir.getJSONArray("stopIds");

            line.listOfStopsId = new String[stops.length()];

            for(int i = 0; i < stops.length(); i++) {
                line.listOfStopsId[i] = stops.getString(i);
            }

            if( directions.length() > 1) {
                dir = directions.getJSONObject(1);

                if(dir.getString("id").equals(line.directionId)) return;

                Line oppositeDirLine = new Line(line);

                oppositeDirLine.directionId = dir.getString("id");
                oppositeDirLine.directionName = dir.getJSONObject("name").getString("name");

                line.oppositeDirectionId = oppositeDirLine.directionId;
                oppositeDirLine.oppositeDirectionId = line.directionId;

                stops = dir.getJSONArray("stopIds");

                oppositeDirLine.listOfStopsId = new String[stops.length()];

                for(int i = 0; i < stops.length(); i++) {
                    oppositeDirLine.listOfStopsId[i] = stops.getString(i);
                }

                ListOfLinesAndStopsIO.write(oppositeDirLine);
            }

            // TODO
        }
        catch (IOException ex) {
            // TODO
            return;
        }
        catch (JSONException ex) {
            // TODO
            return;
        }
    }

    public static void fillStopInfo(Stop stop) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_STOP_INFO, stop.id)));

            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.fillStopInfo()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_STOP_INFO, stop.id)));
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            // poopulate basic info of bus stop from API
            stop.code = entry.getString("code");
            stop.lat = entry.getDouble("lat");
            stop.lon = entry.getDouble("lon");
            stop.name = entry.getString("name");
            stop.wheelchairBoarding = entry.getString("wheelchairBoarding").equals("ACCESSIBLE");

            JSONArray routeIds = entry.getJSONArray("routeIds");
            stop.otherLinesThatThisStopServes = new String[routeIds.length()];

            for(int i = 0; i < routeIds.length(); i++) {
                if(routeIds.getString(i) == stop.lineId) continue;

                stop.otherLinesThatThisStopServes[i] = routeIds.getString(i);
            }

            Line line = new Line(stop.lineId);
            if(!line.partOfLine(stop.id)) {
                line = line.getOppositeDirection();
            }

            stop.directionId = line.directionId;
            stop.directionName = line.directionName;
            stop.color = line.color;
            stop.lineShortName = line.shortName;

        }
        catch (IOException ex) {
            // TODO
            ex.printStackTrace();
            return;
        }
        catch (JSONException ex) {
            // TODO
            ex.printStackTrace();
            return;
        }
    }

    public static ArrayList<Stop> getStopsNearLoc(double lat, double lon, double rad) {
        ArrayList<Stop> stops= new ArrayList<Stop>();

        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, REQUEST_STOP_NEARBY) +
                    String.format(REQUEST_END_LOC, lat, lon) + "&radius=" + rad);

            while (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.getStopsNearLoc()");
                json = readJsonFromUrl(String.format(REQUEST, REQUEST_STOP_NEARBY) +
                        String.format(REQUEST_END_LOC, lat, lon) + "&radius=" + rad);
            }

            JSONArray list = json.getJSONObject(REQUEST_DATA).getJSONArray(REQUEST_LIST);

            for(int i = 0; i < list.length(); i++ ) {
                JSONArray routeIds = list.getJSONObject(i).getJSONArray("routeIds");

                for(int j = 0; j < routeIds.length(); j++) {
                    stops.add(new Stop(list.getJSONObject(i).getString("id"), routeIds.getString(j)));
                }
            }

            return stops;


        } catch (JSONException | IOException e) {
            e.printStackTrace();

            return stops;
        }
    }

    public static ArrayList<Integer> getPrediction (String stopOfficialId, String lineOfficalId, String directionName) {
        ArrayList<Integer> times = new ArrayList<Integer>();

        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_PREDICTION, stopOfficialId))+"&minutesBefore=0&minutesAfter=60");

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                Log.w("API Request Fails", "occured in RemoteFetch.getPrediction()");
                json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_PREDICTION, stopOfficialId))+"&minutesBefore=0&minutesAfter=60");
            }

            long currentTime = json.getLong("currentTime");

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);
            JSONArray arrivalsAndDepartures = entry.getJSONArray("arrivalsAndDepartures");

            for(int i = 0; i < arrivalsAndDepartures.length(); i++ ) {
                JSONObject time = arrivalsAndDepartures.getJSONObject(i);
                String routeId = time.getString("routeId");
                String dirName = time.getString("tripHeadsign");
                boolean departureEnabled = time.getBoolean("departureEnabled");

                if(!routeId.equals(lineOfficalId)) continue;
                if(!departureEnabled) continue;
                if(!directionName.equals(dirName) && !(routeId.equals("MTS_201") || routeId.equals("MTS_202")))continue;

                long predicted = time.getLong("predictedDepartureTime");

                int next = (int)(predicted - currentTime)/60000;

                if(next >= 0) {
                    times.add(next);
                }
            }

            return times;


        } catch (JSONException | IOException e) {
            e.printStackTrace();

            return times;
        }
    }
}
