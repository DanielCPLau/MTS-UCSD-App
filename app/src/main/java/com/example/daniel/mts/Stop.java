package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop implements Writable{
    public String id;
    public String code;
    public String name;
    public String direction;
    public double lat;
    public double lon;
    public String lineThatThisStopServesId;
    public String[] stopsThatThisStopSharesWithId;
    public boolean favorite;
    public boolean isUCSDShuttleStop;
    public boolean wheelchairBoarding;

    public Stop(String id) {
        this.id = id;
        RemoteFetch.fillStopInfo(this);
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
        return id;
    }

    public void fill() {
        RemoteFetch.fillStopInfo(this);
    }
}
