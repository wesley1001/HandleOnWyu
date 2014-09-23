package com.wuyuan.android.menu;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.activity.LostPubActivity;
import com.wuyuan.android.activity.LostDetailContent;
import com.wuyuan.android.result.LostResult;
import com.wuyuan.android.ui.base.FlipperLayout.OnOpenListener;
import com.wuyuan.android.utils.ActivityForResultUtil;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.RefreshableListView;
import com.wuyuan.android.utils.SyncHttp;

/**
 * 失物招领类
 * 
 * @author 趙子龍
 * 
 */
public class Lost implements com.wuyuan.android.utils.RefreshableListView.IXListViewListener{
	private Context mContext;
	private View mMessage;

	private Button mMenu;
	private Button mWriteMessage;
	private Button mPick;
	private Button mLost;
	private RefreshableListView mDisplay;
	private OnOpenListener mOnOpenListener;
	private LostResult result= null;
	private ListAdapter mAdapter;
	private KXApplication mKXApplication;
	private boolean mIslost = true;
	private final int LIMITECOUNT = 5;//限制加载条数
	private final int INTTNUM = 0;//限制加载条数
	//private final int LIMITENEXT = LIMITECOUNT - 1;//限制加载更多条数（根据数据库设计得出的算法）
	private int mflag = 0;
	private Handler mHandler;

	/*
	 * 加载更多思路
	 * 
	 * next + 5；  josn解析数据即可
	 */
	
	
	
	
	public Lost(Context context , KXApplication application) {
		mContext = context;
		mMessage = LayoutInflater.from(context).inflate(R.layout.message, null);
		mKXApplication = application;
		mHandler = new Handler();
		findViewById();
		setListener();
		init();
		mDisplay.setPullLoadEnable(true);
		mDisplay.setXListViewListener(this);
	}

	private void findViewById() {
		mMenu = (Button) mMessage.findViewById(R.id.message_menu);
		mWriteMessage = (Button) mMessage.findViewById(R.id.message_write_lostorget);
		mDisplay = (RefreshableListView) mMessage.findViewById(R.id.message_display);
		mPick = (Button) mMessage.findViewById(R.id.pick_public);
		mLost = (Button) mMessage.findViewById(R.id.lost_public);
		//mCheckBtn = (RadioButton)mMessage.findViewById(R.)
	}

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent ();
				intent.putExtra("position", position - 1);
				intent.putExtra("flag", mflag);
				intent.setClass(mContext, LostDetailContent.class);
				mContext.startActivity(intent);
				
			}
			
		});
		
		
		
		mWriteMessage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent ();
				intent.setClass(mContext, LostPubActivity.class);
				mContext.startActivity(intent);
			}
		});
		mPick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!mIslost) {
					mIslost = true;
					mflag = 0;
					mPick.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mLost.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mAdapter = new LostAdapter(mKXApplication.mPickResults);
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		mLost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mIslost) {
					mIslost = false;
					mflag = 1;
					mPick.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mLost.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					mAdapter = new LostAdapter(KXApplication.mLostResults);
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		
	}
	
	private void clear(){
		mKXApplication.mPickResults.clear();
		mKXApplication.mLostResults.clear();
	}
	

	private void init() {
		new LoadNewsAsyncTask().execute(null);
		//添加适配器
		try {
			mAdapter = new LostAdapter(mKXApplication.mPickResults);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mDisplay.setAdapter(mAdapter);
	}

	private class NewDataTask extends AsyncTask<Object, Integer, Void>{

		@Override
		protected Void doInBackground(Object... params) {
			// TODO Auto-generated method stub
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
		
	}
	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Void>{
		@Override
		protected void onPreExecute() {
			//第二个参数0代表捡到  1代表丢失
			GetSpecCatNews(INTTNUM,0);
		}
		
		@Override
		protected Void doInBackground(Object... params) {
			//第二个参数0代表捡到  1代表丢失
			GetSpecCatNews(INTTNUM,1);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//让flag 初始化为0
			mflag = 0;
		}
	}
	/*
     *                 		***********************		  json获取数据            ********************
     */
	private  void GetSpecCatNews(int next,int flag){

		//int lost_id = id;
		mflag = flag;
		String urlStr = "http://"+GetAddressUtil.GETLOCALHOST2+"/wuyuan/json_lost.php";
		String params = "lost_id="+LIMITECOUNT+"&next="+next+"&flag="+mflag;
		SyncHttp syncHttp = new SyncHttp();
		try{
			
			String retStr = syncHttp.httpGet(urlStr, params);
			JSONObject  jsonObject = new JSONObject(retStr);
			JSONArray newslist = jsonObject.getJSONArray("value"); 
			if(newslist !=null){
	            for(int i=0;i<newslist.length();i++) 
	            { 
	            	result = new LostResult();
	                JSONObject newsObject = (JSONObject)newslist.opt(i); 
	                result.setTitle(newsObject.getString("title"));
	                result.setTime(newsObject.getString("time"));
	                result.setContent(newsObject.getString("content"));
	                result.setPlace(newsObject.getString("place"));
	                result.setContract_person(newsObject.getString("contract_person"));
	                result.setPhone(newsObject.getString("phone"));
	                result.setQQ(newsObject.getString("QQ"));
	                result.setCount(newsObject.getInt("count"));
	                if(mflag == 0){
	                	//获取捡到的条数
	                	ActivityForResultUtil.MYSQLPICKCOUNTNUM = newsObject.getInt("count");
	                	Log.i("pick_num-------->>>>>", ActivityForResultUtil.MYSQLPICKCOUNTNUM+"");
	                	mKXApplication.mPickResults.add(result);
	                }else if(mflag == 1){
	                	//获取丢的条数
	                	ActivityForResultUtil.MYSQLLOSTCOUNTNUM = newsObject.getInt("count");
	                	Log.i("lost_num-------->>>>>", ActivityForResultUtil.MYSQLLOSTCOUNTNUM+"");
	                	mKXApplication.mLostResults.add(result);
	                }
	            } 	
			}
            else{
            	Toast.makeText(getApplication(), "该栏目暂时没有新闻",Toast.LENGTH_SHORT).show();
            }
            
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private Context getApplication() {
		// TODO Auto-generated method stub
		return null;
	}
	public View getView() {
		return mMessage;
	}

	private class LostAdapter extends BaseAdapter {

		private List<LostResult> mResults;
		public LostAdapter(List<LostResult> results) {
			if (results != null) {
				mResults = results;
			} else {
				mResults = new ArrayList<LostResult>();
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
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.message_list_item, null);
				holder = new ViewHolder();
				holder.lost_property_title = (TextView) convertView
						.findViewById(R.id.lost_property_title);
				holder.lost_property_content = (TextView) convertView
						.findViewById(R.id.lost_property_content);
				holder.lost_property_time = (TextView) convertView
						.findViewById(R.id.lost_property_time);
				holder.lost_property_phonenumber = (TextView) convertView
						.findViewById(R.id.lost_property_phonenumber);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			result = mResults.get(position);
			holder.lost_property_title.setText(result.getTitle());
			holder.lost_property_content.setText(result.getContent());
			holder.lost_property_time.setText(result.getTime());
			holder.lost_property_phonenumber.setText(result.getPhone());
			return convertView;
		}

		class ViewHolder {
			TextView lost_property_title;
			TextView lost_property_content;
			TextView lost_property_time;
			TextView lost_property_phonenumber;
		}
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}

	private void onLoad() {
		mDisplay.stopRefresh();
		mDisplay.stopLoadMore();
	}
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				clear();
				ActivityForResultUtil.MYSQLPICKORLOSTNEXTNUM = 0;
				try {
					init();
				} catch (Exception e) {
					// TODO: handle exception
				}
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ActivityForResultUtil.MYSQLPICKORLOSTNEXTNUM += LIMITECOUNT;
				GetSpecCatNews(ActivityForResultUtil.MYSQLPICKORLOSTNEXTNUM,0);
				GetSpecCatNews(ActivityForResultUtil.MYSQLPICKORLOSTNEXTNUM,1);
				try {
					mAdapter = new LostAdapter(mKXApplication.mPickResults);
				} catch (Exception e) {
					// TODO: handle exception
				}
				mDisplay.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}
}
