package com.example.daniel.mts;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line extends LineInfo implements Writable {
    public String directionId = "0";            // direction of the line
    public String directionName = "";        // name of the direction
    public String[] listOfStopsId = new String[0];      // list of stops on this line
    public String oppositeDirectionId = "";  // Points to the line of the other direction;

    // eg) 30 to Downtown and 30 to UTC
    public Line(String id) {
        super(id);

        ListOfLinesAndStopsIO.read(this);
    }

    public Line(Line line) {
        this.id = line.id;
        this.agency = line.agency;
        this.shortName = line.shortName;
        this.longName = line.longName;
        this.color = line.color;
        this.textColor = line.textColor;
        this.favorite = line.favorite;
    }

    public String getWritePreflix() {
        return ListOfLinesAndStopsIO.FILENAME_LINE;
    }

    public boolean isFilled() {
        return !longName.equals("");
    }

    public String getId() {
        return id + "_" + directionId;
    }

    public String getOfficalId() {
        return id;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = super.getJSONObject();

        if( directionName.equals("")) {
            RemoteFetch.fillLineDetailInfo(this);
        }

        try {
            obj.put("directionId", directionId);
            obj.put("directionName", directionName);

            JSONArray jarray = new JSONArray();
            for(int i = 0; i < listOfStopsId.length; i++) {
                jarray.put(listOfStopsId[i]);
            }

            obj.put("listOfStopsId", jarray);
            obj.put("oppositeDirectionId", oppositeDirectionId);

        } catch (JSONException e) {
            Log.w("JSONException", "occured in Line.getJSONObject()");
            e.printStackTrace();
        }

        return obj;
    }

    public void fillJSONObject(JSONObject obj) {
        try {
            this.agency = obj.getString("agency");
            this.shortName = obj.getString("shortName");
            this.longName = obj.getString("longName");
            this.color = obj.getString("color");
            this.textColor = obj.getString("textColor");
            this.favorite = obj.getBoolean("favorite");
            this.directionId = obj.getString("directionId");
            this.directionName = obj.getString("directionName");

            JSONArray jarray = obj.getJSONArray("listOfStopsId");
            listOfStopsId = new String[jarray.length()];

            for(int i = 0; i < jarray.length(); i++) {
                listOfStopsId[i] = jarray.getString(i);
            }

            this.oppositeDirectionId = obj.getString("oppositeDirectionId");
        } catch (JSONException e) {
            Log.w("JSONException", "occured in Line.fillJSONObject()");
            e.printStackTrace();
        }
    }
}
