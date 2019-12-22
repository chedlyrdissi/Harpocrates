package com.example.textcomparator;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.telecom.StatusHints;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView listview;
    private List<Info> list;
    private ArrayAdapter adapter;
    private ImageView sqlExecutor,isqlInsertmageView,sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listview=findViewById(R.id.AccountList);

        list=StashDataBase.getInstance(getApplicationContext()).getAccountList();
        adapter=new ArrayAdapter<>(this, R.layout.simple_list_item_custom, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
            }
        });

        sqlExecutor=findViewById(R.id.sqlImageView);
        sqlExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(AdminActivity.this);
                dialog.setContentView(R.layout.sql_dialog);

                final EditText sql=dialog.findViewById(R.id.sqlText);
                Button execute=dialog.findViewById(R.id.executeQuerryButton);
                final TextView error=dialog.findViewById(R.id.errorMessageInInputDialog2);
                error.setVisibility(View.GONE);

                execute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String querry=sql.getText().toString();
                        try{
                            StashDataBase.getInstance(getApplicationContext()).executeQuerry(querry);
                            Toast.makeText(AdminActivity.this,
                                    StashDataBase.getInstance(getApplicationContext()).listNumber(),
                                    Toast.LENGTH_LONG).show();
                            sync.performClick();
                            dialog.dismiss();
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(),"the querry is broken",
                                    Toast.LENGTH_SHORT).show();
                            error.setText("the querry * "+querry+" * is broken +"+e.getMessage());
                            error.setVisibility(View.VISIBLE);
                        }

                    }
                });
                dialog.show();
            }
        });

        isqlInsertmageView=findViewById(R.id.isqlInsertmageView);
        isqlInsertmageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        sync=findViewById(R.id.updateAccountListImageView);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                adapter.addAll(StashDataBase.getInstance(getApplicationContext()).getAccountList());
                //list=StashDataBase.getInstance(getApplicationContext()).getAccountList();
                adapter.notifyDataSetChanged();
            }
        });
    }

}
