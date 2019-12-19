package com.example.textcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements logInFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onFragmentInteraction(String username,String password) {
        final String user=new String(username);
        final String pass=new String(password);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                AccountType type=StashDataBase.getInstance().getAccountType(user,pass);
                if (type==AccountType.irregular){

                }else if (type==AccountType.admin){
                    Intent intent=new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }else if (type==AccountType.regular){
                    Intent intent=new Intent(MainActivity.this, StashActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);

    }
}
