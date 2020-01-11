package com.example.textcomparator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.Map;

import adminfragments.AdminAccFragment;
import adminfragments.AdminFragmentInteractionListener;
import adminfragments.AdminRawQuerryFragment;
import adminfragments.AdminReturnQuerryFragment;

public class AdminActivity extends AppCompatActivity implements AdminFragmentInteractionListener {

    public final String NONE="none";
    public final String ACCLIST="acclist";
    public final String RAWQUERRY="rawqerry";
    public final String SELECTION="selection";


    private LinearLayout adminLayout;

    private Fragment fragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String[] spinnerChoices=new String[]{NONE,ACCLIST,RAWQUERRY,SELECTION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminLayout=findViewById(R.id.adminLayout);
        spinner=findViewById(R.id.adminSpinner);

        adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerChoices);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice=spinnerChoices[position];
                if(fragment!=null){
                    transaction=manager.beginTransaction();
                    transaction.remove(fragment);
                    transaction.commit();
                }

                if (!choice.equals(NONE)){

                    if (choice.equals(ACCLIST)) {
                        fragment= AdminAccFragment.newInstance();
                    }else if (choice.equals(RAWQUERRY)) {
                        fragment= AdminRawQuerryFragment.newInstance();
                    }else if (choice.equals(SELECTION)) {
                        fragment= AdminReturnQuerryFragment.newInstance();
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

        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();

    }

    @Override
    public void onFragmentInteraction(List<Map<String, String>> list) {

    }
}

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