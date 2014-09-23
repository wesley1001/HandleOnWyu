package com.wuyuan.android.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kaixin.android.R;
import com.wuyuan.android.KXActivity;


/**
 * 启动引导页
 * @author ◥◣松雲鶴羽◢◤--子龍
 */
public class StartActivity extends KXActivity implements Runnable {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		// 启动一个线程
		new Thread(this).start();
	}

	public void run() {
		try {
			// 一秒后跳转到登录界面
			Thread.sleep(1000);
			startActivity(new Intent(StartActivity.this, LoginActivity.class));
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}