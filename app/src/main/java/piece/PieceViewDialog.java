package piece;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.harpocrates.R;

import piece.entry.Entry;

public class PieceViewDialog extends Dialog {

    private Context context;

    private Piece piece;
    private EditText title;

    private ListView listView;
    private Adapter adapter;

    private EditText newItemKey, newItemValue;
    private TextWatcher textWatcher;

    private Button addItemBtn;

    public PieceViewDialog(@NonNull final Context context, @NonNull  Piece piece ) {
        super(context);

        this.context = context;
        this.piece = piece;
        setContentView(R.layout.piece_view_dialog_layout);

        title = findViewById( R.id.pieceDialogTitle );

        listView = findViewById( R.id.pieceViewDialogItemsListView );
        //adapter = new ArrayAdapter<>( context, android.R.layout.simple_list_item_1, piece.getItems() );
        adapter = new ArrayAdapter<>( context, R.layout.entry_item_list_item, piece.getItems() );
        listView.setAdapter((ListAdapter) adapter);

        newItemKey = findViewById( R.id.pieceDialogNewPieceKey );
        newItemValue = findViewById( R.id.pieceDialogNewPieceValue );
        addItemBtn = findViewById( R.id.pieceDialogAddItemBtn );

        textWatcher = new EntryTextWatcher();
        newItemKey.addTextChangedListener( textWatcher );
        newItemValue.addTextChangedListener( textWatcher );

        title.setText( piece.gettitle() );

        addItemBtn.setEnabled( false );

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"btn is enabled", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class EntryTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            boolean validKey = Entry.isValidKey( newItemKey.getText().toString() );
            boolean validValue = Entry.isValidValue( newItemValue.getText().toString() );

            if ( validKey && validValue ) {

                addItemBtn.setEnabled(true);

                newItemKey.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));
                newItemValue.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

            } else if ( !validKey && validValue ) {

                addItemBtn.setEnabled(false);
                // only key is invalid
                newItemKey.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
                newItemValue.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

            } else if ( validKey && !validValue ) {

                addItemBtn.setEnabled(false);
                // only value is invalid
                newItemValue.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
                newItemKey.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

            } else {

                addItemBtn.setEnabled(false);
                // both invalid
                newItemKey.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
                newItemValue.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

/*
        // set layout
        LinearLayout layout;
        // prepate items
        EditText key, value;
        LinearLayout.LayoutParams eqParams;
        LinearLayout.LayoutParams kvparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0,45);
        for ( Entry item:piece.getItems() ) {
            key = new EditText( context );
            key.setLayoutParams( kvparams );
            key.setText( item.getKey() );
            key.setTextColor( Color.WHITE );

            eqParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0,10);
            TextView equals = new TextView( context );
            equals.setLayoutParams( eqParams );
            equals.setText(" = ");
            equals.setTextColor( Color.WHITE );
            equals.setTextSize(16);
            equals.setGravity(View.TEXT_ALIGNMENT_CENTER);

            value = new EditText( context );
            value.setLayoutParams( kvparams );
            value.setText( item.getValue() );
            key.setTextColor( Color.WHITE );

            layout = new LinearLayout( context );
            layout.setLayoutParams( new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
            layout.setOrientation( LinearLayout.HORIZONTAL );

            layout.addView(key);
            layout.addView(equals);
            layout.addView(value);

            itemsLayout.addView(layout);
        }
        */
