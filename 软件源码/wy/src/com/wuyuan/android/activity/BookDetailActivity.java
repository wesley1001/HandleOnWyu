package com.wuyuan.android.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kaixin.android.R;

public class BookDetailActivity extends Activity {
	private TextView bookTitle,bookCallno,bookPosition;
	private Button mBack;

	 
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
     setContentView(R.layout.bookcontent);
     mBack = (Button) findViewById(R.id.bookdetail_back);
     bookTitle=(TextView)super.findViewById(R.id.bookcontent_bookTitle);
     bookCallno=(TextView)super.findViewById(R.id.bookcontent_bookCallno);
     bookPosition=(TextView)super.findViewById(R.id.bookcontent_bookPosition);
          bookTitle.setText(LibSearchResultActivity.bookInfo.getBookTitle());
          bookCallno.setText(LibSearchResultActivity.bookInfo.getBookCallno());
          bookPosition.setText(LibSearchResultActivity.bookInfo.getBookPosition());      
      	mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
}
	
}