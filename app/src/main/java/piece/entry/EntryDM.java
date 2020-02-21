package piece.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.harpocrates.account.AccountDM;
import com.example.harpocrates.Helper;
import com.example.harpocrates.account.UserBean;

import java.util.List;

import piece.TitleDM;

public class EntryDM extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "harpocrates.db";
    public static final String TABLE= "entry";
    public static final String COLUMN_ID = "entry_id";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_KEY_ISVISIBLE = "key_isvisible";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_VALUE_ISVISIBLE = "value_isvisible";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EFFECTIVE_USER_ID = "effective_user_id";
    public static final String COLUMN_LAST_MODIFIED = "last_modified";
    public static final String COLUMN_TITLE_ID = "title_id";

    public static final String TEMPTABLENAME = "entry_temp_table";
    private static boolean DEFAULTISVISIBLE = true;

    public EntryDM(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory ) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( getCreateStatement() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( getCreateTempTable() );
        sqLiteDatabase.execSQL( getDropTableStatement( TABLE ) );
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL( getPopulateTempStatement() );
        sqLiteDatabase.execSQL( getDropTableStatement( TEMPTABLENAME ) );
    }

    public boolean addEntry ( int titleID, String key, String value ) {
        return addEntry( titleID, key, value, true, true );
    }

    public boolean addEntry ( int titleID, String key, String value, boolean keyVisible, boolean valueVisible )
            throws EntryDBException {

        boolean result = false;
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            ContentValues values=new ContentValues();
            values.put( COLUMN_KEY               , key                   );
            values.put( COLUMN_VALUE             , value                 );
            values.put( COLUMN_LAST_MODIFIED     , Helper.getDateTime()  );
            values.put( COLUMN_KEY_ISVISIBLE     , keyVisible            );
            values.put( COLUMN_VALUE_ISVISIBLE   , valueVisible          );
            values.put( COLUMN_TITLE_ID          , titleID               );
            values.put( COLUMN_USER_ID           , UserBean.getInstance().getCurrentUser().getID() );

            if ( UserBean.getInstance().hasHiddenUser() ) {
                values.put( COLUMN_EFFECTIVE_USER_ID, UserBean.getInstance().getHiddenUser().getID() );
            } else {
                values.put( COLUMN_EFFECTIVE_USER_ID, UserBean.getInstance().getCurrentUser().getID() );
            }

            db.insert(TABLE,null,values);
            result = true;
        } catch ( Exception e ) {
            throw new EntryDBException( key, value, " error adding entry ", e.getMessage() );
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

    protected String getCreateTempTable() {
        return "CREATE TABLE " + TEMPTABLENAME
                + " AS SELECT * FROM " + TABLE + ";";
    }

    protected String getDropTableStatement( String tableName ) {
        String statement = null;
        if ( validTableName( tableName ) ) {
            statement = "DROP TABLE IF EXISTS " + tableName + ";";
        }
        return statement;
    }

    protected String getPopulateTempStatement() {
        return "INSERT INTO " + TABLE               + " (\n"
                + " "  + COLUMN_ID                  + ",\n"
                + " [" + COLUMN_KEY                 + "],\n"
                + " "  + COLUMN_KEY_ISVISIBLE       + ",\n"
                + " "  + COLUMN_VALUE               + ",\n"
                + " "  + COLUMN_VALUE_ISVISIBLE     + ",\n"
                + " "  + COLUMN_USER_ID             + ",\n"
                + " "  + COLUMN_EFFECTIVE_USER_ID   + ",\n"
                + " "  + COLUMN_LAST_MODIFIED       + ",\n"
                + " "  + COLUMN_TITLE_ID            + "\n"
                + " )\n"
                + " SELECT "    + COLUMN_ID                 + ",\n"
                + " \""         + COLUMN_KEY                + "\",\n"
                + " "           + COLUMN_KEY_ISVISIBLE      + ",\n"
                + " "           + COLUMN_VALUE              + ",\n"
                + " "           + COLUMN_VALUE_ISVISIBLE    + ",\n"
                + " "           + COLUMN_USER_ID            + ",\n"
                + " "           + COLUMN_EFFECTIVE_USER_ID  + ",\n"
                + " "           + COLUMN_LAST_MODIFIED      + ",\n"
                + " "           + COLUMN_TITLE_ID           + "\n"
                + " FROM " + TEMPTABLENAME + ";";
    }

    protected String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY, AUTOINCREMENT"
                + "[" + COLUMN_KEY + "]"    + " STRING              NOT NULL,"
                + COLUMN_KEY_ISVISIBLE      + " BOOLEAN             DEFAULT ("
                                            + DEFAULTISVISIBLE + "), "
                + COLUMN_VALUE              + " STRING              NOT NULL, "
                + COLUMN_VALUE_ISVISIBLE    + " BOOLEAN             DEFAULT ("
                                            + DEFAULTISVISIBLE + "), "
                + COLUMN_USER_ID            + " INTEGER REFERENCES " + AccountDM.TABLE
                                            + " (" + AccountDM.COLUMN_ID + ") " + " NOT NULL, "
                + COLUMN_EFFECTIVE_USER_ID  + " INTEGER REFERENCES " + AccountDM.TABLE
                                            + " (" + AccountDM.COLUMN_ID + ") " + " NOT NULL, "
                + COLUMN_LAST_MODIFIED      + " DATETIME, "
                + COLUMN_TITLE_ID           + " INTEGER  REFERENCES " + TitleDM.TABLE
                                            + " (" + TitleDM.COLUMN_ID + ") NOT NULL"
                + " );";
    }

    private boolean validTableName( String tableName ) {
        return ( tableName != null )
                && ( tableName.equalsIgnoreCase( TABLE ) || tableName.equalsIgnoreCase( TEMPTABLENAME ) );
    }
}
