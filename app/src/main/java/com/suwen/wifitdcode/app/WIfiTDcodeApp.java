package com.suwen.wifitdcode.app;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;

public class WIfiTDcodeApp extends Application {
	
	public final static String youmiId = "8fdcc10e6f47dca6";
	public final static String youmiKey = "b983688ac665667c";
	
	/**
	 * 文件存放根位置
	 */
	public static String rootPath;
	
	/**
	 * 屏幕高宽
	 */
	public static int screenHeight = 800;
	public static int screenWidth = 480;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		rootPath = Environment.getExternalStorageDirectory() + "/WIFI扫一扫/";
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdir();
		}
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenHeight = wm.getDefaultDisplay().getHeight();
		screenWidth = wm.getDefaultDisplay().getWidth();
	}

}
