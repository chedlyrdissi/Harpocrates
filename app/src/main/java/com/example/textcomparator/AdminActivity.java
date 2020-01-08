package com.example.textcomparator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AdminActivity extends AppCompatActivity {

    public final String NONE="none";
    public final String ACCLIST="acclist";
    public final String RAWQUERRY="rawqerry";
    public final String SELECTQUERRY="selectquerry";


    private LinearLayout adminLayout;
    private Fragment fragment;

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String[] spinnerChoices=new String[]{NONE,ACCLIST,RAWQUERRY,SELECTQUERRY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminLayout=findViewById(R.id.adminLayout);
        spinner=findViewById(R.id.adminSpinner);

        adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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