package com.suwen.wifitdcode;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

import com.suwen.wifitdcode.app.WIfiTDcodeApp;
import com.suwen.wifitdcode.util.BitMapManage;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class LoadingActivity extends Activity {
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		mContext = this;
		ImageView loading = (ImageView)findViewById(R.id.loading);
		BitmapDrawable background = new BitmapDrawable(this.getResources(),
				BitMapManage.readBitmap(this, R.drawable.loading, Config.ARGB_8888));
		loading.setBackgroundDrawable(background);
		//友盟统计初始化
		MobclickAgent.updateOnlineConfig(this);
		//有米广告初始化
		AdManager.getInstance(this).init(WIfiTDcodeApp.youmiId, WIfiTDcodeApp.youmiKey, true);
		//异步预加载广告
		SpotManager.getInstance(this).loadSpotAds();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(mContext, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		}).start();
	}
}
