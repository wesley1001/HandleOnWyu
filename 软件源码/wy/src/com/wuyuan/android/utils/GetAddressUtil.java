package com.wuyuan.android.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.widget.Toast;

public class GetAddressUtil {
	public static String GETLOCALHOST ;
	//public static final String GETLOCALHOST2 = "192.168.195.101" ;
	//public static final String GETLOCALHOST2 = "192.168.195.102" ;
	//public static final String GETLOCALHOST2 = "117.29.67.161" ;
	//public static final String GETLOCALHOST2 = "59.59.113.99" ;
	public static final String GETLOCALHOST2 = "110.87.223.105" ;
	//上传图片服务器地址
	public static final String LostImageHost = "http://"+GETLOCALHOST2+"/ImageServer/LostImage";
	//发布兼职招聘地址
	public static final String WORK_PUBLIC = "http://"+GETLOCALHOST2+"/wuyuan/post_current.php";
	//发布失物招领地址
	public static final String LOST_PUBLIC = "http://"+GETLOCALHOST2+"/wuyuan/post_lost.php";
	//图书馆查询书籍地址
	public static final String LOGIN_URL = "http://211.80.243.109:8080/reader/redr_verify.php";
	public static final String BOOK_LST = "http://211.80.243.109:8080/reader/book_lst.php";//"http://lib.njutcm.edu.cn:8088/reader/book_lst.php";
	public static final String MAIN_URL = "http://211.80.243.109:8080/opac/openlink.php";


	public static void showToast(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}
	
	public static String getGETLOCALHOST() {
		return GETLOCALHOST;
	}

	public static void setGETLOCALHOST(String gETLOCALHOST) {
		GETLOCALHOST = gETLOCALHOST;
	}

	public static List<Cookie> cookies;
	
	public static void getUrl(){
		try {
			String  getlocalhost = getAddress.getLocalhostAddress().toString();
			GetAddressUtil.setGETLOCALHOST(getlocalhost);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(GETLOCALHOST);
	}
}
class getAddress{
	public static String getLocalhostAddress() throws UnknownHostException{
		InetAddress address = InetAddress.getLocalHost();		
		return address.getHostAddress().toString();
	}
}