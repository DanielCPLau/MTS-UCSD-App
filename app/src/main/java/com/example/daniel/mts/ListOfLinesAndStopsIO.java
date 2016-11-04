package com.example.daniel.mts;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


/**
 * Created by Isaac on 10/22/16.
 *
 * Methods to read, write Line and Stop objects, and the list used in the List of lines page
 */

class ListOfLinesAndStopsIO {
    static final String FILENAME_LINE = "line_";
    static final String FILENAME_STOP = "stop_";
    static final String FILENAME_LINE_INFO_LIST = "lineInfoList";
    static final String FILENAME_FAVORITE_LIST = "favouriteList";


    private static void write(Writable obj) {
        try {
            Context context = MyApplication.getAppContext();

            String path = obj.getWritePreflix() + obj.getId();
            new File(path).delete();

            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            JSONObject jobj = obj.getJSONObject();
            oos.writeObject(jobj.toString());

            oos.close();
            fos.close();

        }
        catch (IOException e) {
            Log.w("IOException", "occured in ListOfLinesAndStopsIO.write()");
            e.printStackTrace();
        }
    }

    static void read(Writable obj) {
        try {
            Context context = MyApplication.getAppContext();

            String path = obj.getWritePreflix() + obj.getId();

            FileInputStream fis = context.openFileInput(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            String read = (String)ois.readObject();
            JSONObject jobj = new JSONObject(read);

            obj.fillJSONObject(jobj);

            ois.close();
        }
        catch(FileNotFoundException e) {
            Log.i("FileNotFoundException", "occured in ListOfLinesAndStopsIO.read()");
            write(obj);
            read(obj);
        }
        catch(IOException e) {
            Log.i("IOException", "occured in ListOfLinesAndStopsIO.read()");
            write(obj);
            read(obj);
        }
        catch(ClassNotFoundException e) {
            Log.i("ClassNotFoundException", "occured in ListOfLinesAndStopsIO.read()");
            e.printStackTrace();
            write(obj);
            read(obj);
        }
        catch (JSONException e) {
            Log.i("JSONException", "occured in ListOfLinesAndStopsIO.read()");
            e.printStackTrace();
            write(obj);
            read(obj);
        }
    }

    static void checkLineInfoList() {
        Context context = MyApplication.getAppContext();

        String path = FILENAME_LINE_INFO_LIST;

        try {
            FileInputStream fis = context.openFileInput(FILENAME_LINE_INFO_LIST);
        }
        catch (FileNotFoundException e) {
            writeLineInfoList();
        }
    }

    private static void writeLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();

            String path = FILENAME_LINE_INFO_LIST;
            new File(path).delete();

            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            LineInfo[] array = RemoteFetch.getListOfLinesInfo("MTS");

            if(array == null) {
                Log.w("Null", "occured in ListOfLinesAndStopsIO.writeLineInfoList()");
                return;
            }

            JSONArray jarray = new JSONArray();

            for (LineInfo anArray : array) {
                jarray.put(anArray.getJSONObject());
            }

            oos.writeInt(array.length);
            oos.writeObject(jarray.toString());

            oos.close();
            fos.close();
        }
        catch (IOException e) {
            Log.w("IOException", "occured in ListOfLinesAndStopsIO.writeLineInfoList()");
            e.printStackTrace();
            writeLineInfoList();
        }
    }

    static LineInfo[] readLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();
            FileInputStream fis = context.openFileInput(FILENAME_LINE_INFO_LIST);
            ObjectInputStream ois = new ObjectInputStream(fis);

            int size = ois.readInt();
            String read = (String) ois.readObject();

            LineInfo lineInfo[] = new LineInfo[size];
            JSONArray jarray = new JSONArray(read);

            for(int i = 0; i < size; i++) {
                JSONObject jobj = (JSONObject) jarray.get(i);
                lineInfo[i] = new LineInfo(jobj);
            }

            ois.close();
            fis.close();

            return lineInfo;
        }
        catch(FileNotFoundException e) {
            writeLineInfoList();
            return readLineInfoList();
        }
        catch(IOException e) {
            writeLineInfoList();
            return readLineInfoList();
        }
        catch(ClassNotFoundException e) {
            Log.w("ClassNotFoundException", "occured in ListOfLinesAndStopsIO.readLineInfoList()");
            e.printStackTrace();
            writeLineInfoList();
            return readLineInfoList();
        }
        catch (JSONException e) {
            Log.w("JSONException", "occured in ListOfLinesAndStopsIO.readLineInfoList()");
            e.printStackTrace();
            writeLineInfoList();
            return readLineInfoList();
        }
    }
}
