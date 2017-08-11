package com.example.todolist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBOpenHelper extends SQLiteOpenHelper {
	public ToDoDBOpenHelper(Context context) {
		super(context, "todo.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL("create table todoitem (_id integer primary key autoincrement,uuid text,toDoItemDetail text, lastModifyDate text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
