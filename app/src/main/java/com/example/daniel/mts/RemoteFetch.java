package com.example.daniel.mts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

/**
 * Created by Isaac on 10/22/16.
 */

public class RemoteFetch {
    private static final String API_KEY = "393bdfb5-b145-4149-a45e-067d8acb6246";

    private static final String REQUEST = "http://realtime.sdmts.com/api/api/where/%s.json?key=" + API_KEY;

    private static final String REQUEST_END_LOC = "&lat=%s&lon=%s";

    private static final String REQUEST_LIST_OF_ROUTE = "route-ids-for-agency/%s";  // Need to add agency name
    private static final String REQUEST_MTS = "MTS";
    private static final String REQUEST_NCTD = "NCTD";

    private static final String REQUEST_LIST_OF_STOPS = "stop-ids-for-agency/%s";   // Might not need this

    private static final String REQUEST_STOP_INFO = "stop/%s";                      // Need stop id

    private static final String REQUEST_ROUTE_INFO = "route/%s";                    // Need route id;

    private static final String REQUEST_ROUTE_NEARBY = "routes-for-location";       // need lat and lon

    private static final String REQUEST_STOP_NEARBY = "stops-for-location";         // need lat and lon

    private static final String REQUEST_ROUTE_STOP_LIST = "stops-for-route/%s";     // need route id;

    private static final int REQUEST_SUCCESS_CODE = 200;
    private static final String REQUEST_DATA = "data";
    private static final String REQUEST_LIST = "list";
    private static final String REQUEST_ENTRY = "entry";

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
                line[i] = new Line(list.getString(i), false);
            }

            return line;
        }
        catch (IOException ex) {
            // TODO
            return null;
        }
        catch (JSONException ex) {
            // TODO
            return null;
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
            return null;
        }
    }

    public static void fillLineInfo(Line line) {
        try {
            JSONObject json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_INFO, line.id)));

            if (json.getInt("code") != REQUEST_SUCCESS_CODE) {
                // Request to API failed
                //TODO
                return;
            }

            JSONObject entry = json.getJSONObject(REQUEST_DATA).getJSONObject(REQUEST_ENTRY);

            // poopulate basic info of Line in API
            line.agency = entry.getString("agencyId");
            line.shortName = entry.getString("shortName");
            line.longName = entry.getString("longName");
            line.color = entry.getString("color");
            line.textColor = entry.getString("textColor");

            json = readJsonFromUrl(String.format(REQUEST, String.format(REQUEST_ROUTE_STOP_LIST, line.id)));
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
}
