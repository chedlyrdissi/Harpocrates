package com.example.harpocrates;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import piece.Piece;
import piece.PieceRecyclerViewAdapter;

public class RegularActivity extends AppCompatActivity {

    private PieceFragment listFragment;
    private Toolbar toolbar;
    private List<Piece> list;
    protected FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);

        list=new ArrayList<>();
        Piece piece;

        for (int i=0; i<5; i++) {

            piece=new Piece("Title " + i );
            for ( int j=0; j<5; j++) {
                piece.add( "key" + j, "value" +j );
            }

            list.add(piece);

        }

        if (findViewById(R.id.RegularFragmentLayout) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            /*
            if (savedInstanceState != null) {
                return;
            }
             */

            // Create a new Fragment to be placed in the activity layout
            listFragment=new PieceFragment(list);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.RegularFragmentLayout, listFragment).commit();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog=new Dialog(RegularActivity.this);
                dialog.setContentView(R.layout.add_context_dialog);

                final EditText context=dialog.findViewById(R.id.contextToAdd);
                Button add=dialog.findViewById(R.id.createContextButton);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message;
                        if(Helper.usableContext(context.getText().toString())){
                            list.add(new Piece(context.getText().toString()));
                            message="context created";
                            dialog.dismiss();
                        }else{
                            message="the context is inusable";
                        }
                        Toast.makeText(getApplicationContext(),message
                                ,Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });
    }

    public void sync(){
        listFragment.sync();
    }

        /*
        final Dialog dialog=new Dialog(RegularActivity.this);
        dialog.setContentView(R.layout.piece_modifier_dialog);

        LinearLayout mainlayout=dialog.findViewById(R.id.mainLayout);
        EditText context=dialog.findViewById(R.id.pieceContextInDialog);
        Button update=dialog.findViewById(R.id.updatePieceInDialogButton);
        EditText[] keys=new EditText[list.size()];
        EditText[] values=new EditText[list.size()];
        LinearLayout[] layouts=new LinearLayout[list.size()];
        Map<String,String> map=piece.getInfoMap();
        context.setText(piece.getContext());

        int i=1;
        for(String k:map.keySet()){
            keys[i]=new EditText(getApplicationContext());
            EditText equals=new EditText(getApplicationContext());
            values[i]=new EditText(getApplicationContext());
            layouts[i]= new LinearLayout(getApplicationContext());

            equals.setText("=");
            keys[i].setText(k);
            values[i].setText(map.get(k));
            layouts[i].setOrientation(LinearLayout.HORIZONTAL);

            layouts[i].addView(keys[i]);
            layouts[i].addView(equals);
            layouts[i].addView(values[i]);

            mainlayout.addView(layouts[i],i++);

        }

        dialog.show();
        */

}
