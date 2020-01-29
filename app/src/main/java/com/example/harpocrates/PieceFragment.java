package com.example.harpocrates;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import piece.Piece;
import piece.PieceRecyclerViewAdapter;

public class PieceFragment extends Fragment {

    public static final int LINEAR_LAYOUT_VERTICAL=0;
    public static final int LINEAR_LAYOUT_HORIZONTAL=1;
    public static final int GRID_LAYOUT_2_COL=2;
    public static final int GRID_LAYOUT_3_COL=3;

    private List<Piece> pieces;
    private PieceRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PieceFragment () {}

    public PieceFragment( List<Piece> pieces ) {
        this(pieces,LINEAR_LAYOUT_VERTICAL);
    }

    public PieceFragment( List<Piece> pieces, int layout ) {
        initializeFragment(pieces,layout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //List<Piece> list;
        //RecyclerView.LayoutManager manager;

        if ( savedInstanceState != null
                && savedInstanceState.getBundle("list") != null
                && savedInstanceState.getBundle("list").get("pieces") != null ) {
            //list = (List<Piece>) savedInstanceState.getBundle("list").get("pieces");
            initializeFragment( (List<Piece>) savedInstanceState.getBundle("list").get("pieces") );
        } else {
            //list = new ArrayList<Piece>();
            initializeFragment( new ArrayList<Piece>() );
        }

        View view = inflater.inflate(R.layout.fragment_piece_list, container, false);
        recyclerView = view.findViewById(R.id.pieceList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void initializeFragment( List<Piece> pieces, int layout ) {
        this.pieces=pieces;
        adapter=new PieceRecyclerViewAdapter( getContext() ,pieces);
        switch ( layout ) {
            case LINEAR_LAYOUT_VERTICAL:    manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                break;
            case LINEAR_LAYOUT_HORIZONTAL:    manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                break;
            case GRID_LAYOUT_2_COL:         manager=new GridLayoutManager(getContext(),GRID_LAYOUT_2_COL);
                break;
            case GRID_LAYOUT_3_COL:         manager=new GridLayoutManager(getContext(),GRID_LAYOUT_3_COL);
                break;
        }
    }

    public void initializeFragment( List<Piece> pieces) {
        initializeFragment(pieces,LINEAR_LAYOUT_VERTICAL);
    }

    public void setList(List<Piece> list){
        this.pieces=list;
        adapter.setPieces(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //TODO
    public void sync(){
        adapter.notifyDataSetChanged();
    }

}
