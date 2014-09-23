package com.wuyuan.android.activity;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.wuyuan.android.BookInfo;
import com.wuyuan.android.utils.BookUtil;
import com.wuyuan.android.utils.JsoupUtil;

public class LibSearchResultActivity extends Activity {

	private ListView listView;
	private String html;
	private TextView sumNumber;
	private TextView pageNumber;
	private Button mBack;

	private Button nextButton;
	private Button preButton;
	private ProgressDialog mypDialog;
	  public static  BookInfo bookInfo=new BookInfo();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		listView = (ListView) findViewById(R.id.search_section_list);

		// 搜索进度提示
		mypDialog = new ProgressDialog(LibSearchResultActivity.this);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setTitle("请稍等");
		mypDialog.setMessage("正在查找...");
		mypDialog.setIndeterminate(false);
		/*// 互动广告接口
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd();*/
		Intent intent = this.getIntent();
		html = intent.getStringExtra("URL");
		System.out.println(html);
		// 默认的第一次搜索
		new LoadBookInfo().execute(html);
		// 当第二次检索时，应将之前的信息清空！
		JsoupUtil.clearInfo();
		// 总页数
		sumNumber = (TextView) findViewById(R.id.sum_number);
		pageNumber = (TextView) findViewById(R.id.page_number);
		// 上一页、下一页按钮
		nextButton = (Button) findViewById(R.id.next);
		preButton = (Button) findViewById(R.id.pre);
		mBack = (Button) findViewById(R.id.libsearchresult_back);
		preButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (JsoupUtil.page <= 1) {
					Toast.makeText(getApplicationContext(), "已经是第一页了！",
							Toast.LENGTH_SHORT).show();
				} else {
					new LoadBookInfo().execute(JsoupUtil.preUrl);
					JsoupUtil.page--;
				}
			}
		});
		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (JsoupUtil.page >= Math.ceil(JsoupUtil.sumNumber / 20)) {
					Toast.makeText(getApplicationContext(), "已经是最后一页了！",
							Toast.LENGTH_SHORT).show();
				} else {
					new LoadBookInfo().execute(JsoupUtil.nextUrl);
					JsoupUtil.page++;
				}
			}
		});
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	
		
			
	class LoadBookInfo extends
			AsyncTask<String, ListView, List<Map<String, Object>>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			System.out.println("onPreExecute");
			mypDialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("doInBackground");
			// System.out.println("html:" + html);
			// System.out.println(params[0]);
			return JsoupUtil.searchBook(params[0]);
		}

		@Override
		protected void onPostExecute(final List<Map<String, Object>> result) {
			// TODO Auto-generated method stub
			System.out.println("onPostExecute");
			// 显示总数、页码及图书列表
			mypDialog.cancel();
			if (result == null) {
				finish();
				Toast.makeText(getApplicationContext(), "本馆没有您检索的纸本馆藏书目",
						Toast.LENGTH_LONG).show();
			} else {
				super.onPostExecute(result);
			 System.out.println("---->"+result);
			
				sumNumber.setText(JsoupUtil.sumNumber.toString());
				pageNumber.setText(JsoupUtil.pageNumber);
				SimpleAdapter listAdapter = new SimpleAdapter(
						getApplicationContext(), result, R.layout.book_list,
						new String[] { "bookTitle", "bookAuthor", "bookCallno",
								"bookPublisher" }, new int[] { R.id.bookTitle,
								R.id.bookAuthor, R.id.bookCallno,
								R.id.bookPublisher });
				listView.setAdapter(listAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						 
						System.out.println(position);
						BookUtil.getItemResult(result, position,bookInfo); 
                        Intent intent = new Intent(LibSearchResultActivity.this,BookDetailActivity.class);
                       startActivity(intent);
					}
				});
				
			}
		}

	}
}
