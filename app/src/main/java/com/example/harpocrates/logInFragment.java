package com.example.harpocrates;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harpocrates.account.AdminUser;
import com.example.harpocrates.account.User;
import com.example.harpocrates.account.UserBean;
import com.google.android.material.snackbar.Snackbar;


public class logInFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText username;
    private ImageView usernameOrb;

    private EditText password;
    private ImageView passwordOrb;

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
        usernameOrb=   view.findViewById(R.id.usernameOrb);

        password=           view.findViewById(R.id.password);
        passwordOrb=   view.findViewById(R.id.passwordOrb);

        unlockButton=       view.findViewById(R.id.unlockButton);
        signupbutton=       view.findViewById(R.id.createAccountButton);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), StashDataBase.getInstance(getContext()).listNumber() + "",Toast.LENGTH_SHORT).show();
                unlockButtonClick();
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.signUp();
            }
        });

        usernameOrb.setVisibility(View.GONE);
        passwordOrb.setVisibility(View.GONE);

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

        clearLogInFragmentInfo();
    }

    public void clearLogInFragmentInfo() {
        username.setText("");
        password.setText("");
        usernameOrb.setVisibility(View.GONE);
        passwordOrb.setVisibility(View.GONE);
    }

    public void unlockButtonClick(){

        if (StashDataBase.getInstance(getContext()).isGod(username.getText().toString(),
                password.getText().toString())){
            Toast.makeText(getContext(),"welcome to god mode",Toast.LENGTH_SHORT).show();
            UserBean.getInstance().setCurrentUser( new AdminUser(-1,"","admin"));
            mListener.onFragmentInteraction(username.getText().toString(),
                    password.getText().toString());
        }else{
            try{
                User user = StashDataBase.getInstance(getContext()).authenticate(username.getText().toString(),
                        password.getText().toString());

                usernameOrb.setVisibility(View.VISIBLE);
                passwordOrb.setVisibility(View.VISIBLE);

                usernameOrb.setImageDrawable(getResources().getDrawable(R.drawable.correct));
                passwordOrb.setImageDrawable(getResources().getDrawable(R.drawable.correct));

                UserBean.getInstance().setCurrentUser( user );
                mListener.onFragmentInteraction(username.getText().toString(),
                        password.getText().toString());

            }catch(IncorrectUsernameException e){

                Snackbar.make(unlockButton,e.getMessage(),Snackbar.LENGTH_SHORT).show();

                usernameOrb.setImageDrawable(getResources().getDrawable(R.drawable.error));
                usernameOrb.setVisibility(View.VISIBLE);

                passwordOrb.setVisibility(View.GONE);

            }catch (IncorrectPasswordException e){

                Snackbar.make(unlockButton,e.getMessage(),Snackbar.LENGTH_SHORT).show();

                passwordOrb.setImageDrawable(getResources().getDrawable(R.drawable.error));
                passwordOrb.setVisibility(View.VISIBLE);

                usernameOrb.setVisibility(View.GONE);

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
