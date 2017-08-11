package com.example.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.dao.ToDoDao;
import com.example.todolist.entity.ToDoItem;

public class NewToDoActivity extends Activity {
	private EditText toDoItemDetailET;
	private Button saveBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_to_do);
		toDoItemDetailET = (EditText) findViewById(R.id.toDoItemDetailET);
		saveBtn = (Button) findViewById(R.id.saveBtn);	
		saveBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String toDoItemDetailETValue = toDoItemDetailET.getText().toString();
				if("".equals(toDoItemDetailETValue)){
					Toast.makeText(getApplicationContext(), "待办事项不能为空！", Toast.LENGTH_LONG).show();
				}else{
					ToDoItem toDoItem = new ToDoItem();
					toDoItem.setUuid(UUID.randomUUID().toString());
					toDoItem.setToDoItemDetail(toDoItemDetailETValue);
					Date date = new Date();
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					toDoItem.setLastModifyDate(dateformat.format(date));
					ToDoDao toDoDao = new ToDoDao(NewToDoActivity.this);
					if(toDoDao.add(toDoItem)){
						setResult(Activity.RESULT_OK);
						finish();
					}else{
						Toast.makeText(NewToDoActivity.this, "Add note failed!", Toast.LENGTH_LONG).show();
					}
				}
			}
			
		});
		
	}
}
