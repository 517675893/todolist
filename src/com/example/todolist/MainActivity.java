package com.example.todolist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.todolist.dao.ToDoDao;
import com.example.todolist.entity.ToDoItem;


public class MainActivity extends Activity {
	private ListView listView;
	private LinearLayout emptyToDoView;
	private List<ToDoItem> toDoListValues = new ArrayList<ToDoItem>();
	private static final int LOAD_FINISHED = 200;
	private static final int NEW_CODE = 201;
	private static final int EDIT_CODE = 202;
	private ToDoAdapter toDoAdaper;
	private static final int EDIT_ID = 1;
	private static final int DELETE_ID = 2;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == LOAD_FINISHED) {
				toDoAdaper = new ToDoAdapter();
				listView.setAdapter(toDoAdaper);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.todoListView);
		emptyToDoView = (LinearLayout) findViewById(R.id.emptyToDoView);
		listView.setEmptyView(emptyToDoView);
		loadToDoListItems();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ToDoItem toDoItem = (ToDoItem) toDoAdaper.getItem(position);
				openToDoListItemDetailPage(toDoItem);
			}

		});
		registerForContextMenu(listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new) {
			startActivityForResult(new Intent(this, NewToDoActivity.class),
					NEW_CODE);
		}
		if (id == R.id.action_logout) {
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确认要退出吗?");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener() {
		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadToDoListItems() {
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				ToDoDao toDoDao = new ToDoDao(MainActivity.this);
				toDoListValues = toDoDao.findAll();
				Message msg = Message.obtain();
				msg.what = LOAD_FINISHED;
				handler.sendMessage(msg);
			}

		}.start();
	}

	private class ToDoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return toDoListValues.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return toDoListValues.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.listview_item_to_do, null);
				holder = new ViewHolder();
				holder.toDoItemDetailTv = (TextView) convertView
						.findViewById(R.id.toDoItemDetailTv);
				holder.lastModifyDateTV = (TextView) convertView
						.findViewById(R.id.lastModifyDateTV);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ToDoItem todoItem = toDoListValues.get(position);
			holder.toDoItemDetailTv.setText(todoItem.getToDoItemDetail());
			holder.lastModifyDateTV.setText(String.valueOf(todoItem
					.getLastModifyDate()));
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView toDoItemDetailTv;
		TextView lastModifyDateTV;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
//			if (requestCode == NEW_CODE || ) {
				// Coming back from the edit view, update the view
				loadToDoListItems();
//			}

		}
	}

	private void openToDoListItemDetailPage(ToDoItem toDoItem) {
		Intent i = new Intent(this, ToDoItemDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("toDoItem", toDoItem);
		i.putExtras(bundle);
		startActivity(i);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("列表操作: ");
		menu.add(0, EDIT_ID, 0, "编辑");
		menu.add(0, DELETE_ID, 0, "删除");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final ToDoItem toDoItem = (ToDoItem) toDoAdaper.getItem(info.position);
		switch (item.getItemId()) {
		case EDIT_ID:
			openToDoListItemEditActivity(toDoItem);
			return true;
		case DELETE_ID:
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确认要删除这条记录吗?");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener() {
		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ToDoDao toDoDao = new ToDoDao(MainActivity.this);
					toDoDao.delete(toDoItem.getUuid());
					loadToDoListItems();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void openToDoListItemEditActivity(ToDoItem toDoItem){
		Intent i = new Intent(this, EditToDoItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("toDoItem", toDoItem);
		i.putExtras(bundle);
		startActivityForResult(i, EDIT_CODE);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
