 package com.example.todolist.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.todolist.entity.ToDoItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ToDoDao {
	
	private ToDoDBOpenHelper helper;
	
	public ToDoDao(Context context){
		helper = new ToDoDBOpenHelper(context);
	}
	
	public boolean find(String uuid){
		boolean result = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select * from todoitem where uuid =?", new String[]{uuid});
			if(cursor.moveToFirst()){
				result = true;
			}
			cursor.close();
			db.close();
		}
		return result;
	} 
	
	
	public boolean add(ToDoItem toDoItem){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("insert into todoitem (uuid,toDoItemDetail,lastModifyDate) values(?,?,?)", new Object[]{toDoItem.getUuid(),toDoItem.getToDoItemDetail(),toDoItem.getLastModifyDate()});
			db.close();
		}
		return find(toDoItem.getUuid());
	}
	
	public void delete(String uuid){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from todoitem where uuid=?", new Object[]{uuid});
			db.close();
		}
	}
	
	public void update(String uuid,String toDoItemDetail,String lastModifyDate){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("update todoitem set toDoItemDetail=?,lastModifyDate=? where uuid=?", new Object[]{toDoItemDetail,lastModifyDate,uuid});
			db.close();
		}
	}
	
	public List<ToDoItem> findAll(){
		List<ToDoItem> toDos = new ArrayList<ToDoItem>();
		SQLiteDatabase db = helper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select _id,uuid,toDoItemDetail,lastModifyDate from todoitem",null);
			while(cursor.moveToNext()){
				ToDoItem toDoItem = new ToDoItem();
				toDoItem.setId(cursor.getInt(0));
				toDoItem.setUuid(cursor.getString(1));
				toDoItem.setToDoItemDetail(cursor.getString(2));
				toDoItem.setLastModifyDate(cursor.getString(3));
				toDos.add(toDoItem);
			}
			cursor.close();
			db.close();
		}
		return toDos;
	}

}
