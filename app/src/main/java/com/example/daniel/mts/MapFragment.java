package com.example.daniel.mts;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends android.support.v4.app.Fragment
                         implements OnFragmentInteractionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // text that user inputs to search maps
    EditText inputSearch;

    // adapter that changes the list according to user input
    CustomListAdapter adapter;

    // listener for the fragment's buttons and search input
    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
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
        // create a new map fragment on loading of this tab
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        // set the given arguments and create the map fragment
        fragment.setArguments(args);
        return fragment;
    }

    /*
     * Function to save the state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /*
     * Function to create the view of the mapfragment, listing the ucsd and mts lines with
     * the maps and the icon images that correspond to the list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.map_activity_list, null);

        // create the view based on the xml format specified
        final ListView lv = (ListView) rootView.findViewById(R.id.android_list);

        // the text that that the user wants to search the bus lines by
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

        // the arraylist holding the required elements for the view including the
        // name, map, icon, and color of the line
        final ArrayList<String> filterNameArray = createButtonList();
        final ArrayList<Integer> filterMapArray = createMapList();
        final ArrayList<Integer> filterIconArray = createIconList();
        final ArrayList<Integer> filterNameArrayColor = createColor();

        // create and set a new adapter that adapts the view according to the
        // text that the user inputs
        adapter = new CustomListAdapter(getActivity(), filterNameArray,
                  filterMapArray, filterIconArray, filterNameArrayColor);
        lv.setAdapter(adapter);

        // watch the inputted text that the user types into the search bar
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing before text is changed
            }

            // when the text is changed, update the view
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // change the inputted text to all lower case
                String input = (charSequence.toString()).toLowerCase();
                int length = input.length();

                // create new list of elements for the new view
                final ArrayList<String> filterNameArray = createButtonList();
                final ArrayList<Integer> filterMapArray = createMapList();
                final ArrayList<Integer> filterIconArray = createIconList();
                final ArrayList<Integer> filterNameArrayColor = createColor();

                // array to hold all the lower case names of the button for
                // search query purposes
                String[] buttonNameLower = new String[filterNameArray.size()];

                for(int k = 0; k < filterNameArray.size(); k++) {
                    buttonNameLower[k] = filterNameArray.get(k).toLowerCase();
                }

                filterNameArray.clear();

                // copy the maps from the filtered map array which contains the proper
                // map of the lines to show.
                ArrayList<Integer> copyMapArray = new ArrayList<Integer>(filterMapArray.size());
                for (int index = 0; index < filterMapArray.size(); index++){
                        copyMapArray.add(filterMapArray.get(index));
                }
                // empty the filter list to prepare for next search
                filterMapArray.clear();

                // copy the icons from the filtered icon array which contains the proper
                // icon of the lines to show.
                ArrayList<Integer> copyIconArray = new ArrayList<Integer>(filterIconArray.size());
                for (int index2 = 0; index2 < filterIconArray.size(); index2++){
                    copyIconArray.add(filterIconArray.get(index2));
                }
                // empty the filter list to prepare for next search
                filterIconArray.clear();

                // copy the color from the filtered color array which contains the proper
                // color of the line buttons to show.
                ArrayList<Integer> copyColor = new ArrayList<Integer>(filterNameArrayColor.size());
                for (int index3 = 0; index3 < filterNameArrayColor.size(); index3++){
                    copyColor.add(filterNameArrayColor.get(index3));
                }
                // empty the filter list to prepare for next search
                filterNameArrayColor.clear();

                // fill the filter array list with the next lines names,
                // map images, icon images, and color of the lines
                for (int j = 0; j < buttonNameLower.length; j++) {
                    if (length <= buttonNameLower[j].length()) {

                        // they should correspond to the search text
                        if (buttonNameLower[j].toString().contains(input)) {
                            filterNameArray.add(buttonNameLower[j]);
                            filterMapArray.add(copyMapArray.get(j));
                            filterIconArray.add(copyIconArray.get(j));
                            filterNameArrayColor.add(copyColor.get(j));
                        }
                    }
                }

                // create a new adapter every time the text is changed in the search bar
                adapter = new CustomListAdapter(getActivity(), filterNameArray,
                        filterMapArray, filterIconArray, filterNameArrayColor);
                lv.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing after the text is changed
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    // the array list of the line names for the buttons on the map fragment
    private ArrayList<String> createButtonList(){
        String[] buttonName;
        // the arraylist to take in a dynamic amount of line names
        ArrayList<String> toReturn = new ArrayList<String>();
        buttonName = new String[]{
                "201", "202", "150","30", "41", "237", "921", "City Shuttle(Arriba/Nobel)",
                "Coaster East", "Coaster East/West", "Hillcrest/Campus A.M.",
                "Hillcrest/Campus P.M.", "Clockwise Campus Loop", "Counter Campus Loop",
                "Weekend Clockwise Campus Loop", "Weekend Counter Campus Loop", "Mesa",
                "Regents", "SIO Loop", "Sanford Consortium Shuttle", "Coaster West",
                "East Campus Connector"
        };

        // add the strings of the line names from the string array to arraylist
        for (int index = 0; index < buttonName.length; index++){

            toReturn.add(index, buttonName[index]);

        }

        return toReturn;
    }

    // the array list to hold the color of the buttons
    private ArrayList <Integer> createColor(){
        Integer[] colorId;
        // the arraylist to hold the colors of the buttons according to
        // mts or ucsd bus
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
        colorId = new Integer[]{
                // for the mts lines (red)
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                Color.rgb(255, 102, 102),
                // for the ucsd lines (blue)
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255),
                Color.rgb(51, 133, 255)
        };

        // add the colors from the array to the arraylist
        for (int index = 0; index < colorId.length; index++){

            toReturn.add(index, colorId[index]);

        }
        return toReturn;
    }

    // the arraylist of the map images for the maps of the lines
    private ArrayList<Integer> createMapList() {
        // ids for the maps that pass through ucsd
        Integer[] mapId;
        // arraylist to hold dynamic maps
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
        mapId = new Integer[]{
                R.drawable.map201,
                R.drawable.map202,
                R.drawable.map150,
                R.drawable.map30,
                R.drawable.map41,
                R.drawable.map237,
                R.drawable.map921,
                R.drawable.mapcityshuttle,
                R.drawable.mapcoastereast,
                R.drawable.mapcoastereastwest,
                R.drawable.maphillcrestam,
                R.drawable.maphillcrestpm,
                R.drawable.mapclockwisecampus,
                R.drawable.countercampusloop,
                R.drawable.eveningclockwise,
                R.drawable.eveningcounterclockwise,
                R.drawable.mapmesa,
                R.drawable.mapregents,
                R.drawable.mapsio,
                R.drawable.mapsanford,
                R.drawable.coasterwest,
                R.drawable.eastcampus,

        };

        // add the static map images into the arraylist of images
        for (int index = 0; index < mapId.length; index++){

            toReturn.add(index, mapId[index]);

        }
        return toReturn;
    }

    // the arraylist of the icon images indicate whether line is mts or ucsd
    private ArrayList<Integer> createIconList(){
        // ids for the ucsd or mts icons
        Integer[] iconId;
        // arraylist to hold dynamic maps
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
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

        // add the icons from the array to the arraylist
        for (int index = 0; index < iconId.length; index++){

            toReturn.add(index, iconId[index]);

        }
        return toReturn;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("WHAT",uri);
        }
    }

    // attach the listener for the map fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // allows buttons to be responsive to user's touch
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // if there is no listener, throw an exception
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // detach the listener from the view
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null; // set the listener to null
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
