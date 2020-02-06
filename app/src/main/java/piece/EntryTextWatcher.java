package piece;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.harpocrates.R;

import piece.entry.Entry;

public class EntryTextWatcher implements TextWatcher {

    private Context context;
    private EditText key;
    private EditText value;
    private Button btn;

    public EntryTextWatcher(Context context, EditText key, EditText value, Button btn ) {
        this.context = context;
        this.key = key;
        this.value = value;
        this.btn = btn;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        boolean validKey = Entry.isValidKey( key.getText().toString() );
        boolean validValue = Entry.isValidValue( value.getText().toString() );

        if ( validKey && validValue ) {

            btn.setEnabled(true);

            key.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));
            value.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

        } else if ( !validKey && validValue ) {

            btn.setEnabled(false);
            // only key is invalid
            key.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
            value.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

        } else if ( validKey && !validValue ) {

            btn.setEnabled(false);
            // only value is invalid
            value.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
            key.setBackgroundColor(ContextCompat.getColor( context, R.color.colorGreen));

        } else {

            btn.setEnabled(false);
            // both invalid
            key.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));
            value.setBackgroundColor(ContextCompat.getColor( context, R.color.colorRed));

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}