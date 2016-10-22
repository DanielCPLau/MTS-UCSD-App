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

    public Stop(
            String name,
            long id,
            Line lineThatThisStopIsOn,
            Stop[] stopsThatThisStopSharesWith,
            boolean favorite) {
        this.name = name;
        this.id = id;
        this.lineThatThisStopIsOn = lineThatThisStopIsOn;
        this.stopsThatThisStopSharesWith = stopsThatThisStopSharesWith;
        this.favorite = favorite;
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
