package com.boyaa.checkjar.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/*
 * androidͼƬ�ȶ�
 */
public class BitmapCompare {
	public static String LogTag="===>";
	BitmapFactory.Options options = new BitmapFactory.Options();
	
	public static boolean sameAs(String path1,String path2)throws FileNotFoundException{
		boolean res=false;
		FileInputStream fis1=new FileInputStream(path1);
		Bitmap bitmap1=BitmapFactory.decodeStream(fis1);
	
		FileInputStream fis2=new FileInputStream(path2);
		Bitmap bitmap2=BitmapFactory.decodeStream(fis2);
	
		res=sameAs(bitmap1,bitmap2);
	
		return res;

	}

	public static boolean sameAs(String path1,String path2,double percent)throws FileNotFoundException{
		FileInputStream fis1=new FileInputStream(path1);
		Bitmap bitmap1=BitmapFactory.decodeStream(fis1);
	
		FileInputStream fis2=new FileInputStream(path2);
		Bitmap bitmap2=BitmapFactory.decodeStream(fis2);
	
		return sameAs(bitmap1,bitmap2,percent);

	}

	public static boolean sameAs(Bitmap bitmap1,Bitmap bitmap2,double percent){
	
		int width=bitmap1.getWidth();
		int height=bitmap2.getHeight();
	
		int numDiffPixels=0;
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				if(bitmap1.getPixel(x,y)!=bitmap2.getPixel(x,y)){
					numDiffPixels++;
				}
			}
		}
		double numberPixels=height*width;
		double diffPercent=numDiffPixels/numberPixels;
		return percent<=1.0D-diffPercent;
		}
	
	@SuppressLint("NewApi")
	public static boolean sameAs(Bitmap bmp1,Bitmap bmp2)throws FileNotFoundException{
		boolean res=false;

		res=bmp1.sameAs(bmp2);

		return res;
	}

	public static Bitmap getSubImage(String path)throws FileNotFoundException{
		Bitmap res = null;
		try{
			Log.d(LogTag, path);
			FileInputStream fis=new FileInputStream(path);
			Bitmap bitmap=BitmapFactory.decodeStream(fis);
			res=Bitmap.createBitmap(bitmap);
	}catch(Exception e){
		e.printStackTrace();
		}
		return res;
	}

 	public boolean BitmapSame(String file, List<String> list) throws Exception{
 		boolean same = false;
 		Bitmap sub1=getSubImage(file);
 		for (int i = 0; i < list.size(); i++) {
 			Log.d(LogTag, "size:"+list.size());
 			Log.d(LogTag, list.get(i));
 			Bitmap sub2=getSubImage(list.get(i));
 			Log.d(LogTag, "sub2:"+sub2);

			same=sameAs(sub1,sub2,0.85);
// 			Log.d(LogTag, "Common.same:"+Common.same);
// 			same=sameAs(sub1,sub2,Double.parseDouble(Common.same));
			Log.d(LogTag, "same:"+same);
 			if(same){
 				return true;
 			}
		}
 		return same;
 	}
 	
 	/*
 	 * ��ͼ����
 	 */
 	public void PictureFilter(int count, String act_id,  List<String> existPage) throws Exception{
 		//ɸѡͼƬ
 		for (int i = 1; i < count; i++) {
	 		String path = OpenHuodong.RobotiumScreenshotsPath + act_id + "_"+ i+".jpg";
//	 		Log.d(LogTag,"path��"+path);
 	 		File file = new File(path);
 	 		boolean same1 = BitmapSame(path,existPage);
 	 		if(same1){
 	 			file.delete();
 	 		}else{
 	 			existPage.add(path);
 	 		}			
		}
 	}

}
