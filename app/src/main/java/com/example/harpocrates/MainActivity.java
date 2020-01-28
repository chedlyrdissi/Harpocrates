package com.example.harpocrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import piece.Piece;
import piece.PieceRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements logInFragment.OnFragmentInteractionListener {


    RecyclerView recycler;
    PieceRecyclerViewAdapter adapter;

    /*
    ArrayAdapter adapter;
    ListView recycler;
    */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler=findViewById(R.id.recyclerMainAct);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new PieceRecyclerViewAdapter(getApplicationContext(),Helper.createDummyList(7,2));
        recycler.setAdapter(adapter);

        /*
        ArrayList<String> list=new ArrayList<>();
        for (int i=0;i<20; i++) list.add("playing around with the list "+i);
        adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        recycler.setAdapter(adapter);
        */


    }

    @Override
    public void signUp() {
        Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
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

}
