package com.suwen.wifitdcode;

import java.util.Date;

import com.suwen.wifitdcode.util.ButtonAnimation;
import com.umeng.analytics.MobclickAgent;

import net.youmi.android.spot.SpotManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private Context mContext;

	private int timeTag = 0;
	private long timeOne = 0;
	private long timeTwo = 0;

	private ImageButton pinkHippo;
	private ImageButton sweepTDcode;
	private ImageButton makeTDcode;
	private ImageButton blueHippo;
	private ImageButton personageCenter;
	private ImageButton myTDcode;
	
	private ButtonAnimation buttonAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		pinkHippo = (ImageButton) findViewById(R.id.pinkHippo);
		sweepTDcode = (ImageButton) findViewById(R.id.sweepTDcode);
		makeTDcode = (ImageButton) findViewById(R.id.makeTDcode);
		blueHippo = (ImageButton) findViewById(R.id.blueHippo);
		personageCenter = (ImageButton) findViewById(R.id.personageCenter);
		myTDcode = (ImageButton) findViewById(R.id.myTDcode);
		pinkHippo.setOnClickListener(this);
		sweepTDcode.setOnClickListener(this);
		makeTDcode.setOnClickListener(this);
		blueHippo.setOnClickListener(this);
		personageCenter.setOnClickListener(this);
		myTDcode.setOnClickListener(this);
		buttonAnimation = new ButtonAnimation(mContext);
		buttonAnimation.setTouchListener(pinkHippo);
		buttonAnimation.setTouchListener(sweepTDcode);
		buttonAnimation.setTouchListener(makeTDcode);
		buttonAnimation.setTouchListener(blueHippo);
		buttonAnimation.setTouchListener(personageCenter);
		buttonAnimation.setTouchListener(myTDcode);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Main");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		SpotManager.getInstance(mContext).disMiss();
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("Main");
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (buttonAnimation.isPress) {
			return;
		}
		switch (v.getId()) {
		case R.id.pinkHippo:
			SpotManager.getInstance(mContext).showSpotAds(mContext);
			break;
		case R.id.sweepTDcode:
			intent.setClass(mContext, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, 1);
			break;
		case R.id.makeTDcode:
			intent.setClass(mContext, MakeWifiTDcodeActivity.class);
			mContext.startActivity(intent);
			break;
		case R.id.blueHippo:
			SpotManager.getInstance(mContext).showSpotAds(mContext);
			break;
		case R.id.personageCenter:
			
			break;
		case R.id.myTDcode:
			
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && data != null) {
			Bundle bundle = data.getExtras();
			String tempUrl = bundle.getString("result");
			if (tempUrl.contains("http://") && tempUrl.endsWith(".apk")) {
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Date date = new Date();
			if (timeTag == 0) {
				timeOne = date.getTime();
				timeTag = 1;
				Toast.makeText(mContext, "再按一次退出WiFi扫一扫", Toast.LENGTH_SHORT)
						.show();
			} else if (timeTag == 1) {
				timeTwo = date.getTime();
				if (timeTwo - timeOne <= 3000) {
					((Activity) mContext).finish();
					timeTag = 0;
					System.exit(0);
				} else {
					timeTag = 1;
					timeOne = date.getTime();
					Toast.makeText(mContext, "再按一次退出WiFi扫一扫",
							Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
