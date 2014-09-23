package com.wuyuan.android.menu;

import java.io.InputStream;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;
import com.wuyuan.android.activity.DiaryDetailActivity;
import com.wuyuan.android.activity.ForumListActivity;
import com.wuyuan.android.result.GiftResult;
import com.wuyuan.android.ui.base.MyGridView;
import com.wuyuan.android.ui.base.MyListView;
import com.wuyuan.android.ui.base.FlipperLayout.OnOpenListener;
import com.wuyuan.android.utils.TextUtil;

/**
 * 论坛类
 * 
 * @author zhaozilong
 * 
 */
public class Forum {
	private Context mContext;
	private KXApplication mKXApplication;
	private View mGifts;

	private Button mMenu;
	private Button mGift;
	private ImageView mMore;
	private ImageButton mImage;
	private MyGridView mDisplay;
	private MyListView mFriendsList;
	private OnOpenListener mOnOpenListener;

	public Forum(Context context, KXApplication application) {
		mContext = context;
		mKXApplication = application;
		mGifts = LayoutInflater.from(context).inflate(R.layout.gifts, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mGifts.findViewById(R.id.gifts_menu);
		mGift = (Button) mGifts.findViewById(R.id.gifts_gift);
		//mMore = (ImageView) mGifts.findViewById(R.id.gifts_more);
		mDisplay = (MyGridView) mGifts.findViewById(R.id.gifts_display);
		mFriendsList = (MyListView) mGifts
				.findViewById(R.id.gifts_friends_list);
		//获取GridView中的图片
		View mConvertView = LayoutInflater.from(mContext).inflate(
				R.layout.gifts_item, null);
		mImage = (ImageButton) mConvertView
				.findViewById(R.id.gifts_item_image);
	}

	private void setListener() {
		//mImage.setOnClickListener();
		
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mGift.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
			}
		});
	}

	private void init() {
		// 获取礼物数据
		getGifts();
		// 获取好友生日数据
		//getFriendsBirthday();
		// 添加适配器
		mDisplay.setAdapter(new GiftAdapter());
		//mFriendsList.setAdapter(new BirthdayAdapter());
	}

	/**
	 * 获取礼物数据
	 */
	private void getGifts() {
		if (mKXApplication.mGiftResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open("data/gifts.KX");
				String json = new TextUtil(mKXApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				GiftResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new GiftResult();
					result.setGid(array.getJSONObject(i).getString("gid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setContent(array.getJSONObject(i).getString(
							"content"));
					mKXApplication.mGiftResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	private class GiftAdapter extends BaseAdapter {

		public int getCount() {
			return 8;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gifts_item, null);
				holder = new ViewHolder();
				holder.image = (ImageButton) convertView
						.findViewById(R.id.gifts_item_image);
				holder.image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent ();
						intent.setClass(mContext, ForumListActivity.class);
						intent.putExtra("position", position);
						mContext.startActivity(intent);
					}
				});
				holder.title = (TextView) convertView
						.findViewById(R.id.gifts_item_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			GiftResult result = mKXApplication.mGiftResults.get(position);
			holder.image.setImageBitmap(mKXApplication.getGift(result.getGid()
					+ ".jpg"));
			holder.title.setText(result.getName());
			return convertView;
		}

		class ViewHolder {
			ImageButton image;
			TextView title;
		}
	}

	

	public View getView() {
		return mGifts;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}
