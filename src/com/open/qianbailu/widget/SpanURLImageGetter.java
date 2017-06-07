/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:03:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.widget;

import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.open.qianbailu.R;
import com.open.qianbailu.utils.QRCodeUtils;
import com.open.qianbailu.utils.ScreenUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:03:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SpanURLImageGetter implements ImageGetter {
	TextView textView;
	Context context;
	int width =200;

	public SpanURLImageGetter(Context contxt, TextView textView) {
		this.context = contxt;
		this.textView = textView;
	}

	@Override
	public Drawable getDrawable(String paramString) {
		URLDrawable urlDrawable = new URLDrawable(context,R.drawable.common_v4,width);
	    
		ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
		getterTask.execute(paramString);
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable drawable) {
			this.urlDrawable = drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				urlDrawable.drawable = result;
                Log.d("onPostExecute","result");
                SpanURLImageGetter.this.textView.requestLayout();
//                SpanURLImageGetter.this.textView.append(" ");
			}
		}

		@Override
		protected Drawable doInBackground(String... params) {
			String source = params[0];
			return fetchDrawable(source);
		}

		public Drawable fetchDrawable(String url) {
			Drawable drawable = null;
			URL Url;
			try {
				if(url.contains("pan.baidu.com/share")){
					//http://pan.baidu.com/share/qrcode?w=800&h=248&url=https://m.lu.sex/show-13-388511.html
					String src = url.split("&url=")[1];
					Bitmap qrBitmap = QRCodeUtils.generateBitmap(src,width, width);  
				    Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);  
				    Bitmap bitmap = QRCodeUtils.addLogo(qrBitmap, logoBitmap);  
				    drawable = new BitmapDrawable(bitmap);
				}else{
					Url = new URL(url);
					drawable = Drawable.createFromStream(Url.openStream(), "");
				}
			} catch (Exception e) {
				return null;
			}
			// 按比例缩放图片
			Rect bounds = getDefaultImageBounds(context);
//			int newwidth = bounds.width();
//			int newheight = bounds.height();
//			double factor = 1;
//			double fx = (double) drawable.getIntrinsicWidth() / (double) newwidth;
//			double fy = (double) drawable.getIntrinsicHeight() / (double) newheight;
//			factor = fx > fy ? fx : fy;
//			if (factor < 1)
//				factor = 1;
//			newwidth = (int) (drawable.getIntrinsicWidth() / factor);
//			newheight = (int) (drawable.getIntrinsicHeight() / factor);
			drawable.setBounds(0, 0,  bounds.width(), bounds.height());
			return drawable;
		}
	}

	// 预定图片宽高比例为 4:3
	@SuppressWarnings("deprecation")
	public Rect getDefaultImageBounds(Context context) {
//		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//		int width = display.getWidth();
//		int height = (int) (width * 3 / 4);
		int size = (int) ScreenUtils.getIntToDip(width,context);
		Rect bounds = new Rect(0, 0, size, size);
		return bounds;
	}
}