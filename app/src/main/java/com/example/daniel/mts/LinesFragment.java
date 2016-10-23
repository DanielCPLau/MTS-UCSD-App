package com.example.daniel.mts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.*;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.lines_fragment,container, false);
        RemoteFetch fetch = new RemoteFetch();
        Line[] lines = fetch.getListOfLines("mts");
        String[] lineStrings = new String[lines.length];
        for(int i = 0; i < lines.length; i++)
        {
            lineStrings[i] = lines[i].id;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.rowlayout, R.id.txtitem,lineStrings);
        setListAdapter(adapter);
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return rootview;
    }

    public void onListItemClick(ListView view1, View view, int position, long id)
    {
        ViewGroup viewGroup = (ViewGroup)view;
        TextView txt = (TextView)viewGroup.findViewById(R.id.txtitem);
        Toast.makeText(getActivity(), txt.getText().toString(),Toast.LENGTH_LONG);
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
}
