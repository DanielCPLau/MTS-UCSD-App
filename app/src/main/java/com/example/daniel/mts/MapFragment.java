package com.example.daniel.mts;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class MapFragment extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.map_activity_list);

        //CustomListAdapter adapter=new CustomListAdapter(this, buttonname, mapid, iconid);
        //list=(ListView)findViewById(R.id.android_list);
        //list.setAdapter(adapter);

        View rootView = inflater.inflate(R.layout.map_activity_list, container, false);

        String[] buttonName = createButtonList();
        Integer[] mapId = createMapList();
        Integer[] iconId = createIconList();
        ListView lv = (ListView)rootView.findViewById(R.id.android_list);
        lv.setAdapter(new CustomListAdapter(getActivity(), buttonName, mapId, iconId));
        System.out.println("HI");
        return rootView;
    }

    private String[] createButtonList(){
        String[] buttonName = {
                "201", "202", "150","30", "41", "237", "921"
        };
        return buttonName;
    }


    private Integer[] createMapList(){

        // ids for the maps that pass through ucsd
        Integer[] mapId={
                R.drawable.map201,
                R.drawable.map202,
                R.drawable.map150,
                R.drawable.map30,
                R.drawable.map41,
                R.drawable.map237,
                R.drawable.map921
        };
        return mapId;
    }


    private Integer[] createIconList(){

        // ids for the ucsd or mts icons
        Integer[] iconId={
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.ucsdicon,
                R.drawable.mtsicon,
                R.drawable.ucsdicon,
                R.drawable.mtsicon,
                R.drawable.ucsdicon,
                R.drawable.mtsicon,
        };
        return iconId;
    }
}