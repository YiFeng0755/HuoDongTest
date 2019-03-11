package com.boyaa.checkjar.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import org.junit.experimental.results.PrintableResult;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Common {
	public static String act_id[];
	public static Properties prop;
	public static String LogTag="===>";
	public int imgNumber;	
	public static String url;
	public static String orientation;
	public static String same;
//	public static Solo solo;
	/*
	 * �����ļ���ȡ
	 */
	public static void analyProperty(String filePath) {
		File file = new File(filePath);		
		//��ȡ���õ�
        InputStream is;
        prop = new Properties();
		try {
			is = new FileInputStream(file);
			prop.load(is);
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actString=prop.getProperty("act_id");//��ȡ����
		url = prop.getProperty("url");
		orientation = prop.getProperty("orientation");
		same = prop.getProperty("same");
		String a[] = actString.split(",");
		for (int i = 0; i < a.length; i++) {
			Log.d(LogTag, a[i]);
		}
        act_id = new String[a.length];
    	for(int i=0;i<a.length;i++){
//    		System.out.println(a[i]);
//    		b[i]=Integer.parseInt(a[i]);
//    		System.out.println(b[i]);
    		act_id[i]=a[i];
    		Log.d(LogTag, act_id[i]);
    	}
	}
	
 	/**
 	 * ��ͼ
 	 */
 	public void takeScreenshot(Solo solo, String name) throws Exception {
 	
 		// �����ͼ�ļ���
// 		String imgName = name + "_" +  imgNumber;
// 		if (imgNumber >= 10){
// 			imgName = name + "_" + imgDate + "_" + imgNumber;
// 		}
 		solo.takeScreenshot(name);
 		imgNumber++;
 	}
 	/**
 	 * ͨ���ִ�ID���Һ���ΨһID�ĵ���view��Ȼ����е��
 	 */
 	public static boolean clickById(Solo solo, String id) throws Exception {
 		//��¼������־
 		if (id == "") {
 			return false;
 		}
 		try {
// 			int viewId = currConText.getResources().getIdentifier(id,"id", packageName);// ��ȡid
 			View view = solo.getView(id);// �õ�View
 			solo.clickOnView(view);// ���
 		} catch (Exception ex) {
 			throw ex;
 		}
 		return true;
 	}
 	
	public static boolean enterTextById(Solo solo, String id, String s) {
		int ctrl;
		EditText v;
		if (s == "") {
			return false;
		}
		ctrl = solo
				.getCurrentActivity()
				.getResources()
				.getIdentifier(id, "id",
						solo.getCurrentActivity().getPackageName());
		v = (EditText) solo.getView(ctrl);
		solo.enterText(v, s);
		return true;
	}
 	/*
 	 * ����ID������ҳ��
 	 */
	public void HuodongPage(Solo solo, String actid, String element) throws Exception{
		Thread.sleep(2000);
		solo.clearEditText((EditText)solo.getView("com.boyaa.checkjar:id/et_actid"));
		solo.enterText((EditText)solo.getView("com.boyaa.checkjar:id/et_actid"), actid);
//		clickById("com.boyaa.checkjar:id/et_actid");
		Thread.sleep(5000);
		System.out.println("----��ʼ���");
		
		solo.clickOnView(solo.getView("button_jump"));				
	}
	
	/*
	 * ɾ��Ŀ¼�����µ��ļ�
	 */
	  public void DeleteFile(File file) { 
	        if (file.exists() == false) { 
	            return; 
	        } else { 
	            if (file.isFile()) { 
	                file.delete(); 
	                return; 
	            } 
	            if (file.isDirectory()) { 
	                File[] childFile = file.listFiles(); 
	                if (childFile == null || childFile.length == 0) { 
	                    file.delete(); 
	                    return; 
	                } 
	                for (File f : childFile) { 
	                    DeleteFile(f); 
	                } 
	                file.delete(); 
	            } 
	        } 
	    } 
	  
	/*
	 * �Թ㲥����ʽ�򿪻	
	 */
	public void cmdOpenPage(Solo solo, String url, String screen){
		Log.d(LogTag,"----�򿪻��ҳ");
		Log.d(LogTag,"\"actID\", \""+url+"\"");
		sendBroad(solo,url,screen);

	}
	public void cmdOpenPage(Solo solo, String url){
		Log.d(LogTag,"----�򿪻��ҳ");
		Log.d(LogTag,"\"actID\", \""+url+"\"");
		sendBroad(solo,url);

	}
	
	/*
	 * �Թ㲥����ʽ���û������
	 */
	public void cmdOpenPageScreen(Solo solo, String screen){
		Log.d(LogTag,"----���ú�����");
		Log.d(LogTag,screen);
 		Intent intent = new Intent();
 		intent.setAction("com.boyaa.checkjar.test");
 		intent.putExtra("screen", screen);
 		solo.getCurrentActivity().sendBroadcast(intent);

	}
 	/*
 	 * ���͹㲥
 	 */
 	private void sendBroad(Solo solo, String string, String Screen){
 		Intent intent = new Intent();
 		intent.setAction("com.boyaa.checkjar.test");
 		intent.putExtra("url", string);
 		intent.putExtra("orientation", orientation);
 		solo.getCurrentActivity().sendBroadcast(intent);
    }  
 	private void sendBroad(Solo solo, String string){
 		Intent intent = new Intent();
 		intent.setAction("com.boyaa.checkjar.test");
 		intent.putExtra("url", string);
 		solo.getCurrentActivity().sendBroadcast(intent);
    } 
 	
	/*
	 * �������ֶԻ�����ر�
	 */
	public void CloseView(Solo solo, String string){
     	try{
     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(text(),string)]"));
//     		RobotiumUtils.removeInvisibleViews(elementList);
     		Log.d(LogTag, "elementList size:"+elementList.size());
    		for(WebElement element : elementList){ 
    			solo.clickOnText(string);
    			continue;
	     		}
//    		solo.clickOnButton("ȡ��");
	     	}catch(Exception e){
	     		e.printStackTrace();
	     		
	     	}  	
		
	}
	
 	/*
 	 * Ԫ���Ƿ��Ѵ���
 	 */
 	public boolean blackkfiter(WebElement webElement,List<WebElement> blackList)
 	{
 		boolean same = false;
 		if(blackList!=null){
 			for (int i = 0; i < blackList.size(); i++) {
 				boolean boolean1 = webElement.getClass().equals(blackList.get(i).getClass());
 				Log.d(LogTag, "boolean1:"+boolean1);
 				boolean boolean2 = webElement.getClassName().equals(blackList.get(i).getClassName());
 				Log.d(LogTag, "boolean2:"+boolean2);
 				boolean boolean3 = webElement.getId().equals(blackList.get(i).getId());
 				Log.d(LogTag, "boolean3:"+boolean3);
 				boolean boolean4 = webElement.getLocationX()==(blackList.get(i).getLocationX());
 				Log.d(LogTag, "boolean4:"+boolean4);
 				boolean boolean5 = webElement.getLocationY()==(blackList.get(i).getLocationY());
 				Log.d(LogTag, "boolean5:"+boolean5);
 				boolean boolean6 = webElement.getTagName().equals(blackList.get(i).getTagName());
 				Log.d(LogTag, "boolean6:"+boolean6);
 				same = boolean1 && boolean2 && boolean3 && boolean4 && boolean5 && boolean6;
 				if(same) {				
 					return true;
			}else {
				continue;
			}
 			}
 		}
 		return same;		
 	}

 	/*
 	 * web元素
 	 */
 	@SuppressLint("NewApi")
	public Stack<WebElement> Search(Solo solo){
 		Stack<WebElement> taskStack= new Stack<WebElement>();
 		
 		ArrayList<WebElement> elementList = solo.getWebElements(By.xpath("//*[@class]"));  //57
 		Log.d(LogTag, "elementlist:"+elementList.size());
		for(WebElement element : elementList){
			try{
				Log.d(LogTag, "element:"+element);
				String className = element.getText();
				Log.d(LogTag, "classname:"+className);
				if(className != null && className !="" && !className.isEmpty() &&className !="undefined" && className.length()<10){ 							
						taskStack.push(element);	                    
 					}
			}catch(Exception e){
				e.printStackTrace();
				}
 			}		
		return taskStack;
 		}
 	//click button
	public Stack<WebElement> SearchButton(Solo solo){
 		Stack<WebElement> taskStack= new Stack<WebElement>();
		ArrayList<WebElement> elementList1 = solo.getWebElements(By.tagName("button")); //[contains(@href, ��logout��)]          By.xpath("//*[@class]")
		Log.d(LogTag, "Button elementlist1:"+elementList1.size()); 
		for(WebElement element : elementList1){
			try{
				String tagname = element.getClassName();
				Log.d(LogTag, "tagname:"+tagname);
				if (tagname.contains("goplay")){
					Thread.sleep(1);
				}else{
					Log.d(LogTag, "element:"+element);
//					if(common.blackkfiter(element,blackList)){
					taskStack.push(element);
				}
			}catch(Exception e){
				e.printStackTrace();
				}
 			}
		
		
		
		return taskStack;
 		}	
	
	public Stack<WebElement> SearchOther (Solo solo){
 		Stack<WebElement> taskStack= new Stack<WebElement>();
		ArrayList<WebElement> elementList1 = solo.getWebElements(By.xpath("//*[@id]")); //[contains(@href, ��logout��)]          By.xpath("//*[@class]")
		Log.d(LogTag, "Button elementlist1:"+elementList1.size()); 
		for(WebElement element : elementList1){
			try{
				String tagname = element.getClassName();
				Log.d(LogTag, "tagname:"+tagname);
				if (tagname.contains("goplay")){
					Thread.sleep(1);
				}else if(tagname.contains("join")){
					Log.d(LogTag, "element:"+element);
//					if(common.blackkfiter(element,blackList)){
					taskStack.push(element);
				}else{
					Log.d(LogTag, "element:"+element);
//					if(common.blackkfiter(element,blackList)){
					taskStack.push(element);
				}
			}catch(Exception e){
				e.printStackTrace();
				}
 			}
		
		
		
		return taskStack;
 		}
 	
} 

