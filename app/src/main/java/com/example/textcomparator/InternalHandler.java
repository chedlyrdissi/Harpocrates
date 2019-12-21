package com.example.textcomparator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stash.db";
    private static final String TABLE_ACCOUNT= "accounts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD= "password";

    public InternalHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_ACCOUNT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT )";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }

    public void addAccount(String username,String password){
        if (username==null || password==null) return;
        if(accountExists(username)){
            Toast.makeText(null,"the account exists already",Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_PASSWORD,password);
        db.insert(TABLE_ACCOUNT,null,values);
        db.close();
    }

    public boolean accountExists(String username){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT username ,password From "+
                TABLE_ACCOUNT+" WHERE username= ' "+username+" ' ";
        boolean result=db.rawQuery(query,null).getCount()>=0;
        db.close();
        return result;
    }

    public void deleteAccount(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT,"username= ' "+username+" ' ",null);
        db.close();
    }

    public void replace(String id,String username,String password){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_ACCOUNT+" SET "+COLUMN_USERNAME+"= ' "+username+" ' ," +
                ""+COLUMN_PASSWORD+"= ' "+password+" ' WHERE "+COLUMN_ID+" = "+id;
        db.rawQuery(query,null);
    }

    public int getIDByUsername(String username){
        SQLiteDatabase db=getReadableDatabase();
        String querry="SELECT "+COLUMN_ID+" FROM "+TABLE_ACCOUNT+" WHERE "+COLUMN_USERNAME+"= ' "+username+" ' ";
        int result=(db.rawQuery(querry,null)).getInt(0);
        db.close();
        return result;
    }

    public List<String> getAccounts(){
        List<String> list=new ArrayList<>();
        String str;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * From "+ TABLE_ACCOUNT;
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            //get values
            str="";
            for (int j=0;j<3;j++){
                str+=cursor.getString(j);
            }
            list.add(str);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public String getPassword(String username){
        String result="";
        try{
            int id=getIDByUsername(username);
            SQLiteDatabase db=getReadableDatabase();
            String querry="SELECT "+COLUMN_PASSWORD+" FROM "+TABLE_ACCOUNT+" WHERE "+COLUMN_ID+"="+id;
            result=db.rawQuery(querry,null).getString(0);
        }catch(Exception e){
            result=null;
        }
        return result;
    }
}
