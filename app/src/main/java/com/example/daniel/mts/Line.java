package com.example.daniel.mts;

import android.util.Log;

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
        if( ListOfLinesAndStopsIO.readable(this)) {
            fill(ListOfLinesAndStopsIO.readLine(getId()));
        }
        else {
            RemoteFetch.fillLineDetailInfo(this);
            ListOfLinesAndStopsIO.write(this);
        }
    }

    public Line(Line line) {
        line.id = this.id;
        line.agency = this.agency;
        line.shortName = this.shortName;
        line.longName = this.longName;
        line.color = this.color;
        line.textColor = this.textColor;
        line.favorite = this.favorite;
    }

    private void fill(Line line) {
        this.agency = line.agency;
        this.shortName = line.shortName;
        this.longName = line.longName;
        this.color = line.color;
        this.textColor = line.textColor;
        this.favorite = line.favorite;
        this.directionId = line.directionId;
        this.directionName = line.directionName;
        this.listOfStopsId = line.listOfStopsId;
        this.oppositeDirectionId = line.oppositeDirectionId;
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
}
