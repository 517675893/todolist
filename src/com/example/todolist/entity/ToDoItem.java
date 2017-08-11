package com.example.todolist.entity;

import java.io.Serializable;

public class ToDoItem  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1938210493029085120L;
	private int id;
	private String uuid;
	private String toDoItemDetail;
	private String lastModifyDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getToDoItemDetail() {
		return toDoItemDetail;
	}
	public void setToDoItemDetail(String toDoItemDetail) {
		this.toDoItemDetail = toDoItemDetail;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
	
}
