package piece.entry;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.harpocrates.R;
import com.example.harpocrates.StashDataBase;

import piece.EntryTextWatcher;

public class ModifyEntryDialog extends Dialog {

    private Context context;
    private Entry entry;

    private EditText key,value;
    private Button save;

    private EntryTextWatcher watcher;

    public ModifyEntryDialog(@NonNull Context context, final Entry entry) {
        super(context);
        setContentView(R.layout.entry_modify_dialog_layout);

        this.context = context;
        this.entry = entry;

        key = findViewById( R.id.entryModifyDialogKey );
        value = findViewById( R.id.entryModifyDialogValue );
        save = findViewById( R.id.entryModifyDialogSaveBtn );

        key.setText( entry.getKey() );
        value.setText( entry.getValue() );

        watcher = new EntryTextWatcher( getContext(), key, value, save );

        key.addTextChangedListener(watcher);
        value.addTextChangedListener(watcher);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( StashDataBase.getInstance(getContext()).updateEntry(entry.getId(), key.getText().toString(), value.getText().toString()) ){
                    dismiss();
                }
            }
        });
    }
}
