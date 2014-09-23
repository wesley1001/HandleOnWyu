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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.activity.ConcurrentActivity;
import com.wuyuan.android.activity.WorkPubActivity;
import com.wuyuan.android.result.ConcurrentJobResult;
import com.wuyuan.android.result.FriendsResult;
import com.wuyuan.android.result.PublicPageResult;
import com.wuyuan.android.ui.base.MyLetterListView;
import com.wuyuan.android.ui.base.FlipperLayout.OnOpenListener;
import com.wuyuan.android.utils.ActivityForResultUtil;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.RefreshableListView;
import com.wuyuan.android.utils.SyncHttp;
import com.wuyuan.android.utils.TextUtil;

/**
 * 兼职招聘类
 * 
 * @author 趙子龍
 * 
 */
public class currentJob implements com.wuyuan.android.utils.RefreshableListView.IXListViewListener{
	private Context mContext;
	private KXApplication mKXApplication;
	private View mFriends;

	private Button mMenu;
	private Button mAdd;
	private EditText mSearch;
	private TextView mBirthday;
	private RefreshableListView mDisplay;
	private MyLetterListView mLetter;
	private Button mAll;
	private Button mPage;
	private TextUtil mTextUtil;
	private ListAdapter mAdapter;
	private ConcurrentJobResult result = null;
	private LoadNewsAsyncTask  mLoadNewsAsyncTask;
	private int count = 0;  //获取数据库中数据总条数
	private Handler mHandler;
	private final int LIMITECOUNT = 5;//限制加载条数

	private OnOpenListener mOnOpenListener;
	// 当前显示的好友数据
	private List<FriendsResult> mMyFriendsResults = new ArrayList<FriendsResult>();
	// 当前显示的好友的姓名的首字母的在列表中的位置
	private List<Integer> mMyFriendsPosition = new ArrayList<Integer>();
	// 当前显示的好友的姓名的首字母数据
	private List<String> mMyFriendsFirstName = new ArrayList<String>();
	// 当前显示的公共主页数据
	private List<PublicPageResult> mMyPublicPageResults = new ArrayList<PublicPageResult>();

	// 是否显示的是好友内容
	private boolean mIsAll = true;

	public currentJob(Context context, KXApplication application) {
		mContext = context;
		mKXApplication = application;
		mFriends = LayoutInflater.from(context).inflate(R.layout.friends, null);
		mHandler = new Handler();
		findViewById();
		setListener();
		init(0);
		mDisplay.setPullLoadEnable(true);
		mDisplay.setXListViewListener(this);
	}

	private void findViewById() {
		mMenu = (Button) mFriends.findViewById(R.id.friends_menu);
		mAdd = (Button) mFriends.findViewById(R.id.friends_add);
		mDisplay = (RefreshableListView) mFriends.findViewById(R.id.concurrently_display);
	}

	private void setListener() {
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.putExtra("position", arg2);										
				intent.setClass(mContext, ConcurrentActivity.class);
				mContext.startActivity(intent);
			}
			
		});
		mMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent ();
				intent.putExtra("count", count);
				Log.i("count", count+"");
				intent.setClass(mContext, WorkPubActivity.class);
				mContext.startActivity(intent);
			}
		});
		
	}

	public View getView() {
		return mFriends;
	}
	private void init(int temp) {

		GetSpecCatNews(temp);
		// 添加适配器
		mAdapter = new ConcurrentJobAdapter(mKXApplication.mConcurrentJobResults);
		mDisplay.setAdapter(mAdapter);
	}
	/*
     *                 		***********************		  json获取数据            ********************
     */
	private  void GetSpecCatNews(int next ){
		
		String urlStr = "http://"+GetAddressUtil.GETLOCALHOST2+"/wuyuan/json_current.php";
		String params = "current_job_id="+LIMITECOUNT+"&add="+next;
		SyncHttp syncHttp = new SyncHttp();
		try{
			
			String retStr = syncHttp.httpGet(urlStr, params);
			JSONObject  jsonObject = new JSONObject(retStr);
			JSONArray newslist = jsonObject.getJSONArray("value"); 
						
			if(newslist !=null){
	            for(int i=0;i<newslist.length();i++) 
	            { 
	            	result = new ConcurrentJobResult();
	                JSONObject newsObject = (JSONObject)newslist.opt(i); 
	                result.setTitle(newsObject.getString("title"));
	                result.setLimit_time(newsObject.getString("limit_time"));
	                result.setSalary(newsObject.getString("salary"));
	                result.setPerson_limit(newsObject.getString("person_limit"));
	                result.setSex(newsObject.getString("sex"));
	                result.setWork_time(newsObject.getString("work_time"));
	                result.setPlace(newsObject.getString("place"));
	                result.setPhone(newsObject.getString("phone"));
	                result.setContent(newsObject.getString("content"));
	                result.setContract_person(newsObject.getString("contract_person"));
	                result.setCount(newsObject.getInt("count"));
	                ActivityForResultUtil.MYSQLCOUNTNUM = newsObject.getInt("count");
	                int ii = newsObject.getInt("count");
	                Log.i("result.setCount(newsObject.getInt(count));", ActivityForResultUtil.MYSQLCOUNTNUM+"");
					mKXApplication.mConcurrentJobResults.add(result);
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
	
	private Context getApplication() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Void>{
		@Override
		protected void onPreExecute() {
//			mTitlebarRefresh.setVisibility(View.GONE);
//			mLoadnewsProgress.setVisibility(View.VISIBLE);
//			mLoadMoreBtn.setText("正在加载.....");
		}
		
		@Override
		protected Void doInBackground(Object... params) {
			GetSpecCatNews(5);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
//			mNewListAdapter.notifyDataSetChanged();
//			mTitlebarRefresh.setVisibility(View.VISIBLE);
//			mLoadnewsProgress.setVisibility(View.GONE);
//			mLoadMoreBtn.setText("加载更多");
		}
	}
	
	
	
	private class ConcurrentJobAdapter extends BaseAdapter {

		private List<ConcurrentJobResult> mResults;
		public ConcurrentJobAdapter(List<ConcurrentJobResult> results) {
			if (results != null) {
				mResults = results;
			} else {
				mResults = new ArrayList<ConcurrentJobResult>();
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
						R.layout.concurrently_job_listitem_detail, null);
				holder = new ViewHolder();
				holder.concurrently_job_title = (TextView) convertView
						.findViewById(R.id.concurrently_job_title);
				holder.concurrently_job_content = (TextView) convertView
						.findViewById(R.id.concurrently_job_content);
				holder.concurrently_job_person_num = (TextView) convertView
						.findViewById(R.id.concurrently_job_person_num);
				holder.concurrently_job_sex_require = (TextView) convertView
						.findViewById(R.id.concurrently_job_sex_require);
				holder.concurrently_job_money = (TextView) convertView
						.findViewById(R.id.concurrently_job_money);
				holder.concurrently_job_time = (TextView) convertView
						.findViewById(R.id.concurrently_job_time);
				holder.concurrently_job_phonenumber = (TextView) convertView
						.findViewById(R.id.concurrently_job_phonenumber);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			result = mResults.get(position);
			holder.concurrently_job_title.setText(result.getTitle());
			holder.concurrently_job_content.setText(result.getContent());
			holder.concurrently_job_person_num.setText(result.getPerson_limit());
			holder.concurrently_job_sex_require.setText(result.getSex());
			holder.concurrently_job_money.setText(result.getSalary());
			holder.concurrently_job_time.setText(result.getLimit_time());
			holder.concurrently_job_phonenumber.setText(result.getPhone());
			
			return convertView;
		}

		class ViewHolder {
			TextView concurrently_job_title;
			TextView concurrently_job_content;
			TextView concurrently_job_person_num;
			TextView concurrently_job_time;
			TextView concurrently_job_sex_require;
			TextView concurrently_job_money;
			TextView concurrently_job_phonenumber;
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
				ActivityForResultUtil.MYSQCURRENTNEXTNUM = 0;
				mKXApplication.mConcurrentJobResults.clear();
				try {
					init(0);
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
				ActivityForResultUtil.MYSQCURRENTNEXTNUM += LIMITECOUNT;							
				try {
					init(ActivityForResultUtil.MYSQCURRENTNEXTNUM);
				} catch (Exception e) {
					// TODO: handle exception
				}
				onLoad();
			}
		}, 2000);
	}

	

}