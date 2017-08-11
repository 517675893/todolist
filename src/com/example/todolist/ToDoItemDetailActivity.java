package com.example.todolist;

import com.example.todolist.entity.ToDoItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ToDoItemDetailActivity extends Activity {
	private TextView lastModifyDateTV2;
	private TextView toDoItemDetailTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_item_detail);
		toDoItemDetailTV = (TextView) findViewById(R.id.toDoItemDetailTV);
		lastModifyDateTV2 = (TextView) findViewById(R.id.lastModifyDateTV2);
		ToDoItem item = (ToDoItem)getIntent().getSerializableExtra("toDoItem");  
		toDoItemDetailTV.setText(item.getToDoItemDetail());
		lastModifyDateTV2.setText("最后修改时间: "+item.getLastModifyDate());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_back) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	
	}
}
