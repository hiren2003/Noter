package com.example.noter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DB_HELPER extends SQLiteOpenHelper {

    private static  final  String DB_NAME="dbnoter";
    private static  final  int VERSION_NUM=1;
    private static  final  String TB_NAME="tblnoter";
    private static  final  String KEY_ID="id";
    private static  final  String KEY_TITLE="title";
    private static  final  String KEY_DESCRIPTION="des";

    public DB_HELPER(Context context) {
        super(context, DB_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+" ( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+KEY_TITLE+" TEXT , "+ KEY_DESCRIPTION+" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
    }
    public void add(String title,String des){
      SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
      ContentValues contentValues=new ContentValues();
      contentValues.put(KEY_TITLE,title);
      contentValues.put(KEY_DESCRIPTION,des);
      sqLiteDatabase.insert(TB_NAME,null,contentValues);
    }
    public ArrayList<ClsModel> fetch() {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<ClsModel> arrayList =new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TB_NAME,null);
        while (cursor.moveToNext()){
            int id;
            String ttl,des;
            id=cursor.getInt(0);
            ttl=cursor.getString(1);
            des=cursor.getString(2);
            ClsModel model =new ClsModel(id,ttl,des);
            arrayList.add(model);
        }
        return arrayList;
    }
    public void del(int id){
            SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
            sqLiteDatabase.delete(TB_NAME,KEY_ID+" = "+id,null);
    }
    public void update(ClsModel model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_TITLE,model.Title);
        contentValues.put(KEY_DESCRIPTION,model.Description);
        sqLiteDatabase.update(TB_NAME,contentValues,KEY_ID+" = "+model.Id,null);
    }
}
