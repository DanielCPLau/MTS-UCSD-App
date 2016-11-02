package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop implements Writable{
    public String id;
    public String lineId;
    public String code;
    public String name;
    public String direction;
    public double lat;
    public double lon;
    public String[] otherLinesThatThisStopServes;
    public boolean favorite = false;
    public boolean isUCSDShuttleStop = false;
    public boolean wheelchairBoarding;

    public Stop(String id, String lineId) {
        this.id = id;
        this.lineId = lineId;
        if( ListOfLinesAndStopsIO.readable(this)) {
            fill(ListOfLinesAndStopsIO.readStop(id, lineId));
        }
        else {
            RemoteFetch.fillStopInfo(this);
            ListOfLinesAndStopsIO.write(this);
        }
    }

    private void fill(Stop stop) {
        this.code = stop.code;
        this.name = stop.name;
        this.direction = stop.direction;
        this.lat = stop.lat;
        this.lon = stop.lon;
        this.lineId = stop.lineId;
        this.otherLinesThatThisStopServes = stop.otherLinesThatThisStopServes;
        this.favorite = stop.favorite;
        this.isUCSDShuttleStop = stop.isUCSDShuttleStop;
        this.wheelchairBoarding = stop.wheelchairBoarding;
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
}
