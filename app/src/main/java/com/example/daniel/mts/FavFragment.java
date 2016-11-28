package com.example.daniel.mts;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;


import static com.example.daniel.mts.ListOfLinesAndStopsIO.readFavoriteList;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavFragment extends ListFragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int count = 0;
    private static int numOfRows = 0;

    // limit of predictions to show
    private static final int LIMIT = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;


    public FavFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment (DEFAULT CODE)
        //return inflater.inflate(R.layout.fav_fragment, container, false);


        // get permission to access networks
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fav_fragment,container, false);


        // Get the List of favorite stops
        ArrayList<Favorite> favoriteArray = ListOfLinesAndStopsIO.readFavoriteList();

        Favorite[] favoriteList = new Favorite[favoriteArray.size()];

        for(int i = 0; i < favoriteList.length; i++) {
            favoriteList[i] = favoriteArray.get(i);
        }
        count = 0;
        numOfRows = favoriteList.length;


        ArrayAdapter<Favorite> adapter = new FavAdapter(getActivity(), R.layout.favoritestops_layout, R.id.favLineStop, favoriteList);
        setListAdapter(adapter);
        setRetainInstance(true);




        return rootview;
    }

    public void onListItemClick(ListView view1, View view, int position, long id)
    {
        Favorite favStop = (Favorite) getListAdapter().getItem(position);
        String stopId = favStop.stopId;
        String lineID = favStop.lineId;

        // start new activity to show stop info based on stop pressed
        Intent i = new Intent(getActivity(), StopActivity.class);

        // pass in info about the stop
        Bundle dataBundle = new Bundle();
        dataBundle.putString("stopId", stopId);
        dataBundle.putString("lineId", lineID);
        i.putExtras(dataBundle);
        startActivity(i);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("HELL", uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void onFragmentMessage(String MSG, Object data) {


    }


    public class FavAdapter extends ArrayAdapter {
        // Constructor
        public FavAdapter(Context context, int resources, int textViewResourceID, Object[] objects) {
            super(context, resources, textViewResourceID, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            count++;


            // get favorite object at row position
            Favorite favStop = (Favorite)getItem(position);
            String stopId = favStop.stopId;
            String lineId = favStop.lineId;


            Stop stop = new Stop(stopId, lineId);
            String color = "#" + stop.color;
            String stopName = stop.name;
            String lineShortName = stop.lineShortName;
            String lineDirName = stop.directionName;


            TextView line = (TextView) view.findViewById(R.id.favLineStop);
            TextView stopText = (TextView)view.findViewById(R.id.favStopName);
            TextView dirName = (TextView)view.findViewById(R.id.favStopDir);
            TextView prediction = (TextView)view.findViewById(R.id.favPrediction);


            GradientDrawable lineCircleBg = (GradientDrawable)line.getBackground();
            lineCircleBg.setColor(Color.parseColor(color));


            line.setText(lineShortName);
            line.setTextColor(Color.WHITE);


            stopText.setText(stopName);
            stopText.setTextColor(Color.BLACK);


            dirName.setText("To " + lineDirName);
            dirName.setTextColor(Color.GRAY);

            if(count > numOfRows && count <= numOfRows * 2) {
                ArrayList<Integer> pred = RemoteFetch.getPrediction(stopId, lineId, lineDirName);
                String times = "";


                if (pred.size() > 0) {
                    for (int i = 0; i < pred.size(); i++) {
                        int time = pred.get(i);


                        if (time == 0) {
                            times += "Arriving";
                        } else {
                            times += pred.get(i);
                        }


                        if (i >= LIMIT - 1) break;
                        if (i < pred.size() - 1) times += ", ";
                    }
                    times += " mins";
                } else {
                    times = "No prediction";
                }


                prediction.setText(times);
                prediction.setTextColor(Color.BLACK);

            }


            // alternate grey and white row background
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.WHITE);
            } else {
                view.setBackgroundColor(Color.parseColor("#EFF5FF"));
            }


            return view;
        }


    }


}




