package com.wuyuan.android.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupUtil {

	public static Integer sumNumber;
	public static String pageNumber;
	public static String preUrl;
	public static String nextUrl;
	public static int page = 1;// 默认第一页

	public static void clearInfo() {
		page = 1;
		sumNumber = 0;
		pageNumber = null;
		preUrl = null;
		nextUrl = null;
	}

	public static List<Map<String, Object>> searchBook(String html) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> bookMap;

		Document doc;
		try {

			// 通过Jsoup的connect（）方法，将html转化成Document
			doc = Jsoup.connect(html).timeout(30 * 1000).get();
			System.out.println("Success to parse!");
			// System.out.println(doc);

			// 判断“本馆没有您检索的纸本馆藏书目”
			String err = doc.select("h3").text().toString();
			if (err.equals("本馆没有您检索的纸本馆藏书目"))
				return null;

			System.out.println("Test!");
			// 获取图书总数以及页码总数
			sumNumber = getSumNumber(doc);
			pageNumber = getPageNumber(doc);
			// 获得前后页的链接
			if (sumNumber >= 20)
				getPreAndNextUrl(doc);

			Elements books = doc.select("tr[bgcolor=#FFFFFF]");
			Iterator<Element> book = books.iterator();
			while (book.hasNext()) {
				Element em = book.next();
				System.out.println(em.text());
				// 这里的bookMap每次都要实例化一个，否则将会出现所有的内容都是最后一条的内容
				bookMap = new HashMap<String, Object>();
				// 经过多次验证，用Element(s)的text（）方法输出不带原来html的标签，而用toString的方法则会带标签
				// 用html（）方法得到标签括起来的内容
				// 解析图书部分内容
				Elements bookInfo = em.select("td");
				int totalTds = bookInfo.size();
				for (int j = 0; j < totalTds; j++) {
					switch (j) {
					case 0:// 图书序号
						bookMap.put("bookNo", bookInfo.get(j).html().toString());
						break;
					case 1:// 标题和链接
						bookMap.put("bookTitle", bookInfo.get(j).text());
						// bookMap.put("bookDetail", bookInfo.get(j).select("a")
						// .attr("href").toString());
						break;
					case 2:// 作者
						bookMap.put("bookAuthor", bookInfo.get(j).text());
						break;
					case 3:// 出版社
						bookMap.put("bookPublisher", bookInfo.get(j).text());
						break;
					case 4:// 索书号
						bookMap.put("bookCallno", bookInfo.get(j).text());
						break;
					case 5:// 文献类型
						bookMap.put("bookType", bookInfo.get(j).text());
						break;
					default:
						break;
					}
				}
				list.add(bookMap);
			}
		} catch (IOException e) {
			// 解析失败！
			e.printStackTrace();
			System.out.println("Failed to Parse!");
		}
		return list;
	}

	// 获得前后页的链接
	private static void getPreAndNextUrl(Document doc) {
		// TODO Auto-generated method stub

		Elements hrefs = doc.select("span[class=pagination]").select(
				"a[class=blue]");

		System.out.println("herfs:" + hrefs);
		if (page <= 1) {
			nextUrl = hrefs.get(0).attr("abs:href");
		}
		if (page >= Math.ceil(sumNumber / 20)) {
			preUrl = hrefs.get(0).attr("abs:href");
		}
		if ((page > 1) && (page < Math.ceil(sumNumber / 20))) {
			preUrl = hrefs.get(0).attr("abs:href");
			nextUrl = hrefs.get(1).attr("abs:href");
		}
		System.out.println("preUrl:" + preUrl);
		System.out.println("nextUrl:" + nextUrl);

	}

	// 获得页码总数
	private static String getPageNumber(Document doc) {
		// TODO Auto-generated method stub
		// 判断是否为多页。（每页默认显示20条数据）
		if (sumNumber <= 20) {
			return "1/1";
		} else {
			return doc.select("span[class=pagination]").select("b").get(0)
					.text().toString();
		}

	}

	// 获得图书总数
	private static Integer getSumNumber(Document doc) {
		// TODO Auto-generated method stubs
		return Integer.parseInt(doc.select("strong[class=red]").text()
				.toString());
	}

	
}
