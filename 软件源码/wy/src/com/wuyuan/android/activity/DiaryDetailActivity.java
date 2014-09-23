package com.wuyuan.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.wuyuan.android.KXActivity;
import com.wuyuan.android.result.DiaryResult;
import com.wuyuan.android.utils.PhotoUtil;
import com.wuyuan.android.utils.TextUtil;

/**
 * 日记内容显示类
 * 
 * @author rendongwei
 * 
 */
public class DiaryDetailActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private TextView mDiaryTitle;
	private TextView mDiaryTime;
	private TextView mDiaryContent;
	private Button mComment;
	private Button mRepost;

	private String mUid;// 日记所属用户的ID
	private String mName;// 日记所属用户的姓名
	private DiaryResult mDiaryResult; // 日记的具体内容
	private ProgressDialog mDialog;
	private int mPosition;
	private String id;
	private String[] contentString = new String[] {
			"武夷学院 全省大学生美术作品展获佳绩",
			"\t\t昨日，记者从武夷学院获悉，在福建省美术家协会主办的第四届福建省高等艺术院校大学生美术作品展览上，武夷学院艺术学院共有26位艺术学院学子的作品入选该展。\n\t\t参加的队员中周有为的版画《吾与本身系列》、林建华的版画《春泥》、林小燕的设计作品《字娱字乐》、陈丹丹的设计作品《预约阳光咖啡厅概念设计》、翁唐辉的设计作品《智能海洋废塑料收集机概念设计》获优秀奖。" +
			"\n\t\t这些入选与获奖作品数在福建省新办本科院校中名列前茅，此次比赛完成得益于我校注重挖掘“武夷”资源优势，发挥品牌效应，培育特色专业、推进产教结合、拓展开放办学的基础上的成果。 ",
			"2014-02-24 11:03" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarydetail_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.diarydetail_back);
		mTitle = (TextView) findViewById(R.id.diarydetail_top_title);
		mDiaryTitle = (TextView) findViewById(R.id.diarydetail_title);
		mDiaryTime = (TextView) findViewById(R.id.diarydetail_time);
		mDiaryContent = (TextView) findViewById(R.id.diarydetail_content);
		mComment = (Button) findViewById(R.id.diarydetail_comment);
		mRepost = (Button) findViewById(R.id.diarydetail_repost);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mRepost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DiaryDetailActivity.this, ChatActivity.class);
				startActivity(intent);
			}
		});
	}

	private void init() {

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// Position
		mPosition = bundle.getInt("position");
		long mId = bundle.getLong("id");

		Toast.makeText(getApplication(),
				"position-->" + mPosition + "|| id-->" + mId,
				Toast.LENGTH_SHORT).show();
		mTitle.setText("新闻详情");
		mDiaryTitle.setText(contentString[0]);
		mDiaryContent.setText(contentString[1]);
		mDiaryTime.setText(contentString[2]);

		/*
		 * // 初始化进度条 mDialog = new ProgressDialog(this);
		 * mDialog.setMessage("正在发送转帖请求..."); // 获取当前日记所属用户的ID和姓名以及日记内容 mUid =
		 * getIntent().getStringExtra("uid"); mName =
		 * getIntent().getStringExtra("name"); mDiaryResult =
		 * getIntent().getParcelableExtra("result");
		 * 
		 * // 根据用户的ID显示不同的内容,如果是当前用户则不显示转帖 if (mUid == null) {
		 * mTitle.setText("我的日记"); mRepost.setVisibility(View.GONE); } else {
		 * mTitle.setText(mName + "的日记"); mRepost.setVisibility(View.VISIBLE); }
		 * // 添加日记的具体内容 mDiaryTitle.setText(mDiaryResult.getTitle());
		 * mDiaryTime.setText(mDiaryResult.getTime()); mDiaryContent.setText(new
		 * TextUtil(mKXApplication).replace(mDiaryResult .getContent()));
		 * mComment.setText(mDiaryResult.getComment_count() + ""); }
		 * 
		 * Handler handler = new Handler() {
		 * 
		 * public void handleMessage(Message msg) { super.handleMessage(msg); //
		 * 如果进度条存在则隐藏并显示提示信息 if (mDialog != null && mDialog.isShowing()) {
		 * mDialog.dismiss(); Toast.makeText(DiaryDetailActivity.this,
		 * "转帖成功!你的好友会通过好友状态看到此转帖", Toast.LENGTH_SHORT).show(); } }
		 */
	};
}
