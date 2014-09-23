package com.wuyuan.android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kaixin.android.R;
import com.wuyuan.android.KXActivity;

/**
 * 登录界面
 * 
 * @author rendongwei
 * 
 */
public class LoginActivity extends KXActivity {
	/**
	 * 登录按钮
	 */
	private Button mLogin;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		findViewById();
		setListener();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mLogin = (Button) findViewById(R.id.login_activity_login);
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		// 登录按钮监听
		mLogin.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("null")
			public void onClick(View v) {
				// 跳转到功能引导页
				
				
				startActivity(new Intent(LoginActivity.this,
						MainActivity.class));
				finish();
				
//				SharedPreferences sp = getSharedPreferences("anzlking",MODE_PRIVATE);
//				//Boolean judge = sp.getBoolean("flag", true);
//				String judge = sp.getString("judge", "logined");
//				if(judge.equals("logined")){
//					startActivity(new Intent(LoginActivity.this,
//							MainActivity.class));
//					finish();
//					
//				}else{
//					Editor editor = sp.edit();
//	                editor.putBoolean("flag", true);
//	                editor.commit();
//	                
//	                startActivity(new Intent(LoginActivity.this,
//							GuideActivity.class));
//					finish();
//				}
				
                
				
			}
		});
	}

	public void onBackPressed() {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
