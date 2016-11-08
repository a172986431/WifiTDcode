package com.suwen.wifitdcode.dialogs;

import com.suwen.wifitdcode.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MessageDialog implements OnClickListener {
	
	private Context mContext;
	private Dialog dialog;
	private DialogButtonOnClick onClick;
	
	private TextView content;
	private Button makeSure;
	private Button cancel;
	
	public MessageDialog(Context context,DialogButtonOnClick onClick,String title){
		mContext = context;
		dialog = new Dialog(context, R.style.dialog);
		this.onClick = onClick;
		initDialog();
		if (title != null && !title.equals("")) {			
			content.setText(title);
		}
	}
	
	private void initDialog(){
		dialog.setContentView(R.layout.dialog_message);
		dialog.setCanceledOnTouchOutside(false);
		content = (TextView)dialog.findViewById(R.id.content);
		makeSure = (Button)dialog.findViewById(R.id.makeSure);
		cancel = (Button)dialog.findViewById(R.id.cancel);
		makeSure.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	public void showDialog(){
		dialog.show();
	}
	
	public void cancelDialog(){
		dialog.cancel();
	}
	
	public void setNoCancel(){
		cancel.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.makeSure:
			if (onClick == null) {				
				cancelDialog();
			}else {
				onClick.buttonOnClick(null);
				cancelDialog();
			}
			break;
		case R.id.cancel:
			cancelDialog();
			break;
		}
	}

}
