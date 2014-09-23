package com.wuyuan.android.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.kaixin.android.R;
import com.wuyuan.android.KXApplication;

public class NearbyPhotoLayout {
	private KXApplication mKXApplication;
	private View mLayout;
	private ImageView mPhoto;

	public NearbyPhotoLayout(Context context, KXApplication application) {
		mKXApplication = application;
		mLayout = LayoutInflater.from(context).inflate(
				R.layout.lbs_nearby_photo_display_item, null);
		mPhoto = (ImageView) mLayout
				.findViewById(R.id.lbs_nearby_photo_display_item_photo);
	}

	public View getLayout() {
		return mLayout;
	}

	public void setPhoto(String image) {
		mPhoto.setImageBitmap(mKXApplication.getNearbyPhoto(image));
	}
}
