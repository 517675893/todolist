package com.example.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.dao.ToDoDao;
import com.example.todolist.entity.ToDoItem;

@SuppressLint("SimpleDateFormat")
public class EditToDoItemActivity extends Activity {
	private EditText toDoItemDetailET;
	private Button saveToDoItemBtn;
	private ToDoItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_to_do_item);
		toDoItemDetailET = (EditText) findViewById(R.id.toDoItemDetailET);
		saveToDoItemBtn = (Button) findViewById(R.id.saveToDoItemBtn);
		item = (ToDoItem) getIntent().getSerializableExtra("toDoItem");
		toDoItemDetailET.setText(item.getToDoItemDetail());
		saveToDoItemBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String toDoItemDetailETValue = toDoItemDetailET.getText().toString();
				if("".equals(toDoItemDetailETValue)){
					Toast.makeText(getApplicationContext(), "待办事项不能为空,请重新编辑！", Toast.LENGTH_LONG).show();
				}else{
					ToDoDao toDoDao = new ToDoDao(EditToDoItemActivity.this);
					Date date = new Date();
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					toDoDao.update(item.getUuid(), toDoItemDetailETValue, dateformat.format(date));
					setResult(Activity.RESULT_OK);
					finish();
				}
				
			}
		});
		
	}
}
