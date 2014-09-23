package com.wuyuan.android.activity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaixin.android.R;
import com.wuyuan.android.result.ConcurrentJobResult;
import com.wuyuan.android.utils.ActivityForResultUtil;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.HttpPostParam;
import com.wuyuan.android.utils.SyncHttp;

public class WorkPubActivity extends Activity{

		private Button mBack;
		private EditText mTitle;
		private EditText mSalary;
		private EditText mLimitPerson;
		private EditText mWorkTime;
		private EditText mWorkPath;
		private EditText mContractMan;
		private EditText mContractPhone;
		private EditText mContent;
		private Button mSubmitBtn;
		
		private String title;
		private String Salary;
		private String LimitPerson;
		private String WorkTime;
		private String WorkPath;
		private String ContractMan;
		private String ContractPhone;
		private String Content;
		private String LimitTime;
		private String Imagepath;
		
		
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.work_pub);
			findViewById();
			setListener();
			
			
		}
		
		private void init() {
			Imagepath = "/storage/emulated/0/Download/NASAEarth-01.jpg";
			title = mTitle.getText().toString();   
			Salary = mSalary.getText().toString();
			LimitPerson = mLimitPerson.getText().toString();
			WorkTime = mWorkTime.getText().toString();
			WorkPath = mWorkPath.getText().toString();
			ContractMan = mContractMan.getText().toString();
			ContractPhone = mContractPhone.getText().toString();
			Content = mContent.getText().toString();
			System.out.println(title+Salary);
		}
		
		private void findViewById() {
			mBack = (Button) findViewById(R.id.work_pub_back);
			mTitle = (EditText)findViewById(R.id.work_pub_title);
			mSalary = (EditText)findViewById(R.id.work_pub_money);
			mLimitPerson =(EditText)findViewById(R.id.work_pub_num);
			mWorkTime =(EditText)findViewById(R.id.work_pub_worktime);
			mWorkPath =(EditText)findViewById(R.id.work_pub_workpath);
			mContractMan =(EditText)findViewById(R.id.work_pub_linkman);
			mContractPhone =(EditText)findViewById(R.id.work_pub_phone);
			mContent =(EditText)findViewById(R.id.work_pub_content);
			mSubmitBtn =(Button)findViewById(R.id.work_pub_submit);
		}

		private void setListener() {
			mBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 关闭当前界面
					finish();
				}
			});
			mSubmitBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
				    try {
				    	new PostWorkThread().start();
				    	/*if(title.equals("")){
					    	Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
					    }else{
					    	new PostWorkThread().start();
						    Toast.makeText(getApplicationContext(), "发表成功", Toast.LENGTH_SHORT).show();
					    }*/
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			
		}

		class PostWorkThread extends Thread{
			  public void run(){
				init();	
				System.out.println("count---------------->"+new ConcurrentJobResult().getCount());
			    DateFormat df1 = DateFormat.getDateInstance();
			    LimitTime = df1.format(new Date()).toString();
			    
			    
//				Intent intent = getIntent();
//				Bundle bundle = intent.getExtras();
//				count = bundle.getInt("count") ;
			    ActivityForResultUtil.MYSQLCOUNTNUM = ActivityForResultUtil.MYSQLCOUNTNUM + 1;
				Log.i("work--------------->ActivityForResultUtil.MYSQLCOUNTNUM", ActivityForResultUtil.MYSQLCOUNTNUM+"");
			    
			    
			    List <HttpPostParam> params = new ArrayList <HttpPostParam>(); 
			    params.add(new HttpPostParam("title", title)); 
			    params.add(new HttpPostParam("image", Imagepath)); 
			    params.add(new HttpPostParam("current_job_id",ActivityForResultUtil.MYSQLCOUNTNUM+"")); 
			    params.add(new HttpPostParam("Salary",Salary));
			    params.add(new HttpPostParam("limit_time",LimitTime));
			    params.add(new HttpPostParam("person_limit",LimitPerson));
			    params.add(new HttpPostParam("work_time",WorkTime));
			    params.add(new HttpPostParam("place",WorkPath));
			    params.add(new HttpPostParam("phone",ContractPhone));
			    params.add(new HttpPostParam("content",Content));
			    params.add(new HttpPostParam("contract_person",ContractMan));
			    SyncHttp http = new SyncHttp();
			    try
			    {
			      String rst = http.post(GetAddressUtil.WORK_PUBLIC, params);
			      System.out.println("result--------------->"+rst);
			    } catch (Exception e)
			    {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }
			  }
			}
}
