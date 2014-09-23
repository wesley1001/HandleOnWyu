package com.wuyuan.android.activity;

import java.util.ArrayList;
import java.util.List;

import com.kaixin.android.R;
import com.wuyuan.android.menu.Desktop.onChangeViewListener;
import com.wuyuan.android.utils.ActivityForResultUtil;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.HttpPostParam;
import com.wuyuan.android.utils.SyncHttp;
import com.wuyuan.android.utils.ViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LostPubActivity extends Activity{

		private Button mBack;
		private Button mSubmitBtn;
		private RadioGroup mCheckLostOrPick;
		private RadioButton mCheckPickBtn;
		private RadioButton mCheckLostBtn;
		private EditText mTitle;
		private EditText mPath;
		private EditText mLostTime;
		private EditText mLinkMan;
		private EditText mPhone;
		private EditText mQQ;
		private EditText mContent;
		
		private String Title = null;
		private String Path = null;
		private String LostTime = null;
		private String LinkMan = null;
		private String Phone = null;
		private String QQ = null;
		private String Content = null;
		private int flag = 0;
		/**
		 * 接口对象,用来修改显示的View
		 */
		private onChangeViewListener mOnChangeViewListener;
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.lost_pub);
			findViewById();
			setListener();
			
		}
		private void init() {
			Title = mTitle.getText().toString();
			Path = mPath.getText().toString();
			LostTime = mLostTime.getText().toString();
			LinkMan = mLinkMan.getText().toString();
			Phone = mPhone.getText().toString();
			QQ = mQQ.getText().toString();
			Content = mContent.getText().toString();
		}
		private void findViewById() {
			mBack = (Button) findViewById(R.id.lost_pub_back);
			mSubmitBtn = (Button) findViewById(R.id.lost_pub_sub_btn);
			mCheckLostOrPick = (RadioGroup)findViewById(R.id.two_select);
			mTitle = (EditText)findViewById(R.id.lost_pub_title);
			mPath = (EditText)findViewById(R.id.lost_pub_path);
			mLostTime = (EditText)findViewById(R.id.lost_pub_losttime);
			mLinkMan = (EditText)findViewById(R.id.lost_pub_linkman);
			mPhone = (EditText)findViewById(R.id.lost_pub_phone);
			mQQ = (EditText)findViewById(R.id.lost_pub_qq);
			mContent = (EditText)findViewById(R.id.lost_pub_content);
			mCheckPickBtn = (RadioButton)findViewById(R.id.two_pup_pick);
			mCheckLostBtn = (RadioButton)findViewById(R.id.two_pup_lost);
		}

		
		private void setListener() {
			mBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 关闭当前界面
					finish();
				}
			});
			mCheckLostOrPick.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if(checkedId==mCheckPickBtn.getId())
				      { 
				          flag = 0;
				          Log.i("捡到还是丢失呢？---------》》》》", mCheckPickBtn.getText().toString()+""+flag);
				      } 
				      else if(checkedId==mCheckLostBtn.getId()) 
				      { 
				    	  flag = 1; 
				    	  Log.i("捡到还是丢失呢？---------》》》》", mCheckLostBtn.getText().toString()+""+flag);
				      }
				}			
			});
			mSubmitBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(flag == 0){
						new PostLostThread().run(ActivityForResultUtil.MYSQLPICKCOUNTNUM);
					}else if(flag == 1){
						new PostLostThread().run(ActivityForResultUtil.MYSQLLOSTCOUNTNUM);
					}
					
					//
				}
			});
		}
		class PostLostThread extends Thread{
			  public void run(int temp){
				init();	
				ActivityForResultUtil.MYSQLPICKORLOSTTEMPNUM = temp;
			    ActivityForResultUtil.MYSQLPICKORLOSTTEMPNUM++;
				Log.i("work--------------->MYSQLPICKORLOSTCOUNTNUM", ActivityForResultUtil.MYSQLPICKORLOSTTEMPNUM+"");
			    
			    
			    List <HttpPostParam> params = new ArrayList <HttpPostParam>(); 
			    params.add(new HttpPostParam("title", Title)); 
			    params.add(new HttpPostParam("flag", flag+"")); 
			    params.add(new HttpPostParam("lost_id",ActivityForResultUtil.MYSQLPICKORLOSTTEMPNUM+"")); 
			    params.add(new HttpPostParam("lost_time",LostTime));
			    params.add(new HttpPostParam("path",Path));
			    params.add(new HttpPostParam("phone",Phone));
			    params.add(new HttpPostParam("content",Content));
			    params.add(new HttpPostParam("Link_man",LinkMan));
			    params.add(new HttpPostParam("QQ",QQ));
			    SyncHttp http = new SyncHttp();
			    try
			    {
			      String rst = http.post(GetAddressUtil.LOST_PUBLIC, params);
			      Log.i("result--------------->",rst);
			    } catch (Exception e)
			    {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }
			  }
			}
		

	

}

