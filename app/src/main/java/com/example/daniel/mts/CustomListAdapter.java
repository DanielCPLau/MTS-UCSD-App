package com.example.daniel.mts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
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
    hello
*/

    private final ArrayList<Integer> filterNameArrayColor;
    private final ArrayList<String> filterNameArray;
    private final ArrayList<Integer> filterMapArray;
    private final ArrayList<Integer> filterIconArray;

    //public CustomListAdapter(Activity context, String[] linename, Integer[] mapid, Integer[] iconid) {
    public CustomListAdapter(Activity context, ArrayList<String> filterNameArray, ArrayList<Integer> filterMapArray,
                             ArrayList<Integer> filterIconArray, ArrayList<Integer> filterNameArrayColor) {

        super(context, R.layout.map_activity_list, filterNameArray);
        // TODO Auto-generated constructor stub


        this.context=context;
        this.filterNameArray=filterNameArray;
        this.filterMapArray=filterMapArray;
        this.filterIconArray=filterIconArray;
        this.filterNameArrayColor=filterNameArrayColor;
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

/*
        if (lineButton.getText().toString().equals("201") || lineButton.getText().toString().equals(202)){
            lineButton.setBackgroundColor(Color.RED);
        }
        else{
            lineButton.setBackgroundColor(Color.BLUE);
        }

*/
        /*
        // ucsd busline
        if (filterMapArray.get(position) == R.drawable.ucsdicon) {
            lineButton.setBackgroundColor(Color.BLUE);
        } else { //mts busline
            lineButton.setBackgroundColor(Color.RED);
        }*/

        ImageView imageView = (ImageView) rowView.findViewById(R.id.map_image);
        ImageView iconImg = (ImageView) rowView.findViewById(R.id.icon_image);


        Picasso.with(context).load(filterMapArray.get(position)).into(imageView);
        Picasso.with(context).load(filterIconArray.get(position)).into(iconImg);
        lineButton.setText(filterNameArray.get(position));
        lineButton.setBackgroundColor(filterNameArrayColor.get(position));
        //imageView.setImageResource(mapid[position]);
        //iconImg.setImageResource(iconid[position]);

        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Button viewButton = (Button) view;
                    if (viewButton.getText().toString().contains("921") ||
                            viewButton.getText().toString().contains("237") ||
                        viewButton.getText().toString().contains("41") ||
                            viewButton.getText().toString().contains("30") ||
                            viewButton.getText().toString().contains("150") ||
                            viewButton.getText().toString().contains("201") ||
                            viewButton.getText().toString().contains("202")) {
                        Intent intent = new Intent(getContext(), DisplayListOfStops.class);
                        Bundle dataBundle = new Bundle();
                        Button b = (Button) view;
                        dataBundle.putString("SelectedProperty", "MTS_" + b.getText().toString());
                        intent.putExtras(dataBundle);
                        context.startActivity(intent);
                    }
                    else{
                        Uri uriUrl = Uri.parse("https://www.ucsdbus.com/arrivals");
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        context.startActivity(launchBrowser);
                    }
            }
        });
        return rowView;

    }

}


