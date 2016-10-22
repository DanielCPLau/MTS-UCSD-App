package com.example.daniel.mts;

/**
 * Created by Isaac on 10/21/16.
 */

public class Line {
    private String symbol;      // symbol of the line; eg) 201, 202
    private String nameOfLine;  // name of the line; eg) SuperLoop
    private Stop[] listOfStops; // list of stops on this line
    private Line oppositeDirection; // Points to the line of the other direction;
                                    // eg) 30 to Downtown and 30 to UTC
    private boolean favorite;   // true if user favorited this line

    /**
     * Constructor
     *
     * @param symbol symbol of the line
     * @param nameOfLine name of the line
     * @param listOfStops list of stops on this line
     * @param oppositeDirection line of the other direction
     */
    public Line(
            String symbol,
            String nameOfLine,
            Stop[] listOfStops,
            Line oppositeDirection,
            boolean favorite) {
        this.symbol = symbol;
        this.nameOfLine = nameOfLine;
        this.listOfStops = listOfStops;
        this.oppositeDirection = oppositeDirection;
        this.favorite = favorite;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getNameOfLine() {
        return nameOfLine;
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
