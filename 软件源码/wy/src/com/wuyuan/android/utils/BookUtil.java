package com.wuyuan.android.utils;

import java.util.List;
import java.util.Map;

import com.wuyuan.android.BookInfo;

public class BookUtil {
	
	 /**
	  * 获取单条的数据
	 * @param result
	 * @param position
	 * @param bookinfo
	 */
	public static  void getItemResult(List<Map<String,Object>> result,int position,BookInfo bookinfo){
		 
			System.out.println(result.get(position).toString());
		
			Map<String,Object> book=result.get(position);
			
			String bookAuthor = book.get("bookAuthor")==null?"":(String)book.get("bookAuthor");	 
			          bookinfo.setBookAuthor(bookAuthor);
			String bookTitle=  book.get("bookTitle")==null?"":(String)book.get("bookTitle");
	          bookinfo.setBookTitle(bookTitle);	          
			String bookType = book.get("bookType")==null?"":(String)book.get("bookType");	
	          bookinfo.setBookType(bookType);
			
			String bookPublisher =  book.get("bookPublisher")==null?"":(String)book.get("bookPublisher");	
	          bookinfo.setBookPublisher(bookPublisher);
			String bookNo = book.get("bookNo")==null?"":(String)book.get("bookNo");	
	          bookinfo.setBookNo(bookNo);
	          
	        String bookCallno=  book.get("bookCallno")==null?"":(String)book.get("bookCallno");
	          bookinfo.setBookCallno(bookCallno);     
	         
	        String bookPosition=getBookPosition(bookCallno);
	          bookinfo.setBookPosition(bookPosition);
			 
		System.out.println("-----over----");//演示取值的结果
		}
	private static String getBookPosition(String bookCallno){
	    String bookPosition="";
		char s =bookCallno.charAt(0);
		System.out.println(s );
		 if(s>='A'&&s<='F'){
			 bookPosition="二楼左侧书库" ;
		 }
		 else if(s>='G'&&s<='K'){
			 bookPosition="三楼左侧书库" ; 
			 }
		 else if(s=='I'){
			 bookPosition="四楼左侧书库" ; 
			 
			 }
		 else if(s=='T'){
			 bookPosition="二楼右侧书库" ; 
			 }
		 else	 
			 bookPosition="三楼右侧书库" ; 
			 
			return bookPosition;
		}
	}
