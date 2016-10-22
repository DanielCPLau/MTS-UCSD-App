package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line {
    private String symbol;      // symbol of the line; eg) 201, 202
    private String name;  // name of the line; eg) SuperLoop
    private Stop[] listOfStops; // list of stops on this line
    private Line oppositeDirection; // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    private boolean favorite;       // true if user favorited this line
    private boolean isUCSDShuttle;  // true if line is UCSD Shuttle

    /**
     * Constructor
     *
     * @param symbol symbol of the line
     * @param isUCSDShuttle true if line is UCSD shuttle, false other wise
     */
    public Line(
            String symbol,
            boolean isUCSDShuttle) {
        this.symbol = symbol;
        this.isUCSDShuttle = isUCSDShuttle;

        if(isUCSDShuttle) {
            // somehow get shuttle info from website
        }
        else {
            // use API to get information about line with symbol
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListOfStops(Stop[] listOfStops) {
        this.listOfStops = listOfStops;
    }

    public void setOppositeDirection(Line oppositeDirection) {
        this.oppositeDirection = oppositeDirection;
    }

    public void switchFavorite() {
        favorite = !favorite;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public Stop[] getListOfStops() {
        return listOfStops;
    }

    public Line getOppositeDirection() {
        return oppositeDirection;
    }

    public boolean getFavorite() {
        return favorite;
    }
}
