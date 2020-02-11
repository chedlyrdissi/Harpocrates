package com.example.harpocrates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class AccountDM extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "stash.db";
    protected static final String TABLE= "accounts";
    protected static final String COLUMN_ID = "_id";
    protected static final String COLUMN_USERNAME = "username";
    protected static final String COLUMN_PASSWORD= "password";
    protected static final String COLUMN_INFOID="info";

    public AccountDM(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT, "+COLUMN_INFOID+" Integer )";

        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addAccount(String username,String password){

        if (username==null || password==null) return;

        if(accountExists(username)){
            return;
        }

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_PASSWORD,password);
        db.insert(TABLE,null,values);
        db.close();
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

    public List<Info> getAccounts(){
        List<Info> list=new ArrayList<>();
        Info info;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * From "+ TABLE;
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            //get values
            info=new Info();
            info.ID=Integer.parseInt(cursor.getString(0));
            info.username=cursor.getString(1);
            info.pw=cursor.getString(2);

            list.add(info);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
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
}
