package com.example.harpocrates;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.harpocrates.user.UserBean;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import piece.entry.Entry;

public class EntryDM extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "stash.db";
    protected static final String TABLE= "entry";
    protected static final String COLUMN_ID = "entry_id";
    protected static final String COLUMN_KEY = "key";
    protected static final String COLUMN_KEY_ISVISIBLE = "key_isvisible";
    protected static final String COLUMN_VALUE = "value";
    protected static final String COLUMN_VALUE_ISVISIBLE = "value_isvisible";
    protected static final String COLUMN_USER_ID = "user_id";
    protected static final String COLUMN_EFFECTIVE_USER_ID = "effective_user_id";
    protected static final String COLUMN_LAST_MODIFIED = "last_modified";

    private Context context;

    public EntryDM(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory ) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( getCreateStatement() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( getUpgradeStatement() );
        onCreate(sqLiteDatabase);
    }

    public boolean addEntry ( Entry entry ) {
        boolean result;
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            ContentValues values=new ContentValues();
            values.put( COLUMN_KEY                  , entry.getKey()            );
            values.put( COLUMN_VALUE                , entry.getValue()          );
            values.put( COLUMN_USER_ID              , entry.getKey()            );
            values.put( COLUMN_EFFECTIVE_USER_ID    , entry.getKey()            );
            values.put( COLUMN_LAST_MODIFIED        , Helper.getDateTime()      );
            values.put( COLUMN_KEY_ISVISIBLE        , entry.isKeyVisible()      );
            values.put( COLUMN_VALUE_ISVISIBLE      , entry.isValueVisible()    );
            values.put( COLUMN_USER_ID, UserBean.getInstance().getCurrentUser().getID() );

            if ( UserBean.getInstance().hasHiddenUser() ) {
                values.put( COLUMN_EFFECTIVE_USER_ID, UserBean.getInstance().getHiddenUser().getID() );
            } else {
                values.put( COLUMN_EFFECTIVE_USER_ID, UserBean.getInstance().getCurrentUser().getID() );
            }

            db.insert(TABLE,null,values);
            result = true;
        } catch ( Exception e ) {
            Toast.makeText( context, e.getMessage(),Toast.LENGTH_LONG );
            result = true;
        } finally {
            db.close();
        }

        return result;
    }

    public Entry getEntry ( Integer entryID ) {
        throw new NullPointerException("method getEntry not implemented ");
    }

    public List<Entry> getUserEntries (Integer userID ) {
        throw new NullPointerException("method getEntry not implemented ");
    }

    public List<Entry> getTitleEntries (Integer userID ) {
        throw new NullPointerException("method getEntry not implemented ");
    }

    protected String getUpgradeStatement() {
        return "DROP TABLE IF EXISTS " + TABLE;
    }

    protected String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY, "
                + COLUMN_KEY                + " TEXT                NOT NULL,"
                + COLUMN_KEY_ISVISIBLE      + " BOOLEAN             DEFAULT 1, "
                + COLUMN_VALUE              + " TEXT                NOT NULL, "
                + COLUMN_VALUE_ISVISIBLE    + " BOOLEAN             DEFAULT 1, "
                + COLUMN_USER_ID            + " INTEGER             NOT NULL, "
                + COLUMN_EFFECTIVE_USER_ID  + " INTEGER             NOT NULL, "
                + COLUMN_LAST_MODIFIED      + " TEXT, "
                + " foreign key ( " + AccountDM.COLUMN_ID + " ) references "
                + AccountDM.TABLE + "ON DELETE CASCADE ON UPDATE NO ACTION "
                + " );";
    }
}
