package piece;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;

import androidx.annotation.Nullable;

import com.example.harpocrates.Helper;
import com.example.harpocrates.account.AccountDM;
import com.example.harpocrates.account.InexistantUserException;
import com.example.harpocrates.account.UserBean;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class TitleDM extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION        = 2;
    public static final String DATABASE_NAME        = "harpocrates.db";
    public static final String TABLE                = "title";
    public static final String COLUMN_ID            = "title_id";
    public static final String COLUMN_TITLE         = "title";
    public static final String COLUMN_USER_ID       = AccountDM.COLUMN_ID;
    public static final String COLUMN_CREAION_DATE  = "creation_date";
    public static final String COLUMN_LAST_MODIFIED = "last_modified";

    public static final String[] ALLCOLUMNS = {
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_USER_ID,
            COLUMN_CREAION_DATE,
            COLUMN_LAST_MODIFIED
    };

    public static final String[] REGULARSELECTCOLUMNS = { COLUMN_ID, COLUMN_TITLE };

    private static final String TEMPTABLENAME       = "title_temp_table";

    public TitleDM(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( getCreateTableStatement() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( getCreateTempTable() );
        db.execSQL( getDropTableStatement( TABLE ) );
        onCreate(db);
        db.execSQL( getPopulateTempStatement() );
        db.execSQL( getDropTableStatement( TEMPTABLENAME ) );
    }

    public List<Piece> getTitles( long user_id  ) throws InexistantUserException {
        if ( validUserID( user_id ) ) {
            List<Piece> list = new ArrayList<>();

            // prepare querry
            String selection = COLUMN_USER_ID + " = ?";
            String[] selectionArgs = { Long.toString( user_id ) };
            Cursor resultSet = null;
            SQLiteDatabase db = null;
            try {

                // run querry
                db = getReadableDatabase();
                resultSet = db.query(
                        TABLE,
                        REGULARSELECTCOLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        COLUMN_CREAION_DATE
                );

                // get querry results
                resultSet.moveToFirst();
                resultSet.moveToPrevious();
                while ( resultSet.moveToNext() ) {
                    list.add( new Piece(
                            resultSet.getInt(resultSet.getColumnIndex(COLUMN_ID)),
                            resultSet.getString(resultSet.getColumnIndex(COLUMN_TITLE))
                    ) );
                }

            } catch ( Exception e ) {
                throw new SQLException( "error running the querry \n"
                + " the following is the original exception message \n "
                + e.getMessage() );
            } finally {
                resultSet.close();
                db.close();
            }

            return list;
        } else {
            throw new InexistantUserException( user_id );
        }
    }

    public Piece createTitle( String title ) {
        if ( validTitle( title ) ) {
            SQLiteDatabase db = null;
            long id;

            try {
                db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put( COLUMN_TITLE, title );
                values.put( COLUMN_USER_ID, UserBean.getInstance().getCurrentUser().getID() );
                values.put( COLUMN_CREAION_DATE, Helper.getDateTime() );
                values.put( COLUMN_LAST_MODIFIED, Helper.getDateTime() );

                id = db.insert( TABLE, null, values );
            } catch (Exception e) {
                throw new SQLException( "an error occured while inserting the title " + title );
            } finally {
                db.close();
            }

            return new Piece( id, title );
        } else {
            throw new IllegalArgumentException( " the title " + title + " is an illegal argument" );
        }
    }

    public boolean updateTitle( long id, String title ) throws IllegalArgumentException {

        boolean isValidID = validID( id );
        boolean isValidtitle = validTitle( title );

        if ( isValidID && isValidtitle ) {

            SQLiteDatabase db = null;
            boolean result;
            try {
                db = getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put( COLUMN_TITLE, title );
                values.put( COLUMN_LAST_MODIFIED, Helper.getDateTime() );

                String selection = COLUMN_USER_ID + " = ?";
                String[] selectionArgs = { Long.toString( id ) };

                int count = db.update(
                        TABLE,
                        values,
                        selection,
                        selectionArgs
                );

                if ( count == 1 ) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                throw new SQLException( "an error occured while inserting the title " + title );
            } finally {
                db.close();
            }

            return result;

        } else if ( !isValidID && !isValidtitle ) {
            throw new IllegalArgumentException( " the title " + title + " is an illegal argument \n"
                                                + " the id " + id + " is invalid " );
        } else if ( !isValidID ) {
            throw new IllegalArgumentException( " the id " + id + " is invalid " );
        } else {
            throw new IllegalArgumentException( " the title " + title + " is an illegal argument" );
        }
    }

    protected static String getCreateTableStatement() {
        return "CREATE TABLE TITLE (\n"
                + COLUMN_ID             + " INTEGER  PRIMARY KEY AUTOINCREMENT\n"
                + "                           NOT NULL,\n"
                + COLUMN_TITLE          + " TEXT     NOT NULL,\n"
                + COLUMN_USER_ID        + " INTEGER  REFERENCES USER (user_id) \n"
                + "                           NOT NULL,\n"
                + COLUMN_CREAION_DATE   + " DATETIME NOT NULL,\n"
                + COLUMN_LAST_MODIFIED  + " DATETIME NOT NULL\n"
                + ");";
    }

    protected static String getPopulateTempStatement() {
        return "INSERT INTO TITLE (\n"
                + COLUMN_ID             + ",\n"
                + COLUMN_TITLE          + ",\n"
                + COLUMN_USER_ID        + ",\n"
                + COLUMN_CREAION_DATE   + ",\n"
                + COLUMN_LAST_MODIFIED  + " \n"
                + "                        )\n"
                + "            SELECT " + ",\n"
                + COLUMN_ID             + ",\n"
                + COLUMN_TITLE          + ",\n"
                + COLUMN_USER_ID        + ",\n"
                + COLUMN_CREAION_DATE   + ",\n"
                + COLUMN_LAST_MODIFIED  + " \n"
                + " FROM "              + " \n"
                + TEMPTABLENAME + ";";
    }

    protected static String getCreateTempTable() {
        return "CREATE TABLE " + TEMPTABLENAME
                + " AS SELECT * FROM " + TABLE + ";";
    }

    protected static String getDropTableStatement( String tableName ) {
        String statement = null;
        if ( validTableName( tableName ) ) {
            statement = "DROP TABLE IF EXISTS " + tableName + ";";
        }
        return statement;
    }

    private static boolean validTableName( String tableName ) {
        return ( tableName != null )
                && ( tableName.equalsIgnoreCase( TABLE ) || tableName.equalsIgnoreCase( TEMPTABLENAME ) );
    }

    //TODO to implement
    private static boolean validUserID ( long user_id ) {
        return true;
    }

    // TODO to implement
    private static boolean validTitle ( String title ) {
        return title != null && !title.isEmpty();
    }

    // TODO to implement
    private static boolean validID ( long id ) {
        return id > -1;
    }

}
