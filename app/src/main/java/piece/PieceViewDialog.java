package piece;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import piece.entry.ModifyEntryDialog;

public class PieceViewDialog extends Dialog {

    private Context context;

    private Piece piece;
    private EditText title;

    private ListView listView;
    private Adapter adapter;

    private EditText newItemKey, newItemValue;
    private TextWatcher textWatcher;

    private Button addItemBtn;

    public PieceViewDialog(@NonNull final Context context, @NonNull final Piece piece ) {
        super(context);

        this.context = context;
        this.piece = piece;
        setContentView(R.layout.piece_view_dialog_layout);

        title = findViewById( R.id.pieceDialogTitle );

        listView = findViewById( R.id.pieceViewDialogItemsListView );
        //adapter = new ArrayAdapter<>( context, android.R.layout.simple_list_item_1, piece.getItems() );
        adapter = new ArrayAdapter<>( context, R.layout.entry_item_list_item, piece.getItems() );
        listView.setAdapter((ListAdapter) adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ( new ModifyEntryDialog( getContext(), piece.get(i)) ).show();
                return true;
            }
        });

        newItemKey = findViewById( R.id.pieceDialogNewPieceKey );
        newItemValue = findViewById( R.id.pieceDialogNewPieceValue );
        addItemBtn = findViewById( R.id.pieceDialogAddItemBtn );

        textWatcher = new EntryTextWatcher( getContext(), newItemKey, newItemValue, addItemBtn );
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
}
