package com.example.textcomparator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link logInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link logInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class logInFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText username;
    private ImageView usernameOrbGreen;
    private ImageView usernameOrbRed;

    private EditText password;
    private ImageView passwordOrbGreen;
    private ImageView passwordOrbRed;

    private Button unlockButton;

    public logInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment logInFragment.
     */
    public static logInFragment newInstance() {
        logInFragment fragment = new logInFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, true);
        username= view.findViewById(R.id.username);
        usernameOrbGreen= view.findViewById(R.id.usernameOrbGreen);
        usernameOrbRed= view.findViewById(R.id.usernameOrbRed);
        password= view.findViewById(R.id.password);
        passwordOrbGreen= view.findViewById(R.id.passwordOrbGreen);
        passwordOrbRed= view.findViewById(R.id.passwordOrbRed);
        unlockButton= view.findViewById(R.id.unlockButton);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockButtonClick();
            }
        });

        usernameOrbGreen.setVisibility(View.INVISIBLE);
        usernameOrbRed.setVisibility(View.INVISIBLE);

        passwordOrbGreen.setVisibility(View.INVISIBLE);
        passwordOrbRed.setVisibility(View.INVISIBLE);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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

    public void unlockButtonClick(){
        String user=username.getText().toString();
        String pass=password.getText().toString();
        boolean usernameExists=StashDataBase.getInstance().usernameExists(user);

        if (usernameExists){
            usernameOrbGreen.setVisibility(View.VISIBLE);
            if (StashDataBase.getInstance().checkPassword(user,pass)){
                passwordOrbRed.setVisibility(View.INVISIBLE);
                passwordOrbGreen.setVisibility(View.VISIBLE);


            }else{
                passwordOrbRed.setVisibility(View.VISIBLE);
                passwordOrbGreen.setVisibility(View.INVISIBLE);
            }
        }else{
            usernameOrbGreen.setVisibility(View.INVISIBLE);
            usernameOrbRed.setVisibility(View.VISIBLE);
        }

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
        void onFragmentInteraction();
    }
}
