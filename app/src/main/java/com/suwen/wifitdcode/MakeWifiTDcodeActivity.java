package com.suwen.wifitdcode;

import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class MakeWifiTDcodeActivity extends Activity implements OnClickListener{
	
	private Context mContext;
	private EditText wifiName;
	private EditText wifiKey;
	private RadioButton wpaRaido;
	private RadioButton wepRaido;
	private CheckBox checkBox;
	private ImageView haiXing;
	private ImageButton moreType;
	private ImageButton create;
	private ImageButton makeBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_wifi_tdcode);
		mContext = this;
		wifiName = (EditText)findViewById(R.id.wifiName);
		wifiKey = (EditText)findViewById(R.id.wifiKey);
		wpaRaido = (RadioButton)findViewById(R.id.wpaRaido);
		wepRaido = (RadioButton)findViewById(R.id.wepRaido);
		checkBox = (CheckBox)findViewById(R.id.checkBox);
		haiXing = (ImageView)findViewById(R.id.haiXing);
		moreType = (ImageButton)findViewById(R.id.moreType);
		create = (ImageButton)findViewById(R.id.create);
		makeBack = (ImageButton)findViewById(R.id.makeBack);
		haiXing.setOnClickListener(this);
		moreType.setOnClickListener(this);
		create.setOnClickListener(this);
		makeBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.makeBack:
			onBackPressed();
			break;
		case R.id.create:
			String ssid = wifiName.getText().toString().trim();
			if (ssid == null || ssid.equals("")) {
				Toast.makeText(mContext, "WIFI名称不能为空",
						Toast.LENGTH_SHORT).show();
				break;
			}
			String type = wpaRaido.isChecked()? "wpa":"wep";
			String string = "WIFI:S:"+ ssid +";T:"+ type +";P:" +
					wifiKey.getText().toString().trim() +";";
			intent.setClass(mContext, SaveTDcodeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("string", string);
			startActivity(intent);
			break;
		case R.id.moreType:
			
			break;
		case R.id.haiXing:
			SpotManager.getInstance(mContext).showSpotAds(mContext);
			break;
		}
	}

}
