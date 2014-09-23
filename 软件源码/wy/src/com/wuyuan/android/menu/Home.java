package com.wuyuan.android.menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.activity.DiaryDetailActivity;
import com.wuyuan.android.activity.PhoneAlbumActivity;
import com.wuyuan.android.ui.base.FlipperLayout.OnOpenListener;
import com.wuyuan.android.utils.ActivityForResultUtil;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.SyncHttp;
/**
 * 菜单首页类
 * 
 * @author 趙子龍
 * 
 */
public class Home {
	private Context mContext;
	private Activity mActivity;
	private KXApplication mKXApplication;
	private View mHome;

	private Button mMenu;
	private LinearLayout mTopLayout;
	private TextView mTopText;
	private Button mRefresh;
	private ListView mDisplay;

	private OnOpenListener mOnOpenListener;

	private String[] mPopupWindowItems = { "好友动态", "热门动态" };
	private PopupWindow mPopupWindow;
	private View mPopView;
	private ListView mPopDisplay;
	private HomeAdapter mHomeAdapter;
	private List<HashMap<String, Object>>  mHomeListData;
	private LoadNewsAsyncTask  mLoadNewsAsyncTask;

	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;

	public Home(Context context, Activity activity, KXApplication application) {
		mContext = context;
		mActivity = activity;
		mKXApplication = application;
		mHome = LayoutInflater.from(context).inflate(R.layout.home, null);
		mPopView = LayoutInflater.from(context).inflate(
				R.layout.home_popupwindow, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mHome.findViewById(R.id.home_menu);
		mTopLayout = (LinearLayout) mHome.findViewById(R.id.home_top_layout);
		mTopText = (TextView) mHome.findViewById(R.id.home_top_text);
		mRefresh = (Button) mHome.findViewById(R.id.home_refresh);
		mDisplay = (ListView) mHome.findViewById(R.id.home_display);
		mPopDisplay = (ListView) mPopView
				.findViewById(R.id.home_popupwindow_display);

	}

	private void setListener() {
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(mContext, DiaryDetailActivity.class);
				intent.putExtra("position", arg2);
				intent.putExtra("id", arg3);
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
		mTopLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示菜单
				//initPopupWindow();
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
				mLoadNewsAsyncTask = new LoadNewsAsyncTask();
				mLoadNewsAsyncTask.execute(5,true);
			}
		});
		mPopDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 这里只更改了标题栏,先不做数据的切换,因为数据有限
				mPopupWindow.dismiss();
				mTopText.setText(mPopupWindowItems[arg2]);
			}
		}); 
		
	}

	private void init() {
		// 获取首页数据
		getHome();
		// 添加适配器
		 mHomeAdapter = new HomeAdapter(mContext, mHomeListData, R.layout.viewed_item, 
				new String[]{"viewed_item_title","viewed_item_content", "viewed_item_time"}, 
				new int[]{R.id.viewed_item_title,R.id.viewed_item_content,
				R.id.viewed_item_time});		
		mDisplay.setAdapter(mHomeAdapter);
	}

	private void getHome(){
		mHomeListData = new ArrayList<HashMap<String,Object>>();
//		LoadNewsAsyncTask mLoadNewsAsyncTask = new LoadNewsAsyncTask();
//		mLoadNewsAsyncTask.execute(5,true);
		GetSpecCatNews(5,mHomeListData, true);
		
	}
	
	/*
     *                 		***********************		4.0  json获取数据            ********************
     */
	private  void GetSpecCatNews(int id , List<HashMap<String, Object>>  HomeList,Boolean fristTimes){
		//StringBuilder str = new StringBuilder(); 
		if(fristTimes){
			HomeList.clear();//将原来的信息清空
		}
		int first_id = id;
		//List<HashMap<String, Object>> newsList = new ArrayList<HashMap<String,Object>>();
		String urlStr = "http://"+GetAddressUtil.GETLOCALHOST2+"/wuyuan/json_home.php";
		String params = "first_id="+first_id;
		SyncHttp syncHttp = new SyncHttp();
		try{
			
			String retStr = syncHttp.httpGet(urlStr, params);
			JSONObject  jsonObject = new JSONObject(retStr);
			JSONArray newslist = jsonObject.getJSONArray("value"); 
			if(newslist !=null){
	            for(int i=0;i<newslist.length();i++) 
	            { 
	                JSONObject newsObject = (JSONObject)newslist.opt(i); 
	                HashMap<String,Object> hashMap = new HashMap<String, Object>();
	                //hashMap.put("viewed_item_title" newsObject.getInt("first_id"));
	                hashMap.put("viewed_item_time", newsObject.getString("time"));
	                hashMap.put("viewed_item_content", newsObject.getString("content"));
	                hashMap.put("viewed_item_title", newsObject.getString("title"));
	                HomeList.add(hashMap);
	            } 	
			}
            else{
            	Toast.makeText(getApplication(), "该栏目暂时没有新闻",1).show();
            }
//            else{
//            	Toast.makeText(MainActivity.this, "获取新闻失败", Toast.LENGTH_LONG).show();
//            }
            
		}catch(Exception e){
			e.printStackTrace();
			//Toast.makeText(getApplication(), "该栏目暂时没有新闻....", 1).show();
		}
		//return newsList;
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
			GetSpecCatNews((Integer)params[0], mHomeListData, (Boolean)params[1]);
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
	
	private class HomeAdapter extends SimpleAdapter{
		

		public HomeAdapter(Context home,
				List<HashMap<String, Object>> homeListData, int viewedItem,
				String[] from, int[] to) {
			// TODO Auto-generated constructor stub
			super(home, homeListData, viewedItem, from, to);
			// TODO Auto-generated constructor stub
		}
		
		
	}



	
	/**
	 * 照片对话框
	 */
	private void PhotoDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("上传照片");
		builder.setItems(new String[] { "拍照上传", "上传手机中的照片" },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File dir = new File("/sdcard/KaiXin/Camera/");
							if (!dir.exists()) {
								dir.mkdirs();
							}
							mKXApplication.mUploadPhotoPath = "/sdcard/KaiXin/Camera/"
									+ UUID.randomUUID().toString();
							File file = new File(
									mKXApplication.mUploadPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							mActivity
									.startActivityForResult(
											intent,
											ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							mContext.startActivity(new Intent(mContext,
									PhoneAlbumActivity.class));
							break;
						}
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	public void getSpecCatNews(Integer integer,
			List<HashMap<String, String>> homeListData2, Boolean boolean1) {
		// TODO Auto-generated method stub
		
	}

	public View getView() {
		return mHome;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
