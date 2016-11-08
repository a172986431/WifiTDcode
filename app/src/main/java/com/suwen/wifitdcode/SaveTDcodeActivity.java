package com.suwen.wifitdcode;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.suwen.wifitdcode.app.WIfiTDcodeApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class SaveTDcodeActivity extends Activity {
	
	private Context mContext;
	private ImageView TDcode;
	private String content;
	
	private int QR_WIDTH = WIfiTDcodeApp.screenWidth*2/3;
	private int QR_HEIGHT = WIfiTDcodeApp.screenWidth*2/3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_tdcode);
		mContext = this;
		TDcode = (ImageView)findViewById(R.id.TDcode);
		Intent intent = getIntent();
		if (intent != null) {
			content = intent.getStringExtra("string");
		}
//		createImage();
	}
	
	// 生成QR图
    private void createImage() {
        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();

//            String text = "扫描二维码扫描二维码";
            
            if (content == null || "".equals(content) || content.length() < 1) {
            	Toast.makeText(mContext, "生成失败请返回重试!",
    					Toast.LENGTH_SHORT).show();
                return;
            }

            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(content, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

//            Log.e("zhu","w:" + martix.getWidth() + "h:"
//                    + martix.getHeight());
            QR_WIDTH = martix.getWidth();
            QR_HEIGHT = martix.getHeight();

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(content,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            TDcode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
