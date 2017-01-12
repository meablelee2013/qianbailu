/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-12上午10:45:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import com.open.qianbailu.utils.UrlUtils;

import android.content.Context;
import android.content.Intent;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-12上午10:45:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuGuestBookWebViewActivity extends QianBaiLuWebViewActivity{
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.activity.QianBaiLuWebViewActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		url = UrlUtils.PC_QIAN_BAI_LU_GUEST_BOOK;
	}
	
	public void loadUrl(){
		webview.loadUrl(url);
	}
	
	public static void startQianBaiLuGuestBookWebViewActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuGuestBookWebViewActivity.class);
		context.startActivity(intent);
	}
}
