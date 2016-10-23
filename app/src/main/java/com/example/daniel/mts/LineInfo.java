package com.example.daniel.mts;

/**
 * Created by Isaac on 10/22/16.
 */

public class LineInfo implements Comparable<LineInfo>{
    public String agency;
    public String id;
    public String shortName;
    public String longName;
    public String color;
    public String textColor;
    public boolean favorite;

    public LineInfo (String id) {
        this.id = id;
        RemoteFetch.fillLineInfo(this);
    }

    @Override
    public int compareTo(LineInfo o) {
        return this.shortName.hashCode() - o.shortName.hashCode();
    }

    public void switchFavorite() {
        favorite = !favorite;
    }
}
