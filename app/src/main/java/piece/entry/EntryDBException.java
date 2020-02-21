package piece.entry;

import android.database.sqlite.SQLiteException;

public class EntryDBException extends SQLiteException {

    public EntryDBException ( String key, String value, String extraMessage, String origMessage) {
        super( extraMessage          + "\n"
                + " key: "   + key   + "\n"
                + " value: " + value + "\n"
                + origMessage );
    }

}
