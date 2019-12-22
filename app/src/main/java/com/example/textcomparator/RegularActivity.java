package com.example.textcomparator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RegularActivity extends AppCompatActivity implements PieceFragment.OnListFragmentInteractionListener {

    private PieceFragment listFragment;
    private Toolbar toolbar;
    private List<Piece> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);

        list=new ArrayList<>();
        list.add(new Piece("cont1"));
        list.add(new Piece("cont2"));
        listFragment=PieceFragment.newInstance(2,list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Piece piece) {

    }
}
