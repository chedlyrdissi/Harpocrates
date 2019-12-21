package com.example.textcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

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
        AccountType type=StashDataBase.getInstance(getApplicationContext()).getAccountType(user,pass);
        if (type==AccountType.irregular){
            Snackbar.make(null,"account does not exist", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else if (type==AccountType.admin){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }, 1000);
        }else if (type==AccountType.regular){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(MainActivity.this, RegularActivity.class);
                    startActivity(intent);
                }
            }, 1000);
        }


    }

    @Override
    public void signUp() {
        Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
