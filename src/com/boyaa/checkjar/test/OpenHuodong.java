package com.boyaa.checkjar.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.After;

import com.robotium.solo.By;
import com.robotium.solo.RobotiumUtils;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi")
public class OpenHuodong extends ActivityInstrumentationTestCase2<Activity> {
	public String LogTag="===>";
	@SuppressLint("SdCardPath")
	public static String RobotiumScreenshotsPath = "/sdcard/Robotium-Screenshots/";
	@SuppressLint("SdCardPath")
	public static String propertyFile = "/sdcard/download/config.ini";
	public Solo solo;
	boolean flag=false; 
	String act_picture="Activity";
//	String url = "";
	
    Common common = new Common();
    BitmapCompare bitmapCompare = new BitmapCompare();
	
	public static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.boyaa.checkjar.MainActivity";
	public static Class<?> launcherActivityClass;
	static{
		try{
			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
			Common.analyProperty(propertyFile);// ���������ļ�
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	public OpenHuodong(){
		super((Class<Activity>) launcherActivityClass);
	}
	@Override
    protected void setUp() throws Exception {
         solo = new Solo(getInstrumentation(), getActivity());
    }
	@After
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
 	
	public void testOpenHuoDong() throws Exception{
		Thread.sleep(5000);
 		Stack<WebElement> taskStack = new Stack<WebElement>();
 		Stack<WebElement> taskStackButton = new Stack<WebElement>();
 		Stack<WebElement> taskStackOther = new Stack<WebElement>();
 		List<String> existPage = new ArrayList<>();
 		List<WebElement> blackList = new ArrayList<>();
 		List<WebElement> blackButtonList = new ArrayList<>();
 		List<WebElement> blackButtonOther = new ArrayList<>();
 		int count=0;
 		//截图路径ַ
 		File destDir = new File(RobotiumScreenshotsPath);
 		//删除截图路径
 		common.DeleteFile(destDir);
 		if(!destDir.exists()) {
 			destDir.mkdir(); 
 		}
 		String url= Common.url;
 		String orientation = Common.orientation;
 		Log.d(LogTag, "url:"+url);
// 		for (int i = 0; i < act_id.length; i++) {
		Log.d(LogTag,"act_id:"+act_picture);
//		common.cmdOpenPageScreen(solo, screen);//�򿪻ҳ��
	 	common.cmdOpenPage(solo, url, orientation);//�򿪻ҳ��
 		Thread.sleep(6000);
 		common.takeScreenshot(solo, act_picture+"_"+count); //����
 		Thread.sleep(1000);
 		taskStack = common.Search(solo);//
 		taskStackButton = common.SearchButton(solo);
 		taskStackOther = common.SearchOther(solo);
 		existPage.add(RobotiumScreenshotsPath+act_picture+"_"+count+".jpg");  //截图
 		count++;	
 		//开始遍历
 		 while (!taskStack.isEmpty()) { 
 			 WebElement thisnode = taskStack.pop();
 			 if (!common.blackkfiter(thisnode,blackList)) {
 				 
				 Log.d(LogTag,"thisnode:"+thisnode.getText());
				 try{
					 String string = thisnode.getTagName();
					 Log.d(LogTag, "thisnode:"+string);
					 solo.clickOnWebElement(thisnode);	
					 blackList.add(thisnode);
				 	}catch(SecurityException e){
				 		Log.d(LogTag,"Ȩ�޲���");
				 		continue;
				 	}catch(Exception e){
						Log.d(LogTag,"Ԫ�ز�����");
						continue;
				 	}
				 Thread.sleep(1000);
				common.takeScreenshot(solo, act_picture+"_"+count);
 	 			count++;
 	 			Log.d(LogTag,"��ͼƬ����"+count);	
				solo.hideSoftKeyboard();
				//����ҳ��Ԫ��
				taskStack = common.Search(solo);
				taskStackButton = common.SearchButton(solo);	
				try{
		     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(text(),'填写信息')]"));
		     		Log.d(LogTag, "elementList size:"+elementList.size());
		    		for(WebElement element : elementList){ 
		    			solo.clickOnWebElement(element);
		    			Thread.sleep(1000);
						common.takeScreenshot(solo, act_picture+"_"+count);
		 	 			count++;
		    			continue;
		     		}
		     	}catch(Exception e){
		     		e.printStackTrace();
		     		continue;
		     	} 
				
		     	try{
		     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(@href,'javascript:;')]"));
		     		Log.d(LogTag, "elementList size:"+elementList.size());
		    		for(WebElement element : elementList){ 
		    			solo.clickOnWebElement(element);
		    			continue;
			     		}
			     	}catch(Exception e){
			     		e.printStackTrace();
			     		continue;
			     		}  
				try{
		     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(text(),'ȷ��')]"));
		     		Log.d(LogTag, "elementList size:"+elementList.size());
		    		for(WebElement element : elementList){ 
		    			solo.clickOnWebElement(element);
		    			continue;
		     		}
		     	}catch(Exception e){
		     		e.printStackTrace();
		     		continue;
		     	}  	
				//遍历button
				while (!taskStackButton.isEmpty()) { 
	 	 			WebElement thisButton = taskStackButton.pop();
		 			 if (!common.blackkfiter(thisButton,blackButtonList)) {
		 				 try{
							Log.d(LogTag, "thisnode:"+thisButton);
							Log.d(LogTag, "thisnode:"+thisButton.getClassName());
							Log.d(LogTag, "thisnode:"+thisButton.getTagName());
							Log.d(LogTag, "thisnode:"+thisButton.getText());
							solo.clickOnWebElement(thisButton);
							blackButtonList.add(thisButton);
							taskStackButton = common.SearchButton(solo);
							}catch(Exception e){
								Log.d(LogTag,"Ԫ�ز�����");
								continue;
							}
						Thread.sleep(1000);
						common.takeScreenshot(solo, act_picture+"_"+count);
						count++;
		 	 			Log.d(LogTag,"��ͼƬ����"+count);
						solo.hideSoftKeyboard();						
						try{
				     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(@href,'javascript:;')]"));
				     		Log.d(LogTag, "elementList size:"+elementList.size());
				    		for(WebElement element : elementList){ 
				    			solo.clickOnWebElement(element);
				    			continue;
				     		}
				     	}catch(Exception e){
				     		e.printStackTrace();
				     		continue;
				     	}  	
						try{
				     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(text(),'ȷ��')]"));
				     		Log.d(LogTag, "elementList size:"+elementList.size());
				    		for(WebElement element : elementList){ 
				    			solo.clickOnWebElement(element);
				    			continue;
				     		}
				     	}catch(Exception e){
				     		e.printStackTrace();
				     		continue;
				     	}
						try{
				     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(.,' strong-dlg-a dlg-submit-btn')]"));
				     		Log.d(LogTag, "elementList size:"+elementList.size());
				    		for(WebElement element : elementList){ 
				    			solo.clickOnWebElement(element);
				    			continue;
				     		}
				     	}catch(Exception e){
				     		e.printStackTrace();
				     		continue;
				     	} 
						try{
				     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(.,' strong-dlg-a dlg-submit-btn')]"));
				     		Log.d(LogTag, "elementList size:"+elementList.size());
				    		for(WebElement element : elementList){ 
				    			solo.clickOnWebElement(element);
				    			continue;
				     		}
				     	}catch(Exception e){
				     		e.printStackTrace();
				     		continue;
				     	}  	
				 		try{
				 			solo.clickOnView(solo.getView(Button.class, 0));
						}catch(junit.framework.AssertionFailedError e){
							Log.d(LogTag,"元素不存在");
							continue;
						}

		 		 }else{
		 			 continue;
		 		 	}
	 	 		}	
 			 }
 			while (!taskStackOther.isEmpty()) { 
 	 			WebElement thisButton = taskStackOther.pop();
	 			 if (!common.blackkfiter(thisButton,blackButtonOther)) {
	 				 try{
						Log.d(LogTag, "thisnode:"+thisButton);
						Log.d(LogTag, "thisnode:"+thisButton.getClassName());
						Log.d(LogTag, "thisnode:"+thisButton.getTagName());
						Log.d(LogTag, "thisnode:"+thisButton.getText());
						solo.clickOnWebElement(thisButton);
						blackButtonList.add(thisButton);
						blackButtonOther = common.SearchButton(solo);
						}catch(Exception e){
							Log.d(LogTag,"元素不存在");
							continue;
						}
					Thread.sleep(1000);
					common.takeScreenshot(solo, act_picture+"_"+count);
					count++;
	 	 			Log.d(LogTag,"count："+count);
					solo.hideSoftKeyboard();						
					try{
			     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(@href,'javascript:;')]"));
			     		Log.d(LogTag, "elementList size:"+elementList.size());
			    		for(WebElement element : elementList){ 
			    			solo.clickOnWebElement(element);
			    			continue;
			     		}
			     	}catch(Exception e){
			     		e.printStackTrace();
			     		continue;
			     	}  	
					try{
			     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(text(),'ȷ��')]"));
			     		Log.d(LogTag, "elementList size:"+elementList.size());
			    		for(WebElement element : elementList){ 
			    			solo.clickOnWebElement(element);
			    			continue;
			     		}
			     	}catch(Exception e){
			     		e.printStackTrace();
			     		continue;
			     	}
					try{
			     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(.,' strong-dlg-a dlg-submit-btn')]"));
			     		Log.d(LogTag, "elementList size:"+elementList.size());
			    		for(WebElement element : elementList){ 
			    			solo.clickOnWebElement(element);
			    			continue;
			     		}
			     	}catch(Exception e){
			     		e.printStackTrace();
			     		continue;
			     	} 
					try{
			     		List<WebElement> elementList = solo.getWebElements(By.xpath("//*[contains(.,' strong-dlg-a dlg-submit-btn')]"));
			     		Log.d(LogTag, "elementList size:"+elementList.size());
			    		for(WebElement element : elementList){ 
			    			solo.clickOnWebElement(element);
			    			continue;
			     		}
			     	}catch(Exception e){
			     		e.printStackTrace();
			     		continue;
			     	}  	
			 		try{
			 			solo.clickOnView(solo.getView(Button.class, 0));
					}catch(junit.framework.AssertionFailedError e){
						Log.d(LogTag,"元素不存在");
						continue;
					}

	 		 }else{
	 			 continue;
	 		 	}
 	 			
			 }
 		 }
 	 		//截图对比
 	 		bitmapCompare.PictureFilter(count, act_picture, existPage);	 		
	 		count = 0;
	 		existPage.clear();
 		}
 	} 	
// }
   

