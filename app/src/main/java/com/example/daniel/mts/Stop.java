package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Stop {
    private String name;    // name of the stop
    private long id;        // id of the stop
    private Line lineThatThisStopIsOn;
    private Stop[] stopsThatThisStopSharesWith;
    private boolean favorite;
    private boolean isUCSDShuttleStop;

    public Stop(
            long id,
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

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Line getLineThatThisStopIsOn() {
        return lineThatThisStopIsOn;
    }

    public Stop[] getStopsThatThisStopSharesWith() {
        return stopsThatThisStopSharesWith;
    }

    public boolean getfavorite() {
        return favorite;
    }
}
