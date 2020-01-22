package com.example.harpocrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

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

    RecyclerView list1;
    PieceRecyclerViewAdapter adapter;
    List<Piece> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieces=new ArrayList<>();
        Piece piece;

        for (int i=0; i<10; i++) {

            piece=new Piece("Title " + i );
            for ( int j=0; j<5; j++) {
                piece.add( "key" + j, "value" +j );
            }

            pieces.add(piece);

        }

        list1=findViewById(R.id.list1);
        list1.setLayoutManager(new LinearLayoutManager(this ));
        adapter=new PieceRecyclerViewAdapter(getApplicationContext(),pieces);
        list1.setAdapter(adapter);
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
