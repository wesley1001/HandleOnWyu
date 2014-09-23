package com.wuyuan.android.activity;

import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.result.LostResult;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class LostDetailContent extends Activity{
	private Button mBack;
	private ImageButton mCall;
	private TextView mLostTitle;
	private TextView mLostTime;
	private TextView mLostContent;
	private TextView mLost_phone;
	private TextView mLost_contract_person;
	private TextView mLost_place;
	private TextView mQQ;
	private int position = 1;
	private int flag ;
	private LostResult result= null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lost_detail);
		findViewById();
		init();
		setListener();		
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.lost_back);
		mLostTitle = (TextView) findViewById(R.id.lost_title);
		mLostTime = (TextView) findViewById(R.id.lost_time);
		mLostContent = (TextView) findViewById(R.id.lost_content);
		mLost_phone = (TextView) findViewById(R.id.lost_phone);
		mLost_place = (TextView) findViewById(R.id.lost_place);	
		mQQ = (TextView) findViewById(R.id.lost_qq);	
		mLost_contract_person = (TextView) findViewById(R.id.lost_contract_person);
		mCall = (ImageButton) findViewById(R.id.lost_imgphone);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try 
		        { 
		          String strInput = mLost_phone.getText().toString();
		          Intent myIntentDial = new  
		          Intent("android.intent.action.CALL",Uri.parse("tel:"+strInput)); 
		          startActivity(myIntentDial);
		        } 
		        catch(Exception e) 
		        { 
		          e.printStackTrace(); 
		        }
			}
		});
	}

	private void init() {
		position = getIntent().getIntExtra("position", position);		
		flag = getIntent().getIntExtra("flag",flag);		
		Log.i("flag----->", flag+"");
		try {
			if(flag == 0){
				result = KXApplication.mPickResults.get(position);
			}else if(flag == 1){
				result = KXApplication.mLostResults.get(position);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		mLostTitle.setText("物件 ："+result.getTitle());
		mLostTime.setText("时间 ："+result.getTime());
		mLostContent.setText("补充内容 ："+result.getContent());
		mLost_phone.setText(result.getPhone());
		mLost_place.setText("地点 ："+result.getPlace());
		mQQ.setText("QQ ："+result.getQQ());
		mLost_contract_person.setText(""+result.getContract_person());
	}

}
