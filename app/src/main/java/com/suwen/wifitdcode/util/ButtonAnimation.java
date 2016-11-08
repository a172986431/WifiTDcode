package com.suwen.wifitdcode.util;

import com.suwen.wifitdcode.R;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class ButtonAnimation {
	private Animation animShrink;
	private Animation animZoom;
	
	//防止点到两个
	public boolean isPress = false;
	private boolean endAinm = false;
	
	public ButtonAnimation(Context context){
		animShrink = AnimationUtils.loadAnimation(context,
				R.anim.button_shrink);
		animShrink.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if (endAinm) {					
					animShrink.setFillAfter(false);
					endAinm = false;
				}else {
					endAinm = true;
				}
			}
		});
		animZoom = AnimationUtils.loadAnimation(context,
				R.anim.button_zoom); 
		animZoom.setFillAfter(true);
	}
	
	public void setTouchListener(final View view) {
		view.setOnTouchListener(new OnTouchListener() {
			boolean canUp = false;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (!isPress) {						
						animShrink.setFillAfter(true);
						view.startAnimation(animShrink);
						isPress = true;
						canUp = true;
					}
					break;
				case MotionEvent.ACTION_UP:
					if (canUp) {
						if (endAinm) {							
							view.startAnimation(animZoom);
							endAinm = false;
						}else {							
							endAinm = true;
						}
						isPress = false;
						canUp = false;
					}
					break;
				}
				return false;
			}
		});
	}

}
