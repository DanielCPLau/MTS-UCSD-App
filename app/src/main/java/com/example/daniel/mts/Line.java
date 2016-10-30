package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line extends LineInfo implements Writable {
    public String directionId = "";            // direction of the line
    public String directionName = "";        // name of the direction
    public String[] listOfStopsId;      // list of stops on this line
    public String oppositeDirectionId = "";  // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    public Line(String id) {
        super(id);
        RemoteFetch.fillLineDetailInfo(this);
    }

    public String getWritePreflix() {
        return ListOfLinesAndStopsIO.FILENAME_LINE;
    }

    public boolean isFilled() {
        return !longName.equals("");
    }

    public String getId() {
        return id + "_" + directionId;
    }

    public void fill() {
        RemoteFetch.fillLineInfo(this);
    }
}
