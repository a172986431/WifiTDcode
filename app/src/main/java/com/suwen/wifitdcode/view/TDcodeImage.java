package com.suwen.wifitdcode.view;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.suwen.wifitdcode.app.WIfiTDcodeApp;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TDcodeImage extends View {
	
	private Context mContext;
	private String content = "wifi";
	private Bitmap tdCoeBitmap;
	private Canvas bmCanvas;
	private Paint cPaint;
	//图片的圆角
	private final float round = 10;
	
	private int tdWhiteColor = 0xffffffff;
	private int tdBlackColor = 0xff000000;
	
	private int QR_WIDTH = WIfiTDcodeApp.screenWidth*2/3;
	private int QR_HEIGHT = WIfiTDcodeApp.screenWidth*2/3;
	
	private int View_WIDTH = 0;
	private int View_HEIGHT = 0;

	public TDcodeImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public TDcodeImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public TDcodeImage(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	private void init(){
		View_WIDTH = QR_WIDTH + (int)round*2;
		View_HEIGHT = QR_HEIGHT + (int)round*2;
		tdCoeBitmap = Bitmap.createBitmap(View_WIDTH, View_HEIGHT, Config.ARGB_8888);
		bmCanvas = new Canvas(tdCoeBitmap);
		cPaint = new Paint();
		cPaint.setColor(0xffffff00);
		bmCanvas.drawRoundRect(new RectF(0, 0, View_WIDTH, View_HEIGHT), round, round, cPaint);
		createImage();
	}
	
	public void setColor(int white,int black){
		tdWhiteColor = white;
		tdBlackColor = black;
	}
	
	// 生成QR图
    public void createImage() {
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
                        pixels[y * QR_WIDTH + x] = tdBlackColor;
                    } else {
                        pixels[y * QR_WIDTH + x] = tdWhiteColor;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
//			Bitmap bitmap = createOneDCode("29497573822929497573822");
            bmCanvas.drawBitmap(bitmap, round, round, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(tdCoeBitmap, 0, 0, null);
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//取原有的View大小 也可以用this.getWidth();
//		int height = View.MeasureSpec.getSize(heightMeasureSpec);    
//	    int width = View.MeasureSpec.getSize(widthMeasureSpec);
	    //用来自定义View的大小
		setMeasuredDimension(View_WIDTH,View_HEIGHT);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
	 *
	 * @param content 将要生成一维码的内容
	 * @return 返回生成好的一维码bitmap
	 * @throws WriterException WriterException异常
	 */
	public Bitmap createOneDCode(String content) throws WriterException {
		// 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.CODE_128, 1080, 400);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int start = 0;
		int end = width;
		Log.e("elang","createOneDCode->" + matrix.getTopLeftOnBit()[0] + " w + h " + matrix.getBottomRightOnBit()[0]);
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
					if (start == 0){
						start = x - 20;
					}else {
						end = x + 20;
					}
				}else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		Log.e("elang",start + "  " + end);
		Bitmap bitmap = Bitmap.createBitmap(end - start, height,
				Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(pixels,0,width,-start,0,end,height,false,cPaint);
		// 通过像素数组生成bitmap,具体参考api
//		bitmap.setPixels(pixels, 0, width, -start, 0, end, height);
		return bitmap;
	}

}
