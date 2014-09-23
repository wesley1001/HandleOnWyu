package com.wuyuan.android.menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.activity.ConcurrentActivity;
import com.wuyuan.android.activity.LibSearchResultActivity;
import com.wuyuan.android.result.PhotoDetailResult;
import com.wuyuan.android.result.PhotoResult;
import com.wuyuan.android.ui.base.FlipperLayout.OnOpenListener;
import com.wuyuan.android.utils.GetAddressUtil;
import com.wuyuan.android.utils.TextUtil;

/**
 * 武院图书馆类
 * 
 * @author 趙子龍
 * 
 */
public class library {
	private Context mContext;
	private KXApplication mKXApplication;
	private View mPhoto;

	private Button mMenu;
	private Button mRefresh;
	private GridView mDisplay;
	private Button mFriend;
	private Button mMySelf;
	private Button mLibSearchBtn;
	private PhotoAdapter mAdapter;
	private EditText mLibSearchEdit;
	private OnOpenListener mOnOpenListener;
	private String html;
	final String HTML1 = "?strSearchType=title&match_flag=forward&historyCount=1&strText=";
	final String HTML2 = "&doctype=ALL&displaypg=20&showmode=table&sort=CATA_DATE&orderby=desc&dept=ALL";
	// 是否是好友照片
	private boolean mIsFriend = true;
	// 屏幕的宽度
	private int mScreenWidth;

	public library(Context context, KXApplication application, int screenWidth) {
		mContext = context;
		mKXApplication = application;
		mScreenWidth = screenWidth;
		mPhoto = LayoutInflater.from(context).inflate(R.layout.photo, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mPhoto.findViewById(R.id.photo_menu);
		mRefresh = (Button) mPhoto.findViewById(R.id.photo_refresh);
		mDisplay = (GridView) mPhoto.findViewById(R.id.photo_display);
		mLibSearchEdit = (EditText) mPhoto.findViewById(R.id.lib_search_edit);
		mLibSearchBtn = (Button) mPhoto.findViewById(R.id.lib_search_button);
//		mMySelf = (Button) mPhoto.findViewById(R.id.photo_myself);
	}

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
				
			}
		});
	
		mLibSearchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					// 字符编码，尤其对中文字符
					String str = new String(mLibSearchEdit.getText().toString()
							.trim().getBytes(), "ISO-8859-1"); //UTF-8
					if (str.equals("")) {
						GetAddressUtil.showToast(mContext,
								"请输入检索内容!");
					} else {
						html = GetAddressUtil.MAIN_URL + HTML1 + str + HTML2;
						Intent intent = new Intent();
						intent.setClass(mContext, LibSearchResultActivity.class);
						intent.putExtra("URL", html);
						mContext.startActivity(intent);
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
	}

	private Context getApplication() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void init() {
		// 获取好有照片数据
		getFriendPhoto();
		// 获取我的照片数据
		getMyPhoto();
		// 添加适配器
		mAdapter = new PhotoAdapter(
				mKXApplication.mFriendPhotoResults.get("kx001"));
		//mDisplay.setAdapter(mAdapter);
	}

	/**
	 * 获取好友照片数据
	 */
	private void getFriendPhoto() {
		if (!mKXApplication.mFriendPhotoResults.containsKey("kx001")) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open("data/kx001_photo.KX");
				String json = new TextUtil(mKXApplication)
						.readTextFile(inputStream);
				getPhotos(json, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取我的照片数据
	 */
	private void getMyPhoto() {
		if (mKXApplication.mMyPhotoResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open("data/my_photo.KX");
				String json = new TextUtil(mKXApplication)
						.readTextFile(inputStream);
				getPhotos(json, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析Json数据
	 * 
	 * @param json
	 * @param isFriend
	 */
	private void getPhotos(String json, boolean isFriend) {
		try {
			JSONArray array = new JSONArray(json);
			PhotoResult result = null;
			List<PhotoResult> list = new ArrayList<PhotoResult>();
			for (int i = 0; i < array.length(); i++) {
				result = new PhotoResult();
				result.setPid(array.getJSONObject(i).getString("pid"));
				result.setImage(array.getJSONObject(i).getInt("image"));
				result.setTitle(array.getJSONObject(i).getString("title"));
				result.setCount(array.getJSONObject(i).getInt("count"));
				result.setTime(array.getJSONObject(i).getString("time"));
				result.setType(array.getJSONObject(i).getInt("type"));
				JSONArray imagesArray = array.getJSONObject(i).getJSONArray(
						"images");
				List<PhotoDetailResult> images = new ArrayList<PhotoDetailResult>();
				for (int j = 0; j < imagesArray.length(); j++) {
					PhotoDetailResult photoDetailResult = new PhotoDetailResult();
					photoDetailResult.setImage(imagesArray.getJSONObject(j)
							.getInt("image"));
					photoDetailResult.setTime(imagesArray.getJSONObject(j)
							.getString("time"));
					photoDetailResult.setDescription(imagesArray.getJSONObject(
							j).getString("description"));
					if (imagesArray.getJSONObject(j).has("comment_count")) {
						photoDetailResult.setComment_count(imagesArray
								.getJSONObject(j).getInt("comment_count"));
					}
					if (imagesArray.getJSONObject(j).has("like_count")) {
						photoDetailResult.setLike_count(imagesArray
								.getJSONObject(j).getInt("like_count"));
					}
					List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
					if (imagesArray.getJSONObject(j).has("comments")) {
						JSONArray commentsArray = imagesArray.getJSONObject(j)
								.getJSONArray("comments");
						for (int k = 0; k < commentsArray.length(); k++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("uid", commentsArray.getJSONObject(k)
									.getString("uid"));
							map.put("avatar", commentsArray.getJSONObject(k)
									.getString("avatar"));
							map.put("name", commentsArray.getJSONObject(k)
									.getString("name"));
							map.put("time", commentsArray.getJSONObject(k)
									.getString("time"));
							map.put("content", commentsArray.getJSONObject(k)
									.getString("content"));
							if (commentsArray.getJSONObject(k).has("replys")) {
								JSONArray replysArray = commentsArray
										.getJSONObject(k)
										.getJSONArray("replys");
								List<Map<String, String>> replyResults = new ArrayList<Map<String, String>>();
								for (int l = 0; l < replysArray.length(); l++) {
									Map<String, String> replyMap = new HashMap<String, String>();
									replyMap.put("uid", replysArray
											.getJSONObject(l).getString("uid"));
									replyMap.put("avatar",
											replysArray.getJSONObject(l)
													.getString("avatar"));
									replyMap.put("name", replysArray
											.getJSONObject(l).getString("name"));
									replyMap.put("time", replysArray
											.getJSONObject(l).getString("time"));
									replyMap.put("content",
											replysArray.getJSONObject(l)
													.getString("content"));
									replyResults.add(replyMap);
								}
								map.put("replys", replyResults);
							}
							comments.add(map);
						}
						photoDetailResult.setComments(comments);
						images.add(photoDetailResult);
					} else {
						photoDetailResult.setComments(comments);
						images.add(photoDetailResult);
					}
				}

				result.setImages(images);
				list.add(result);
			}
			if (isFriend) {
				mKXApplication.mFriendPhotoResults.put("kx001", list);
			} else {
				mKXApplication.mMyPhotoResults = list;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class PhotoAdapter extends BaseAdapter {

		private List<PhotoResult> mResults = new ArrayList<PhotoResult>();

		public PhotoAdapter(List<PhotoResult> results) {
			if (results != null) {
				mResults = results;
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
						R.layout.photo_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photo_item_img);
				int padding = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 40, mContext
								.getResources().getDisplayMetrics());
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.width = (mScreenWidth - padding) / 3;
				params.height = (mScreenWidth - padding) / 3;
				holder.image.setLayoutParams(params);
				holder.title = (TextView) convertView
						.findViewById(R.id.photo_item_title);
				holder.description = (TextView) convertView
						.findViewById(R.id.photo_item_description);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			PhotoResult result = mResults.get(position);
			if (result.getType() == 0) {
				holder.image.setImageBitmap(mKXApplication.getAvatar(result
						.getImage()));
			} else {
				holder.image.setImageBitmap(mKXApplication.getPhoto(result
						.getImage()));
			}

			holder.title.setText(result.getTitle() + "(" + result.getCount()
					+ ")");
			if (mIsFriend) {
				holder.description.setText("罗智宜");
			} else {
				holder.description.setText(result.getTime() + " 更新");
			}
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			TextView title;
			TextView description;
		}
	}

	public View getView() {
		return mPhoto;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
