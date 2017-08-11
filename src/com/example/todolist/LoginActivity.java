package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity { 
	private EditText nameET,passwordET;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		nameET = (EditText) findViewById(R.id.nameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameValue = nameET.getText().toString();
				String passwordValue = passwordET.getText().toString();
				if("".equals(nameValue) || "".equals(passwordValue)){
					Toast.makeText(getApplicationContext(), "用户名或者密码不能为空！", Toast.LENGTH_LONG).show();
				}else if("1".equals(nameValue) && "1".equals(passwordValue)){
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "用户名或者密码错误！", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
	}
	
}
