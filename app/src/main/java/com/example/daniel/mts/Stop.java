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
        if( ListOfLinesAndStopsIO.readable(this)) {
            fill(ListOfLinesAndStopsIO.readStop(getId()));
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
        this.lineThatThisStopServesId = stop.lineThatThisStopServesId;
        this.stopsThatThisStopSharesWithId = stop.stopsThatThisStopSharesWithId;
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
        return id;
    }
}
