package com.example.daniel.mts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.*;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.daniel.mts.R.drawable.circle;


// Sheldon Test
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinesFragment extends ListFragment implements OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LinesFragment newInstance(String param1, String param2) {
        LinesFragment fragment = new LinesFragment();
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
        // get permission to access network
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.lines_fragment,container, false);

        // get MTS line ids
        LineInfo[] lineInfo = ListOfLinesAndStopsIO.readLineInfoList();    // Need to change later to read from IO

        // Display list of lines
        ArrayAdapter<LineInfo> adapter = new MyAdapter(getActivity(),R.layout.rowlayout, R.id.txtitem,lineInfo);
        setListAdapter(adapter);
        setRetainInstance(true);

        return rootview;
    }

    public void onListItemClick(ListView view1, View view, int position, long id)
    {
        // get the Line at this row
        LineInfo lineObj = (LineInfo)getListAdapter().getItem(position);

        // open campus loop website for UCSD lines
        if(lineObj.agency.equals("UCSD")) {
            Uri uriUrl = Uri.parse("https://www.ucsdbus.com/arrivals");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
        else {
            // start a new activity when row is clicked and pass in the Line's id
            String selectedValue = (String) lineObj.id;
            Intent i = new Intent(getActivity(), DisplayListOfStops.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putString("SelectedProperty", selectedValue);
            i.putExtras(dataBundle);
            startActivity(i);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("HELLO", uri);
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

    /**
     *  Sources of help : http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
     */
    public class MyAdapter extends ArrayAdapter {
        private final Context context;
        public MyAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
            super(context, resource, textViewResourceId, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

            // get lineinfo object at row position
            LineInfo obj = (LineInfo)getItem(position);
            String col = "#" + obj.color;
            String shortNm = obj.shortName;
            String longNm = obj.longName;
            String agency = obj.agency;
            String txtCol = "#" + obj.textColor;


            longNm = longNm.replaceAll("Center", "Ctr");
            longNm = longNm.replaceAll("Clockwise", "CW");
            longNm = longNm.replaceAll("Counterclockwise", "CCW");
            longNm = longNm.replaceAll("Counterclock", "CCW");
            longNm = longNm.replaceAll("San Diego", "SD");

            // alternating grey and white row backgrounds
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.WHITE);
            } else {
                view.setBackgroundColor(Color.parseColor("#F7F7F7"));
            }

            // get text object at row position
            TextView tv = (TextView) view.findViewById(R.id.txtitem);

            // set color of circle background to the color of the lineinfo object
            GradientDrawable tvBackground = (GradientDrawable) tv.getBackground();
            tvBackground.setColor(Color.parseColor(col));

            // set text to be line number from the lineinfo object
            tv.setText(shortNm);
            if(agency.equals("UCSD")) {
                tv.setTextColor(Color.parseColor(txtCol));
            }
            else {
                tv.setTextColor(Color.WHITE);
            }

            tv = (TextView) view.findViewById(R.id.nameItem);

            tv.setText(longNm);
            tv.setTextColor(Color.BLACK);

            return view;
        }
    }
}
