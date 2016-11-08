package com.suwen.wifitdcode.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;

public class BitMapManage {
	
	/**
	 * 通过传入位图,新的宽.高比进行位图的缩放操作   
	 * @param bitmap
	 * @param w 缩放后的宽度
	 * @param h 缩放后的高度
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {   
	  
	        // load the origial Bitmap   
	        Bitmap BitmapOrg = bitmap;   
	  
	        int width = BitmapOrg.getWidth();   
	        int height = BitmapOrg.getHeight();   
	        int newWidth = w;   
	        int newHeight = h;   
   
	        //缩放比例
	        float scaleWidth = ((float) newWidth) / width;   
	        float scaleHeight = ((float) newHeight) / height;   
	  
	        // create a matrix for the manipulation   
	        Matrix matrix = new Matrix();   
	        // resize the Bitmap   
	        if (scaleWidth > scaleHeight) {
				matrix.postScale(scaleHeight, scaleHeight);
			}else {				
				matrix.postScale(scaleWidth, scaleWidth);   
			}
	  
	        // recreate the new Bitmap   
	        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,   
	                height, matrix, true);   
	  
	        return resizedBitmap;   
	    } 
	/**
	 * 将bitmap转为二进制数组
	 * @param bmp
	 * @param needRecycle 是否需要回收
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 极大的减少图片对内存的消耗
	 */
	public static Bitmap readBitmap(Context context, int id ,Bitmap.Config config){
	     BitmapFactory.Options opt = new BitmapFactory.Options();
	     opt.inPreferredConfig = config;  //Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数图片有损,默认为RGB_8888
	     opt.inInputShareable=true;
	     opt.inPurgeable=true;//设置图片可以被回收
	     InputStream is = context.getResources().openRawResource(id);
	     return BitmapFactory.decodeStream(is, null, opt);
	} 
}
