package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line {
    public String id;
    public String agency;           // agency of the bus line
    public String shortName;
    public String longName;
    public String color;
    public String textColor;
    public Stop[] listOfStops;      // list of stops on this line
    public Line oppositeDirection;  // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    public boolean favorite;        // true if user favorited this line
    public boolean isUCSDShuttle;   // true if line is UCSD Shuttle

    public Line(
            String id,
            boolean isUCSDShuttle) {
        this.id = id;
        this.isUCSDShuttle = isUCSDShuttle;

        if (isUCSDShuttle) {
            // somehow get shuttle info from website
        } else {
            // use API to get information about line with symbol
            // RemoteFetch.fillLineInfo(this);
        }
    }

    public void switchFavorite() {
        favorite = !favorite;
    }
}
