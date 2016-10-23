package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop {
    public String id;
    public String code;
    public String name;
    public String direction;
    public double lat;
    public double lon;
    public Line lineThatThisStopServes;
    public Stop[] stopsThatThisStopSharesWith;
    public boolean favorite;
    public boolean isUCSDShuttleStop;
    public boolean wheelchairBoarding;

    public Stop(
            String id,
            boolean isUCSDShuttleStop) {
        this.id = id;
        if(isUCSDShuttleStop) {
            // somehow fill in instance variables
        }
        else {
            // fill in vairbles with MTS api
            RemoteFetch.fillStopInfo(this);
        }
    }

    public void switchFavorite() {
        favorite = !favorite;
    }
}
