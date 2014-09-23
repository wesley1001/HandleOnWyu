package com.wuyuan.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.result.ForumResult;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.SyncHttp;

public class ForumListActivity extends Activity{

	/**
	 * 论坛列表类
	 * 
	 * @author 趙子龍
	 * 
	 */

	private Button mMenu;
	private Button mRefresh;
	private ListView mDisplay;
	private KXApplication mKXApplication;
	private ListAdapter mAdapter;
	private ForumResult result = null;
	private int mPosition = 0;
	private int temp = 0;
	private int LIMITCOUNT = 5;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forumlist_activity);		
		findViewById();
		setListener();
		init();
	}

	

		

	private void findViewById() {
		mMenu = (Button) findViewById(R.id.forumlist_menu);
		mRefresh = (Button) findViewById(R.id.forumlist_refresh);
		mDisplay = (ListView) findViewById(R.id.forumlist_display);

	}

	private void setListener() {
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent ();
//				intent.setClass(mContext, DiaryDetailActivity.class);
//				intent.putExtra("position", arg2);
//				intent.putExtra("id", arg3);
//				mContext.startActivity(intent);
			}
			
		});
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
				
			}
		});
		
		
	}

	private void init() {
		
		temp = getIntent().getIntExtra("position", 0) + 1;
		if(temp != mPosition){
			mKXApplication.mForumResult.clear();
		}
		System.out.println("temp----->"+temp+"    position----->"+mPosition);
		mPosition = temp;
		System.out.println("temp----->"+temp+"    position----->"+mPosition);
		GetSpecCatNews(0);
		// 添加适配器
		mAdapter = new ForumAdapter(mKXApplication.mForumResult);			
		mDisplay.setAdapter(mAdapter);
		
	}
	/*
     *                 		***********************		  json获取数据            ********************
     */
	private  void GetSpecCatNews(int next){
		
		String urlStr = "http://"+GetAddressUtil.GETLOCALHOST2+"/wuyuan/json_forum.php";
		String params = "cid="+mPosition+"&add="+next+"&nid="+LIMITCOUNT;
		SyncHttp syncHttp = new SyncHttp();
		try{
			
			String retStr = syncHttp.httpGet(urlStr, params);
			JSONObject  jsonObject = new JSONObject(retStr);
			JSONArray newslist = jsonObject.getJSONArray("value"); 
						
			if(newslist !=null){
	            for(int i=0;i<newslist.length();i++) 
	            { 
	            	result = new ForumResult();
	                JSONObject newsObject = (JSONObject)newslist.opt(i); 
	                result.setTitle(newsObject.getString("title"));
	                result.setTime(newsObject.getString("time")); 
	                result.setContent(newsObject.getString("content"));
	                result.setCount(newsObject.getInt("count"));
	                //ActivityForResultUtil.MYSQLCOUNTNUM = newsObject.getInt("count");
	                //int ii = newsObject.getInt("count");
	                //Log.i("result.setCount(newsObject.getInt(count));", ActivityForResultUtil.MYSQLCOUNTNUM+"");
					mKXApplication.mForumResult.add(result);
	            } 	
			}
            else{
            	Toast.makeText(getApplication(), "该栏目暂时没有新闻",Toast.LENGTH_SHORT).show();
            }
            
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplication(), "数据获取异常", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	private class ForumAdapter extends BaseAdapter {

		private List<ForumResult> mResults;
		public ForumAdapter(List<ForumResult> results) {
			if (results != null) {
				mResults = results;
			} else {
				mResults = new ArrayList<ForumResult>();
			}
		}
		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.viewed_item, null);
				holder = new ViewHolder();
				holder.forumList_title = (TextView) convertView
						.findViewById(R.id.viewed_item_title);
				holder.forumList_time = (TextView) convertView
						.findViewById(R.id.viewed_item_time);
				holder.forumList_content = (TextView) convertView
						.findViewById(R.id.viewed_item_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			result = mResults.get(position);
			holder.forumList_title.setText(result.getTitle());
			holder.forumList_time.setText(result.getTime());
			holder.forumList_content.setText(result.getContent());		
			return convertView;
		}

		class ViewHolder {
			TextView forumList_title;
			TextView forumList_content;
			TextView forumList_time;
		}
	}
	
}



