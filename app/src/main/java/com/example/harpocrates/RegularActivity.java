package com.example.harpocrates;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import piece.Piece;
import piece.PieceViewDialogContext;

public class RegularActivity extends AppCompatActivity implements PieceViewDialogContext {

    private PieceFragment listFragment;
    private Toolbar toolbar;
    private List<Piece> list;
    protected FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);

        list = Helper.createDummyList(30,5);

        if ( listFragment != null ) {
            getSupportFragmentManager().beginTransaction().remove(listFragment).commit();
        }

        listFragment=new PieceFragment(list);
        getSupportFragmentManager().beginTransaction().add(R.id.regularFragmentLayout, listFragment).commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog=new Dialog(RegularActivity.this);
                dialog.setContentView(R.layout.add_title_dialog);

                final EditText title=dialog.findViewById(R.id.dialogTitleToAdd);
                Button add=dialog.findViewById(R.id.dialogCreateTitleButton);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message;
                        if(Helper.usableTitle(title.getText().toString())){
                            Piece piece = StashDataBase.getInstance(getApplicationContext()).createTitle( title.getText().toString() );
                            list.add( piece );
                            message="title created";
                            dialog.dismiss();
                        }else{
                            message="the title is inusable";
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

    @Override
    public boolean isValidKey(String key) {
        return false;
    }

    @Override
    public boolean isValidValue(String value) {
        return false;
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
