package com.example.daniel.mts;

import android.app.Activity;
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
import java.util.Arrays;

import static android.R.attr.id;

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

    public static void write(Writable obj) {
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

        } catch (IOException ex) {
            Log.w("IOException", "occured in ListOfLinesAndStopsIO.write()");
            ex.printStackTrace();
        }
    }

    public static void read(Writable obj) {
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
            write(obj);
            read(obj);
        }
        catch(IOException e) {
            Log.i("IOException", "occured in ListOfLinesAndStopsIO.read()");
            e.printStackTrace();
            write(obj);
            read(obj);
        }
        catch(ClassNotFoundException e) {
            Log.i("ClassNotFoundException", "occured in ListOfLinesAndStopsIO.read()");
            e.printStackTrace();
            write(obj);
            read(obj);
        } catch (JSONException e) {
            Log.i("JSONException", "occured in ListOfLinesAndStopsIO.read()");
            e.printStackTrace();
            write(obj);
            read(obj);
        }
    }

    public static void writeLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();

            String path = FILE_NAME_LINE_INFO_LIST;
            new File(path).delete();

            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            LineInfo[] array = RemoteFetch.getListOfLinesInfo("MTS");
            JSONArray jarray = new JSONArray();

            for(int i = 0; i < array.length; i++) {
                jarray.put(array[i].getJSONObject());
            }

            oos.writeInt(array.length);
            oos.writeObject(jarray.toString());

            oos.close();
            fos.close();
        } catch (IOException ex) {
            // TODO
            ex.printStackTrace();
        }
    }

    public static LineInfo[] readLineInfoList() {
        try {
            Context context = MyApplication.getAppContext();
            FileInputStream fis = context.openFileInput(FILE_NAME_LINE_INFO_LIST);
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
            // TODO
            e.printStackTrace();
            writeLineInfoList();
            return readLineInfoList();
        }
        catch(ClassNotFoundException e) {
            // TODO
            e.printStackTrace();
            writeLineInfoList();
            return readLineInfoList();
        } catch (JSONException e) {
            // TODO
            e.printStackTrace();
            writeLineInfoList();
            return readLineInfoList();
        }
    }

}
