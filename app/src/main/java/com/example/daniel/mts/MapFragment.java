package com.example.daniel.mts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends android.support.v4.app.Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructory
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.map_activity_list, null);

        String[] buttonName = createButtonList();
        Integer[] mapId = createMapList();
        Integer[] iconId = createIconList();
        ListView lv = (ListView) rootView.findViewById(R.id.android_list);


        CustomListAdapter adapter = new CustomListAdapter(getActivity(), buttonName, mapId, iconId);
        lv.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    private String[] createButtonList(){
        String[] buttonName;
        buttonName = new String[]{
                "201", "202", "150","30", "41", "237", "921", "City Shuttle(Arriba/Nobel)",
                "Coaster East", "Coaster East/West", "Hillcrest/Campus A.M.",
                "Hillcrest/Campus P.M.", "Clockwise Campus Loop", "Counter Campus Loop",
                "Weekend Clockwise Campus Loop", "Weekend Counter Campus Loop", "Mesa",
                "Regents", "SIO Loop", "Sanford Consortium Shuttle", "Coaster West",
                "East Campus Connector"
        };
        return buttonName;
    }


    private Integer[] createMapList() {
        // ids for the maps that pass through ucsd
        Integer[] mapId;
        mapId = new Integer[]{
                R.drawable.map201,
                R.drawable.map202,
                R.drawable.map150,
                R.drawable.map30,
                R.drawable.map41,
                R.drawable.map237,
                R.drawable.map921

//                R.drawable.mapcityshuttle,
//                R.drawable.mapcoastereast,
//                R.drawable.mapcoastereastwest,
//                R.drawable.maphillcrestam,
//                R.drawable.maphillcrestpm,
//                R.drawable.mapclockwisecampus,
//                R.drawable.countercampusloop,
//                R.drawable.eveningclockwise,
//                R.drawable.eveningcounterclockwise,
//                R.drawable.mapmesa,
//                R.drawable.mapregents,
//                R.drawable.mapsio,
//                R.drawable.mapsanford,
//                R.drawable.coasterwest,
//                R.drawable.eastcampus

        };
        return mapId;
    }




    private Integer[] createIconList(){
        // ids for the ucsd or mts icons
        Integer[] iconId;
        iconId = new Integer[]{
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.mtsicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon,
                R.drawable.ucsdicon

        };
        return iconId;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("WHAT",uri);
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
}
