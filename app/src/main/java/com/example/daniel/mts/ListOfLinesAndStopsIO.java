package com.example.daniel.mts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;

/**
 * Created by Isaac on 10/22/16.
 */

public class ListOfLinesAndStopsIO {
    private static final String FILENAME_LINE = "line_";
    private static final String FILENAME_STOP = "stop_";
    public static final String FILE_NAME_LINE_INFO_LIST = "lineInfoList";

    // run this to initialize all line and stop information
    public static void init() {
    }

    public static void writeLine(Line line) {

    }

    public static void writeStop(Stop stop) {

    }

    public static void writeLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();
            FileOutputStream fos = context.openFileOutput(FILE_NAME_LINE_INFO_LIST, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            LineInfo[] array = RemoteFetch.getListOfLinesInfo("MTS");

            JSONArray mJSONArray = new JSONArray(Arrays.asList(array));

            // write size first
            oos.writeInt(array.length);

            for(LineInfo l:array) {
                oos.writeObject(l);
            }

            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            // TODO
            ex.printStackTrace();
        }
    }


    public static Line readLine(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            Context context = MyApplication.getAppContext();
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(FILENAME_LINE + id));

            Line line = (Line) ois.readObject();

            ois.close();

            return line;
        }
        catch(FileNotFoundException ex) {
            // TODO
            writeLine(new Line(id));
            readLine(id);
        }
        catch(IOException | ClassNotFoundException ex) {
            // TODO
            ex.printStackTrace();
        }
        return null;
    }

    public static Stop readStop(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            Context context = MyApplication.getAppContext();
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(FILENAME_STOP + id));

            Stop stop = (Stop) ois.readObject();

            ois.close();

            return stop;
        }
        catch(FileNotFoundException ex) {
            writeStop(new Stop(id));
            readStop(id);
        }
        catch(IOException | ClassNotFoundException ex) {
            // TODO
            ex.printStackTrace();
        }
        return null;
    }

    public static LineInfo[] readLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();
            FileInputStream fis = context.openFileInput(FILE_NAME_LINE_INFO_LIST);
            ObjectInputStream ois = new ObjectInputStream(fis);

            if(ois.available() == 0) {
                writeLineInfoList();
            }

            int size = ois.readInt();

            LineInfo lineInfo[] = new LineInfo[size];

            for(int i = 0; i < size; i++) {
                lineInfo[i] = (LineInfo) ois.readObject();
            }
            ois.close();
            fis.close();

            return lineInfo;
        }
        catch(FileNotFoundException ex) {
            writeLineInfoList();
            readLineInfoList();
        }
        catch(IOException | ClassNotFoundException ex) {
            // TODO
            ex.printStackTrace();
        }
        return null;
    }

}
