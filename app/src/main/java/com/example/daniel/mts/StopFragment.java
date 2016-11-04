package com.example.daniel.mts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment extends Fragment {
    private TextView line;
    private TextView stopName;
    private TextView directionName;
    private TextView prediction;
    private ImageButton refresh;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PREDICTION_LIMIT = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StopFragment newInstance(String param1, String param2) {
        StopFragment fragment = new StopFragment();
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
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_stop, container,false);

        // Set the Text to try this out
        StopActivity act = (StopActivity) getActivity();
        String stopId = act.getStopId();
        String lineId = act.getLineId();
        Stop stop = new Stop(stopId, lineId);

        String color = "#" + stop.color;
        String lineShortNameString = stop.lineShortName;
        String stopNameString = stop.name;
        String lineDirectionNameString = stop.directionName;
        ArrayList<Integer> predictions = RemoteFetch.getPrediction(stopId, lineId);

        line = (TextView) myInflatedView.findViewById(R.id.txtitem);
        stopName = (TextView) myInflatedView.findViewById(R.id.nameItem);
        directionName = (TextView) myInflatedView.findViewById(R.id.direction);
        refresh = (ImageButton) myInflatedView.findViewById(R.id.reverse);

        GradientDrawable tvBackground = (GradientDrawable) line.getBackground();
        tvBackground.setColor(Color.parseColor(color));

        line.setText(lineShortNameString);
        line.setTextColor(Color.WHITE);

        view = line.getRootView();

        stopName.setText(stopNameString);
        stopName.setTextColor(Color.BLACK);

        directionName.setText("To " + lineDirectionNameString);
        directionName.setTextColor(Color.DKGRAY);

        String times;
        if( predictions.size() > 0) {
            times = "Next bus in ";
            for (int i = 0; i < predictions.size(); i++) {
                int time = predictions.get(i);

                if( time == 0) {
                    times += "Arriving";
                }
                else {
                    times += predictions.get(i);
                }

                if (i < predictions.size() - 1) times += ", ";
                if (i >= PREDICTION_LIMIT) break;
            }
            times += " minutes";
        }
        else {
            times = "There is currently no prediction.";
        }

        prediction = (TextView) myInflatedView.findViewById(R.id.prediction);
        prediction.setText(times);
        prediction.setTextColor(Color.BLACK);

        return myInflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
