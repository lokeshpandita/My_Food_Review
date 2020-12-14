package com.food.myfoodreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.*;
import java.util.*;

public class DataBaseHelper  extends SQLiteOpenHelper
{  
   
	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String TABLE_NAME = "calorie";


	//constructor
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME , null, 1);
	}



	public Integer deleteData(String s)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_NAME, "ID = ?", new String[]{s});

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(
			"create table calorie " +
			"(id integer primary key, date text,carbohydrate text,protein text, fat text,food text)"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS calorie");
		onCreate(db);
	}
	public boolean insertDetail (String date, String carbohydrate, String protien, String fat,String food) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("date", date);
		contentValues.put("carbohydrate", carbohydrate);
		contentValues.put("protein", protien);
		contentValues.put("fat", fat);
		contentValues.put("food", food);
		db.insert("calorie", null, contentValues);
		return true;
	}
	public Cursor getAllData(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res = db.rawQuery("Select * From " + TABLE_NAME,null);
		return res;
	}

	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return numRows;
	}
}
	

	

