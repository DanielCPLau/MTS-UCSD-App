package com.example.daniel.mts;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * Created by Isaac on 10/22/16.
 */

public class ListOfLinesAndStopsIO extends Activity {
    private static final String FILENAME_LINE = "line_";
    private static final String FILENAME_STOP = "stop_";

    // run this to initialize all line and stop information
    public void init() {
    }

    public void writeLine(Line line) {

    }

    public void writeStop(Stop stop) {

    }

    public void writeLineIdList(String[] list) {

    }

    public void writeStopIdList(String[] list) {

    }

    public Line readLine(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(FILENAME_LINE + id));

            Line line = (Line) ois.readObject();

            ois.close();

            return line;
        }
        catch(FileNotFoundException ex) {
            // TODO
        }
        catch(IOException ex) {
            // TODO
        }
        catch(ClassNotFoundException ex) {
            // TODO
        }
        return null;
    }

    public Stop readStop(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(FILENAME_STOP + id));

            Stop stop = (Stop) ois.readObject();

            ois.close();

            return stop;
        }
        catch(FileNotFoundException ex) {
            // TODO
        }
        catch(IOException ex) {
            // TODO
        }
        catch(ClassNotFoundException ex) {
            // TODO
        }
        return null;
    }

    public String[] readLineIdList() {
        return null;
    }

    public String[] readStopIdList() {
        return null;
    }
}
