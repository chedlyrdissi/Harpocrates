package com.example.harpocrates.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.harpocrates.Helper;

import java.util.ArrayList;
import java.util.List;

public class AccountDM extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION        = 2;
    public static final String DATABASE_NAME        = "harpocrates.db";
    public static final String TABLE                = "user";
    public static final String COLUMN_ID            = "user_id";
    public static final String COLUMN_USERNAME      = "username";
    public static final String COLUMN_PASSWORD      = "password";
    public static final String COLUMN_CREAION_DATE  = "creation_date";
    public static final String COLUMN_LAST_MODIFIED = "last_modified";

    Context context;

    public AccountDM(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE USER (\n" +
                "    user_id       INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
                "    username      STRING   NOT NULL\n" +
                "                           UNIQUE ON CONFLICT ABORT,\n" +
                "    password      STRING   NOT NULL,\n" +
                "    creation_date STRING,\n" +
                "    last_modified STRING     NOT NULL\n" +
                ");";

        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public User addAccount(String username, String password){

        boolean validUsername = validUsername( username );
        boolean validPassword = validPassword( password );

        if ( validUsername && validPassword ) {
            SQLiteDatabase db = null;
            long id;
            try {

                db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put( COLUMN_USERNAME, username );
                values.put( COLUMN_PASSWORD, password );
                values.put( COLUMN_CREAION_DATE, Helper.getDateTime() );
                values.put( COLUMN_LAST_MODIFIED, Helper.getDateTime() );

                id = db.insert( TABLE, null, values );

                if ( id < 0 ) {
                    throw new SQLException( "an error occured while inserting the user \n"
                            + " username : " + username + "\n"
                            + " password : " + password + "\n" );
                }

            } catch (Exception e) {
                throw new SQLException( "an error occured while inserting the user \n"
                        + " username : '" + username + "'\n"
                        + " password : '" + password + "'\n" );
            } finally {
                db.close();
            }

            return new User( id, username, password );
        } else if ( !validPassword && !validUsername ) {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " username : '" + username + "'\n"
                    + " password : '" + password + "'\n" );
        } else if ( !validPassword ) {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " password : '" + password + "'\n" );
        } else {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " username : " + username + "\n" );
        }
    }

    public boolean accountExists(String username){
        boolean result=false;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        try{
            String query="SELECT username From "+
                    TABLE+" WHERE username= '"+username+"' ";
            cursor=db.rawQuery(query,null);
            result=cursor.getCount()>0;
        }catch(Exception e){
        }finally{
            if(cursor!=null) cursor.close();
            db.close();
        }
        return result;
    }

    public int getAccountNumbers(){
        int result;
        SQLiteDatabase db=getReadableDatabase();
        String querry="SELECT * FROM "+TABLE;
        result=db.rawQuery(querry,null).getCount();
        db.close();
        return result;
    }

    public void deleteAccount(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE,"username= '"+username+"' ",null);
        db.close();
    }

    public void replace(int id,String username,String password){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE,COLUMN_ID+" = "+id,null);
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_ID,id);
        contentValues.put(COLUMN_USERNAME,username);
        contentValues.put(COLUMN_PASSWORD,password);
        db.insert(TABLE,null,contentValues);

        db.close();
    }

    public int getIDByUsername(String username){
        SQLiteDatabase db=getReadableDatabase();
        String querry="SELECT "+COLUMN_ID+" FROM "+TABLE+" WHERE "+COLUMN_USERNAME+"= '"+username+"' ";
        int result=(db.rawQuery(querry,null)).getInt(0);
        db.close();
        return result;
    }

    public List<User> getAccounts(){
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " +
                "user_id, " +
                "username, " +
                "password, " +
                "creation_date, " +
                "last_modified " +
                "From " + TABLE;
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        cursor.moveToPrevious();
        while ( cursor.moveToNext() ) {
            //get values
            long id = cursor.getInt(0);
            String username = cursor.getString(1);
            String pw = cursor.getString(2);
            String creationDate = cursor.getString(3);
            String lastModified = cursor.getString(4);

            list.add( new User( id, username, pw, creationDate, lastModified ) );
        }

        cursor.close();
        db.close();
        Toast.makeText( context, list.size() + "", Toast.LENGTH_SHORT ).show();
        return list;
    }

    public String getPassword(String username){
        String result;
        SQLiteDatabase db=getReadableDatabase();
        try{
            String querry="SELECT "+COLUMN_PASSWORD+" FROM "+TABLE+
                    " WHERE "+COLUMN_USERNAME+"='"+username+"'";
            Cursor cursor=db.rawQuery(querry,null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }else{
                result=null;
            }
            cursor.close();
        }catch(Exception e){
            result=null;
        }finally {
            db.close();
        }
        return result;
    }

    public void execute(String querry){
        SQLiteDatabase db=getWritableDatabase();
        db.rawQuery(querry,null);
        db.close();
    }

    private boolean validUsername( String username ) {
//        boolean isValid = false;
//
//        if ( username != null && !username.isEmpty() ) {
//            SQLiteDatabase db = getReadableDatabase();
//            String querry = " SELECT " + COLUMN_ID + " FROM " + TABLE
//                    + " WHERE " + COLUMN_USERNAME + " = '"+username+"' ";
//            try {
//                isValid = db.rawQuery(querry, null).getCount() == 0;
//            } catch ( Exception e ) {
//
//            } finally {
//                db.close();
//            }
//        }
//
//        return isValid;
        boolean isValid = false;
        if ( username != null && !username.isEmpty() ) {
            isValid = true;
        }
        return isValid;
    }

    private boolean validPassword( String pw ) {
        boolean isValid = false;
        if ( pw != null && !pw.isEmpty() ) {
            isValid = true;
        }
        return isValid;
    }

    public User getAccount( String username, String password ) {

        boolean validUsername = validUsername( username );
        boolean validPassword = validPassword( password );
        User user = null;
        if ( validUsername && validPassword ) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = getReadableDatabase();
                String[] columns = { COLUMN_ID, COLUMN_USERNAME };
                String selection = COLUMN_USERNAME + " = ? and " + COLUMN_PASSWORD + " = ? ;";
                String[] selectionArgs = { username, password };
                cursor = db.query(
                        TABLE,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        COLUMN_CREAION_DATE
                );

                // get querry results
                if ( cursor.moveToFirst() ) {
                    user = new User(
                            cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                    );
//                    if ( cursor.moveToNext()) {
//                        throw new duplicateUserException(
//                                cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
//                                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
//                        );
//                    }
                }

            } catch (Exception e) {
                throw new SQLException( "an error occured while fetching the user \n"
                        + " username : '" + username + "'\n"
                        + " password : '" + password + "'\n" );
            }  finally {
                db.close();
                cursor.close();
            }

            return user;
        } else if ( !validPassword && !validUsername ) {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " username : '" + username + "'\n"
                    + " password : '" + password + "'\n" );
        } else if ( !validPassword ) {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " password : '" + password + "'\n" );
        } else {
            throw new SQLException( "an error occured while inserting the user \n"
                    + " username : " + username + "\n" );
        }
    }
}

/*
CREATE TABLE sqlitestudio_temp_table AS SELECT *
                                          FROM USER;

DROP TABLE USER;

CREATE TABLE USER (
    user_id       INTEGER  PRIMARY KEY AUTOINCREMENT,
    username      STRING   NOT NULL
                           UNIQUE ON CONFLICT ABORT,
    password      STRING   NOT NULL,
    creation_date DATETIME,
    last_modified DATE     NOT NULL
);

INSERT INTO USER (
                     user_id,
                     username,
                     password,
                     creation_date,
                     last_modified
                 )
                 SELECT user_id,
                        username,
                        password,
                        creation_date,
                        last_modified
                   FROM sqlitestudio_temp_table;

DROP TABLE sqlitestudio_temp_table;
 */