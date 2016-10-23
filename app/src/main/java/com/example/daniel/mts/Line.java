package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line extends LineInfo {
    public Stop[] listOfStops;      // list of stops on this line
    public Line oppositeDirection;  // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    public Line(String id) {
        super(id);
        RemoteFetch.fillLineDetailInfo(this);
    }
}
