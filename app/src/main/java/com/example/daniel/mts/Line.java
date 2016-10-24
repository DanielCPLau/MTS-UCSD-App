package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line extends LineInfo {
    public String[] listOfStopsId;      // list of stops on this line
    public String oppositeDirectionId;  // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    public Line(String id) {
        super(id);
        RemoteFetch.fillLineDetailInfo(this);
    }
}
