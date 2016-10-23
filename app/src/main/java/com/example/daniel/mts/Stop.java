package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop {
    public String name;    // name of the stop
    public String id;        // id of the stop
    public Line lineThatThisStopIsOn;
    public Stop[] stopsThatThisStopSharesWith;
    public boolean favorite;
    public boolean isUCSDShuttleStop;

    public Stop(
            String id,
            boolean isUCSDShuttleStop) {
        this.id = id;
        if(isUCSDShuttleStop) {
            // somehow fill in instance variables
        }
        else {
            // fill in vairbles with MTS api
        }
    }

    public void switchFavorite() {
        favorite = !favorite;
    }
}
