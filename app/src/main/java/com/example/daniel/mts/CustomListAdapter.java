package com.example.daniel.mts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] linename;
    private final Integer[] mapid;
    private final Integer[] iconid;

    public CustomListAdapter(Activity context, String[] linename, Integer[] mapid, Integer[] iconid) {
        super(context, R.layout.map_activity_list, linename);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.linename=linename;
        this.mapid=mapid;
        this.iconid=iconid;
    }

//    @Override
//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.map_fragment, null,true);
//
//        Button lineButton = (Button) rowView.findViewById(R.id.map_button_201);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.map_image);
//        ImageView iconImg = (ImageView) rowView.findViewById(R.id.icon_image);
//
//        lineButton.setText(linename[position]);
//        imageView.setImageResource(mapid[position]);
//        iconImg.setImageResource(iconid[position]);
//        return rowView;
//
//    }
}