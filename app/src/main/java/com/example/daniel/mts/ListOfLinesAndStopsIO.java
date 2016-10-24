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
    private static final String FILE_NAME_LINE_INFO_LIST = "lineInfoList";

    // run this to initialize all line and stop information
    public void init() {
    }

    public void writeLine(Line line) {

    }

    public void writeStop(Stop stop) {

    }

    public void writeLineInfoList() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(FILE_NAME_LINE_INFO_LIST, Context.MODE_PRIVATE));
        }
        catch (FileNotFoundException ex) {
            // TODO
        }
        catch (IOException ex) {
            // TODO
        }
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

    public LineInfo[] readLineInfoList() {
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(FILE_NAME_LINE_INFO_LIST));
            LineInfo[] lineInfo = (LineInfo[]) ois.readObject();
            ois.close();
            return lineInfo;
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

}
