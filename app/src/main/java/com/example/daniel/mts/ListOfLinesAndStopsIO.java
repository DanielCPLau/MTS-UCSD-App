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
    public static final String FILENAME_LINE = "line_";
    public static final String FILENAME_STOP = "stop_";
    public static final String FILE_NAME_LINE_INFO_LIST = "lineInfoList";

    // run this to initialize all line and stop information
    public static void init() {
    }

    public static void writeLine(Line obj) {
        try {
            Context context = MyApplication.getAppContext();
            FileOutputStream fos = context.openFileOutput(obj.getWritePreflix() + obj.getId(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Log.d("Hello1", obj.getWritePreflix() + obj.getId());

            oos.writeObject(obj);

            oos.close();
            fos.close();

            Log.d("HEllo", ""+readable(obj));
        } catch (IOException ex) {
            // TODO
            ex.printStackTrace();
        }
    }

    public static void writeStop(Stop obj) {
        try {
            Context context = MyApplication.getAppContext();
            FileOutputStream fos = context.openFileOutput(obj.getWritePreflix() + obj.getId(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();

        } catch (IOException ex) {
            // TODO
            ex.printStackTrace();
        }
    }

    public static boolean readable(Writable obj) {
        try {
            Log.d("Hello3", obj.getWritePreflix() + obj.getId());

            Context context = MyApplication.getAppContext();
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(obj.getWritePreflix() + obj.getId()));

            if(ois.available() == 0) {
                ois.close();
                return false;
            }
            ois.close();

            return true;
        }
        catch(FileNotFoundException ex) {
            return false;
        }
        catch(IOException ex) {
            return false;
        }
    }

    public static void writeLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();
            FileOutputStream fos = context.openFileOutput(FILE_NAME_LINE_INFO_LIST, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            LineInfo[] array = RemoteFetch.getListOfLinesInfo("MTS");

            // write size first
            oos.writeInt(array.length);

            for(LineInfo l:array) {
                oos.writeObject(l);
            }

            oos.close();
            fos.close();
        } catch (IOException ex) {
            // TODO
            ex.printStackTrace();
        }
    }


    // MUST USE getID() for id.
    public static Line readLine(String id) {
        try {
            Context context = MyApplication.getAppContext();
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(FILENAME_LINE + id));

            if(ois.available() == 0) {
                ois.close();
                writeLine(new Line(id));
            }

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

    // DO NOT USE stop.getId() for this method.
    public static Stop readStop(String id, String lineId) {
        try {
            Context context = MyApplication.getAppContext();
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(FILENAME_STOP + id + "_" + lineId));

            if(ois.available() == 0) {
                ois.close();
                writeStop(new Stop(id, lineId));
            }

            Stop stop = (Stop) ois.readObject();

            ois.close();

            return stop;
        }
        catch(FileNotFoundException ex) {
            // TODO

            ex.printStackTrace();
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
                fis.close();
                ois.close();
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
