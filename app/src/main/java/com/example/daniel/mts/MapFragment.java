package com.example.daniel.mts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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

    private ListView lv;

    EditText inputSearch;

    CustomListAdapter adapter;
    //ArrayList<HashMap<String, String>> productList;

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

        /*
        final String[] buttonName = createButtonList();
        final Integer[] mapId = createMapList();
        final Integer[] iconId = createIconList();
        */
        final ListView lv = (ListView) rootView.findViewById(R.id.android_list);

        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
        final ArrayList<String> filterNameArray = createButtonList();
        final ArrayList<Integer> filterMapArray = createMapList();
        final ArrayList<Integer> filterIconArray = createIconList();

        adapter = new CustomListAdapter(getActivity(), filterNameArray, filterMapArray, filterIconArray);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //MapFragment.this.adapter.getFilter().filter(charSequence);
                String input = (charSequence.toString()).toLowerCase();
                int length = input.length();
                String[] buttonNameLower = new String[filterNameArray.size()];

                for(int k = 0; k < filterNameArray.size(); k++) {
                    buttonNameLower[k] = filterNameArray.get(k).toLowerCase();

                }

                filterNameArray.clear();

                ArrayList<Integer> copyMapArray = new ArrayList<Integer>(filterMapArray.size());
                for (int index = 0; index < filterMapArray.size(); index++){

                        copyMapArray.add(filterMapArray.get(index));

                }
                filterMapArray.clear();

                ArrayList<Integer> copyIconArray = new ArrayList<Integer>(filterIconArray.size());
                for (int index2 = 0; index2 < filterIconArray.size(); index2++){

                    copyIconArray.add(filterIconArray.get(index2));

                }
                filterIconArray.clear();


                for (int j = 0; j < buttonNameLower.length; j++) {
                    if (length <= buttonNameLower[j].length()) {

                        if (buttonNameLower[j].toString().contains(input)) {
                            filterNameArray.add(buttonNameLower[j]);
                            filterMapArray.add(copyMapArray.get(j));
                            filterIconArray.add(copyIconArray.get(j));

                        }
                    }
                }


                adapter = new CustomListAdapter(getActivity(), filterNameArray, filterMapArray, filterIconArray);
                lv.setAdapter(adapter);
                /*

                if (Arrays.asList(buttonNameLower).contains(input)){

                    int indexOfInput = Arrays.asList(buttonNameLower).indexOf(input);
                    adapter = new CustomListAdapter(getActivity(), new String[]{input}, new Integer[]{mapId[indexOfInput]}, new Integer[]{iconId[indexOfInput]});
                    lv.setAdapter(adapter);
                }
                */
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private ArrayList<String> createButtonList(){
        String[] buttonName;
        ArrayList<String> toReturn = new ArrayList<String>();
        buttonName = new String[]{
                "201", "202", "150","30", "41", "237", "921", "City Shuttle(Arriba/Nobel)",
                "Coaster East", "Coaster East/West", "Hillcrest/Campus A.M.",
                "Hillcrest/Campus P.M.", "Clockwise Campus Loop", "Counter Campus Loop",
                "Weekend Clockwise Campus Loop", "Weekend Counter Campus Loop", "Mesa",
                "Regents", "SIO Loop", "Sanford Consortium Shuttle", "Coaster West",
                "East Campus Connector"
        };

        for (int index = 0; index < buttonName.length; index++){

            toReturn.add(index, buttonName[index]);

        }

        return toReturn;
    }


    private ArrayList<Integer> createMapList() {
        // ids for the maps that pass through ucsd
        Integer[] mapId;
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

        for (int index = 0; index < mapId.length; index++){

            toReturn.add(index, mapId[index]);

        }
        return toReturn;
    }




    private ArrayList<Integer> createIconList(){
        // ids for the ucsd or mts icons
        Integer[] iconId;
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
