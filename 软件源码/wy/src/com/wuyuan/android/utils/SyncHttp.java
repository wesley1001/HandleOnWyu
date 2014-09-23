package com.wuyuan.android.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class SyncHttp {
    Context context = null;
	public String httpGet(String url,String params) throws Exception{
		String response = null;
		if(null!=params && !params.equals("")){
			url +="?"+params;
		}
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		//httpclient
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		//Get
		HttpGet httpGet = new HttpGet(url);
		try{
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				response = EntityUtils.toString(httpResponse.getEntity());
			}
			else{
				response = "返回码"+statusCode;
			}
		}catch(Exception e){
			throw new Exception(e);
		}
		            return response;        
	}
	
	public String post (String url,List<HttpPostParam> params) throws Exception{
	      String response = null;
	      int timeoutConnection = 3000;
	      int timeoutSocket = 5000;
	      HttpParams httpParameters = new BasicHttpParams();
	      HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	      HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	      
	      HttpClient httpClient = new DefaultHttpClient(httpParameters);
	      /*建立HTTP Post联机*/
	      HttpPost httpPost = new HttpPost(url); 
	      
	     
	        httpPost.setEntity(new UrlEncodedFormEntity((buildNameValuePair(params)), HTTP.UTF_8)); 
	     
	      HttpResponse httpResponse = httpClient.execute(httpPost);
	      int statusCode = httpResponse.getStatusLine().getStatusCode();
	      if(statusCode == HttpStatus.SC_OK){
	          response = EntityUtils.toString(httpResponse.getEntity());

	      }
	      return response;
	     }
	    private List<BasicNameValuePair> buildNameValuePair(List<HttpPostParam> params){
	      List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
	     
	      for(HttpPostParam param : params){
	        BasicNameValuePair pair = new BasicNameValuePair(param.getName() , param.getValue());
	        result.add(pair);
	      }
	      return result;
	    }
	

}










