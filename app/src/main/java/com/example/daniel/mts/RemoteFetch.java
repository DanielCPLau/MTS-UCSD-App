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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Created by Isaac on 10/22/16.
 */

public class RemoteFetch {
    private static final String API_KEY = "393bdfb5-b145-4149-a45e-067d8acb6246";

    private static final String REQUEST = "http://realtime.sdmts.com/api/api/where/%s.json?key=" + API_KEY;

    private static final String REQUEST_END_LOC = "&lat=%s&lon=%s";

    private static final String REQUEST_LIST_OF_ROUTE = "route-ids-for-agency/%S";  // Need to add agency name
    private static final String REQUEST_MTS = "MTS";
    private static final String REQUEST_NCTD = "NCTD";

    private static final String REQUEST_LIST_OF_STOPS = "stop-ids-for-agency/%S";   // Might not need this

    private static final String REQUEST_STOP_INFO = "stop/%S";                      // Need stop id

    private static final String REQUEST_ROUTE_INFO = "route/%S";                    // Need route id;

    private static final String REQUEST_ROUTE_NEARBY = "routes-for-location";       // need lat and lon

    private static final String REQUEST_STOP_NEARBY = "stops-for-location";         // need lat and lon

    private static final String REQUEST_ROUTE_STOP_LIST = "stops-for-route/%S";     // need route id;

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
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

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
            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return null;
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
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_LIST_OF_ROUTE, agency)));
            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return null;
            }

            JSONArray list = json.getJSONObject(REQUEST_DATA).getJSONArray(REQUEST_LIST);

            LineInfo[] lineInfo = new LineInfo[list.length()];

            for(int i = 0; i < lineInfo.length; i++) {
                Thread.sleep(TIMER);
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
        catch (InterruptedException ex) {
            // TODO
            return null;
        }
    }

    public static void fillLineInfo(LineInfo line) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_INFO, line.id)));

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return;
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
            // TODO
            return;
        }
        catch (JSONException ex) {
            // TODO
            return;
        }
    }

    public static void fillLineDetailInfo(Line line) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_STOP_LIST, line.id))+"&includePolylines=false");

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return;
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            JSONArray directions = entry.getJSONArray("stopGroupings").getJSONObject(0).getJSONArray("stopGroups");

            JSONObject dir = directions.getJSONObject(0);

            line.directionId = dir.getString("id");

            line.directionName = dir.getJSONObject("name").getString("name");

            JSONArray stops = dir.getJSONArray("stopIds");

            String[] stopsArray = new String[stops.length()];

            for(int i = 0; i < stops.length(); i++) {
                stopsArray[i] = stops.getString(i);
            }

            line.listOfStopsId = stopsArray;

            if( directions.length() > 1) {

                dir = directions.getJSONObject(1);

                Line oppositeDirLine = new Line(line.id);

                oppositeDirLine.directionId = dir.getString("id");
                oppositeDirLine.directionName = dir.getJSONObject("name").getString("name");

                line.oppositeDirectionId = oppositeDirLine.directionId;
                oppositeDirLine.oppositeDirectionId = line.directionId;

                stops = dir.getJSONArray("stopIds");

                String[] stopsArray2 = new String[stops.length()];

                for(int i = 0; i < stops.length(); i++) {
                    stopsArray2[i] = stops.getString(i);
                }

                oppositeDirLine.listOfStopsId = stopsArray2;
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

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return;
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            // poopulate basic info of bus stop from API
            stop.code = entry.getString("code");
            stop.direction = entry.getString("direction");
            stop.lat = entry.getInt("lat");
            stop.lon = entry.getInt("lon");
            stop.name = entry.getString("name");
            stop.wheelchairBoarding = entry.getString("wheelchairBoarding").equals("ACCESSIBLE");

            // TODO
            // get line list
            // get stops nearby
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

    public static Stop[] getStopsNearLoc(int lat, int lon) {
        Stop[] stops;

        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, REQUEST_STOP_NEARBY) +
                    String.format(REQUEST_END_LOC, lat, lon) );

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                stops = new Stop[0];
                return stops;
            }

            JSONArray list = json.getJSONObject(REQUEST_DATA).getJSONArray(REQUEST_LIST);

            stops = new Stop[list.length()];

            for(int i = 0; i < list.length(); i++ ) {
                stops[i] = new Stop(list.getJSONObject(i).getString("id"));
            }

            return stops;


        } catch (JSONException | IOException e) {
            e.printStackTrace();

            stops = new Stop[0];
            return stops;
        }
    }
}

        //Testing data retrieval from mts api
//        Button btnHit = (Button)findViewById(R.id.btnHit);
//        mtsInfo = (TextView) findViewById(R.id.mts);
//        if(mtsInfo == null)
//        {
//            Log.d("nullTest","MTSINFO IS NULL");
//        }
//        btnHit.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                new JSONTask().execute("http://realtime.sdmts.com/api/api/where/route-ids-for-agency/MTS.json?key=393bdfb5-b145-4149-a45e-067d8acb6246");
//            }
//        });



//public class JSONTask extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuffer buffer = new StringBuffer();
//                String line ="";
//
//                while ((line = reader.readLine()) != null){
//                    buffer.append(line);
//                }
//
//                String finalJson= buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONObject childObject = (JSONObject)parentObject.get("data");
//                JSONArray parentArray = childObject.getJSONArray("list");
//
//                ArrayList<String> busList = new ArrayList<String>();
//                if (parentArray != null) {
//                    int len = parentArray.length();
//                    for (int i=0;i<len;i++){
//                        busList.add(parentArray.get(i).toString());
//                    }
//                }
//
//                String busNum = busList.get(0);
//
////                return busNum;
//                return busList.toString();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if(connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if(reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        if(mtsInfo != null) {
//            mtsInfo.setText(result);
//        }
//    }
//}


