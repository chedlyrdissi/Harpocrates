package com.example.harpocrates;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

import adminfragments.AdminAccFragment;
import adminfragments.AdminFragmentInteractionListener;
import adminfragments.AdminFragmentsChoices;
import adminfragments.AdminRawQuerryFragment;
import adminfragments.AdminSelectionFragment;

import static adminfragments.AdminFragmentsChoices.ACCLIST;
import static adminfragments.AdminFragmentsChoices.NONE;
import static adminfragments.AdminFragmentsChoices.RAWQUERRY;
import static adminfragments.AdminFragmentsChoices.SELECTION;

public class AdminActivity extends AppCompatActivity implements AdminFragmentInteractionListener {

    private LinearLayout adminLayout;
    private Fragment fragment;

    private ArrayAdapter adapter;
    private Spinner spinner;
    private AdminFragmentsChoices[] spinnerChoices=new AdminFragmentsChoices[]{ NONE, ACCLIST, RAWQUERRY, SELECTION };

    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminLayout=findViewById(R.id.adminLayout);
        spinner=findViewById(R.id.adminSpinner);
        proceedButton=findViewById(R.id.adminProceedButton);

        adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerChoices);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminFragmentsChoices selection=(AdminFragmentsChoices) spinner.getSelectedItem();
                if ( selection == ACCLIST ) {
                    removeFragmentIfExists();
                    fragment= AdminAccFragment.newInstance();
                    placeFragment();
                } else if ( selection == RAWQUERRY ) {
                    removeFragmentIfExists();
                    fragment= AdminRawQuerryFragment.newInstance();
                    placeFragment();
                } else if ( selection == SELECTION ) {
                    removeFragmentIfExists();
                    fragment= AdminSelectionFragment.newInstance();
                    placeFragment();
                } else if ( selection == NONE ) {
                    Toast.makeText(getApplicationContext(),"please select a destination", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(List<Map<String, String>> list) {

    }

    private void removeFragmentIfExists() {
        if( fragment!=null ){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragment = null;
        }
    }

    private void placeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.adminLayout, fragment);
        transaction.commit();
    }

}


/*
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AdminFragmentsChoices choice=spinnerChoices[position];


                if (!choice.equals(NONE)){

                    if (choice.equals(ACCLIST)) {
                        fragment= AdminAccFragment.newInstance();
                    }else if (choice.equals(RAWQUERRY)) {
                        fragment= AdminRawQuerryFragment.newInstance();
                    }else if (choice.equals(SELECTION)) {
                        fragment= AdminSelectionFragment.newInstance();
                    }

                    transaction=manager.beginTransaction();
                    transaction.add(R.id.adminLayout, fragment);
                    transaction.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
 */

/*

item dialog
final Dialog dialog=new Dialog(AdminActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_OPTIONS_PANEL);
                dialog.setContentView(R.layout.account_selection_layout);

                final String user=list.get(position).username;
                String pw=list.get(position).pw;

                Toast.makeText(AdminActivity.this,user+" "+pw,Toast.LENGTH_SHORT).show();

                final EditText username=dialog.findViewById(R.id.DialogUsername);
                final EditText password=dialog.findViewById(R.id.DialogPassword);
                Button update=dialog.findViewById(R.id.DialogUpdateButton);
                Button delete=dialog.findViewById(R.id.DialogDeleteButton);

                username.setText(user);
                password.setText(pw);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StashDataBase.getInstance(getApplicationContext()).updateAcocunt(list.get(position).ID,
                                username.getText().toString(),password.getText().toString());
                        sync.performClick();
                        dialog.dismiss();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StashDataBase.getInstance(getApplicationContext()).deleteAcocunt(user);
                        sync.performClick();
                        dialog.dismiss();
                    }
                });

                dialog.show();


 insert dialog
 final Dialog dialog=new Dialog(AdminActivity.this);
                dialog.setContentView(R.layout.sql_insert_dialog);

                final EditText user=dialog.findViewById(R.id.usernameValue);
                final EditText pw=dialog.findViewById(R.id.passwordValue);
                Button button=dialog.findViewById(R.id.insertValueButton);
                final TextView error=dialog.findViewById(R.id.errorMessageInInputDialog);
                error.setVisibility(View.GONE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String u,p;
                        u=user.getText().toString();
                        p=pw.getText().toString();
                        if(Helper.validPassword(p) && Helper.validUserName(u)){
                            try{
                                StashDataBase.getInstance(getApplicationContext()).createAccount(u,p);
                                sync.performClick();
                                dialog.dismiss();
                            }catch(Exception e){
                                error.setText(e.getMessage());
                                error.setVisibility(View.VISIBLE);
                            }finally {
                                //dialog.dismiss();
                            }
                        }
                    }
                });
                dialog.show();

 */