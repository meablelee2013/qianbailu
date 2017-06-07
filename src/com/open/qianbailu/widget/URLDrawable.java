/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:04:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.open.qianbailu.utils.ScreenUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:04:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class URLDrawable extends BitmapDrawable {
	protected Drawable drawable;

	@SuppressWarnings("deprecation")
	public URLDrawable(Context context,int resid,int width) {
		this.setBounds(getDefaultImageBounds(context,width));
		drawable = context.getResources().getDrawable(resid);
		drawable.setBounds(getDefaultImageBounds(context,width));
	}

	@Override
	public void draw(Canvas canvas) {
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}

	@SuppressWarnings("deprecation")
	public Rect getDefaultImageBounds(Context context,int width) {
//		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//		int width = display.getWidth();
//		int height = (int) (width * 3 / 4);
		int size = (int) ScreenUtils.getIntToDip(width,context);
		Rect bounds = new Rect(0, 0, size, size);
		return bounds;
	}
}