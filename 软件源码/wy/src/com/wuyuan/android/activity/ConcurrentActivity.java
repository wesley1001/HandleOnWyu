package com.wuyuan.android.activity;

import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.result.ConcurrentJobResult;
import com.wuyuan.android.result.LostResult;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ConcurrentActivity extends Activity{

	private Button mBack;
	private ImageButton mCall;
	private TextView mTitle;
	private TextView mSalary;
	private TextView mLimitPersonNum;
	private TextView mLimitSex;
	private TextView mLimitTime;
	private TextView mPlace;
	private TextView mContent;
	private TextView mContractPerson;
	private TextView mPhone;
	private int position;
	private ConcurrentJobResult result = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_detail);
		findViewById();
		setListener();
		init();
	}
	

	private void setListener() {
		// TODO Auto-generated method stub
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try 
		        { 
		          String strInput = "15659350289";
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

	private void findViewById() {
		// TODO Auto-generated method stub
		mBack = (Button)findViewById(R.id.lost_back);
		mCall = (ImageButton)findViewById(R.id.concurrent_detail_imgphone);
		mTitle = (TextView)findViewById(R.id.concurrent_detail_title);
		mSalary = (TextView)findViewById(R.id.concurrent_detail_salary);
		mLimitPersonNum = (TextView)findViewById(R.id.concurrent_detail_Limit_person);
		mLimitSex = (TextView)findViewById(R.id.concurrent_detail_limitsex);
		mLimitTime = (TextView)findViewById(R.id.concurrent_detail_work_time);
		mPlace = (TextView)findViewById(R.id.concurrent_detail_work_path);
		mContent = (TextView)findViewById(R.id.concurrent_detail_content);
		mContractPerson = (TextView)findViewById(R.id.concurrent_detail_contractPerson);
		mPhone = (TextView)findViewById(R.id.concurrent_detail_phonetext);
	}
	
	private void init() {
		Intent intent = getIntent();
		position = getIntent().getIntExtra("position", position) - 1;		
		try {
			result = KXApplication.mConcurrentJobResults.get(position);
			System.out.println("position-------------------->"+position);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		mTitle.setText(result.getTitle());
		mSalary.setText(result.getSalary());
		mLimitPersonNum.setText(result.getPerson_limit());
		mLimitSex.setText(result.getSex());
		mLimitTime.setText(result.getLimit_time());
		mPlace.setText(result.getPlace());
		mContent.setText(result.getContent());
		mContractPerson.setText(result.getContract_person());
		mPhone.setText(result.getPhone());
	}
}