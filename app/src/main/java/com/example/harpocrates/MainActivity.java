package com.example.harpocrates;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import piece.Piece;
import piece.PieceView;

public class MainActivity extends AppCompatActivity implements logInFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dialog dialog=new Dialog(MainActivity.this);

        //LinearLayout layout=findViewById(R.id.pieceMainLayout);
        Piece piece=new Piece("context");
        Map<String,String> map=new HashMap<>();
        for (int i=0;i<5;i++)
            map.put("param"+i,"param"+i);
        piece.setInfoMap(map);
        PieceView view=new PieceView(getApplicationContext(),piece);

        dialog.addContentView(
                view,
                new LinearLayout.LayoutParams(150,50)
        );

        dialog.show();
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
