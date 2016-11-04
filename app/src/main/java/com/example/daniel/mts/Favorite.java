package com.example.daniel.mts;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Isaac on 11/3/16.
 */

class Favorite implements Comparable<Favorite> {
    public String stopId;
    public String lineId;
    public boolean favorite;

    public Favorite(String stopId, String lineId, boolean favorite) {
        this.stopId = stopId;
        this.lineId = lineId;
        this.favorite = favorite;

        ListOfLinesAndStopsIO.writeFavorite(this);
    }

    public Favorite(JSONObject jobj) {
        try {
            this.stopId = jobj.getString("stopID");
            this.lineId = jobj.getString("lineId");
            this.favorite = jobj.getBoolean("favorite");
        }
        catch (JSONException e) {
            // TODO
            e.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("stopId", stopId);
            jobj.put("lineId", lineId);
            jobj.put("favorite", favorite);
        }
        catch (JSONException e) {
            // TODO
            e.printStackTrace();
        }

        return jobj;
    }

    public boolean cancelsWith(Favorite f) {
        return (this.stopId.equals(f.stopId)) && (this.lineId.equals(f.lineId)) && (this.favorite != f.favorite);
    }

    @Override
    public int compareTo(Favorite f) {
        return this.stopId.compareTo(f.stopId);
    }

}

