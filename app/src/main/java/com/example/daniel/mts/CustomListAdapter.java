package com.example.daniel.mts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
/*
    private final String[] linename;
    private final Integer[] mapid;
    private final Integer[] iconid;
*/

    private final ArrayList<String> filterNameArray;
    private final ArrayList<Integer> filterMapArray;
    private final ArrayList<Integer> filterIconArray;

    //public CustomListAdapter(Activity context, String[] linename, Integer[] mapid, Integer[] iconid) {
    public CustomListAdapter(Activity context, ArrayList<String> filterNameArray, ArrayList<Integer> filterMapArray,
                             ArrayList<Integer> filterIconArray) {

        super(context, R.layout.map_activity_list, filterNameArray);
        // TODO Auto-generated constructor stub


        this.context=context;
        this.filterNameArray=filterNameArray;
        this.filterMapArray=filterMapArray;
        this.filterIconArray=filterIconArray;
        /*
        this.linename=linename;
        this.mapid=mapid;
        this.iconid=iconid;
        */
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.map_fragment, null,true);



        Button lineButton = (Button) rowView.findViewById(R.id.map_button_template);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.map_image);
        ImageView iconImg = (ImageView) rowView.findViewById(R.id.icon_image);

        Picasso.with(context).load(filterMapArray.get(position)).into(imageView);
        Picasso.with(context).load(filterIconArray.get(position)).into(iconImg);
        lineButton.setText(filterNameArray.get(position));
        //imageView.setImageResource(mapid[position]);
        //iconImg.setImageResource(iconid[position]);


        return rowView;

    }

}
