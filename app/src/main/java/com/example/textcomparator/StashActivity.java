package com.example.textcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StashActivity extends AppCompatActivity implements PieceFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stash);
    }

    @Override
    public void onListFragmentInteraction(Piece piece) {

    }
}
