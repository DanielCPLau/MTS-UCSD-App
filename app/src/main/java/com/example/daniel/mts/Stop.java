package com.example.daniel.mts;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop implements Writable {
    public String id;
    public String lineId;
    public String code;
    public String name = "";
    public String direction;
    public double lat;
    public double lon;
    public String[] otherLinesThatThisStopServes = new String[0];
    public boolean favorite = false;
    public boolean isUCSDShuttleStop = false;
    public boolean wheelchairBoarding;

    public Stop(String id, String lineId) {
        this.id = id;
        this.lineId = lineId;

        ListOfLinesAndStopsIO.read(this);
    }


    public void switchFavorite() {
        favorite = !favorite;
    }

    public String getWritePreflix() {
        return ListOfLinesAndStopsIO.FILENAME_STOP;
    }

    public boolean isFilled() {
        return !name.equals("");
    }

    public String getId() {
        return id + "_" + lineId;
    }

    public String getOfficalId() {
        return id;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        if(name.equals("")) {
            RemoteFetch.fillStopInfo(this);
        }

        try {
            obj.put("code", code);
            obj.put("name", name);
            obj.put("direction", direction);
            obj.put("lat", lat);
            obj.put("lon", lon);
            obj.put("favorite", favorite);
            obj.put("isUCSDShuttleStop", isUCSDShuttleStop);
            obj.put("wheelchairBoarding", wheelchairBoarding);

            JSONArray jarray = new JSONArray();
            for(int i = 0; i < otherLinesThatThisStopServes.length; i++) {
                jarray.put(otherLinesThatThisStopServes[i]);
            }

            obj.put("otherLinesThatThisStopServes", jarray);

        } catch (JSONException e) {
            Log.w("JSONException", "occured in Stop.getJSONObject()");
            e.printStackTrace();
        }

        return obj;
    }

    public void fillJSONObject(JSONObject obj) {
        try {
            this.code = obj.getString("code");
            this.name = obj.getString("name");
            this.direction = obj.getString("direction");
            this.lat = obj.getDouble("lat");
            this.lon = obj.getDouble("lon");
            this.favorite = obj.getBoolean("favorite");
            this.isUCSDShuttleStop = obj.getBoolean("isUCSDShuttleStop");
            this.wheelchairBoarding = obj.getBoolean("wheelchairBoarding");

            JSONArray jarray = obj.getJSONArray("otherLinesThatThisStopServes");
            otherLinesThatThisStopServes = new String[jarray.length()];

            for(int i = 0; i < jarray.length(); i++) {
                otherLinesThatThisStopServes[i] = jarray.getString(i);
            }

        } catch (JSONException e) {
            Log.w("JSONException", "occured in Line.fillJSONObject()");
            e.printStackTrace();
        }

    }
}
