package com.example.daniel.mts;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Isaac on 10/22/16.
 */

public class LineInfo implements Comparable<LineInfo> {
    public String agency;
    public String id;
    public String shortName;
    public String longName;
    public String color = "";
    public String textColor;
    public boolean favorite = false;

    public LineInfo () {
    }

    public LineInfo (String id) {
        this.id = id;
        RemoteFetch.fillLineInfo(this);
    }

    public LineInfo (JSONObject jobj) {
        try {
            this.agency = jobj.getString("agency");
            this.id = jobj.getString("id");
            this.shortName = jobj.getString("shortName");
            this.longName = jobj.getString("longName");
            this.color = jobj.getString("color");
            this.textColor = jobj.getString("textColor");
            this.favorite = jobj.getBoolean("favorite");
        } catch (JSONException e) {
            Log.w("JSONException", "occured in LineInfo(JSONObject jobj)");
            e.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        if( color.equals("")) {
            RemoteFetch.fillLineInfo(this);
        }

        try {
            obj.put("id", id);
            obj.put("agency", agency);
            obj.put("shortName", shortName);
            obj.put("longName", longName);
            obj.put("color", color);
            obj.put("textColor", textColor);
            obj.put("favorite", favorite);

        } catch (JSONException e) {
            Log.w("JSONException", "occured in LineInfo.getJSONObject()");
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public int compareTo(LineInfo o) {
        return this.shortName.hashCode() - o.shortName.hashCode();
    }

    public void switchFavorite() {
        favorite = !favorite;
    }
}
