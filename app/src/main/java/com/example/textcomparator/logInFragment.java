package com.example.textcomparator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;


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

    private Button unlockButton,signupbutton;

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

        username=           view.findViewById(R.id.username);
        usernameOrbGreen=   view.findViewById(R.id.usernameOrbGreen);
        usernameOrbRed=     view.findViewById(R.id.usernameOrbRed);

        password=           view.findViewById(R.id.password);
        passwordOrbGreen=   view.findViewById(R.id.passwordOrbGreen);
        passwordOrbRed=     view.findViewById(R.id.passwordOrbRed);

        unlockButton=       view.findViewById(R.id.unlockButton);
        signupbutton=       view.findViewById(R.id.createAccountButton);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockButtonClick();
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.signUp();
            }
        });

        usernameOrbGreen.setVisibility(View.INVISIBLE);
        usernameOrbRed.setVisibility(View.INVISIBLE);

        passwordOrbGreen.setVisibility(View.INVISIBLE);
        passwordOrbRed.setVisibility(View.INVISIBLE);

        return view;
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

        username.setText("");
        password.setText("");

        usernameOrbGreen.setVisibility(View.INVISIBLE);
        passwordOrbGreen.setVisibility(View.INVISIBLE);
        usernameOrbRed.setVisibility(View.INVISIBLE);
        passwordOrbRed.setVisibility(View.INVISIBLE);
    }

    public void unlockButtonClick(){

        Toast.makeText(getContext(),""+StashDataBase.getInstance(getContext()).listNumber(),Toast.LENGTH_SHORT).show();

        if (StashDataBase.getInstance(getContext()).isGod(username.getText().toString(),
                password.getText().toString())){
            Toast.makeText(getContext(),"welcome to god mode",Toast.LENGTH_SHORT).show();
            mListener.onFragmentInteraction(username.getText().toString(),
                    password.getText().toString());
        }else{
            try{
                StashDataBase.getInstance(getContext()).authenticate(username.getText().toString(),
                        password.getText().toString());

                usernameOrbGreen.setVisibility(View.VISIBLE);
                passwordOrbGreen.setVisibility(View.VISIBLE);
                usernameOrbRed.setVisibility(View.INVISIBLE);
                passwordOrbRed.setVisibility(View.INVISIBLE);

                mListener.onFragmentInteraction(username.getText().toString(),
                        password.getText().toString());

            }catch(IncorrectUsernameException e){

                Snackbar.make(unlockButton,e.getMessage(),Snackbar.LENGTH_SHORT).show();

                usernameOrbGreen.setVisibility(View.INVISIBLE);
                usernameOrbRed.setVisibility(View.VISIBLE);

                passwordOrbGreen.setVisibility(View.INVISIBLE);
                passwordOrbRed.setVisibility(View.INVISIBLE);

            }catch (IncorrectPasswordException e){

                Snackbar.make(unlockButton,e.getMessage(),Snackbar.LENGTH_SHORT).show();

                usernameOrbGreen.setVisibility(View.VISIBLE);
                usernameOrbRed.setVisibility(View.INVISIBLE);

                passwordOrbGreen.setVisibility(View.INVISIBLE);
                passwordOrbRed.setVisibility(View.VISIBLE);

            }
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
        void onFragmentInteraction(String username,String password);
        void signUp();
    }
}
