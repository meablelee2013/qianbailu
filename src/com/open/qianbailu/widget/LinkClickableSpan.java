/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:02:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.widget;

import com.open.qianbailu.activity.QianBaiLuWebViewActivity;
import com.open.qianbailu.utils.UrlUtils;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7上午11:02:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class LinkClickableSpan extends ClickableSpan {
	private String url;  
	private Context context;
	
	public LinkClickableSpan(Context context,String url){
		this.context = context;
		this.url = url;
	}
	
	/* (non-Javadoc)
	 * @see android.text.style.ClickableSpan#onClick(android.view.View)
	 */
	@Override
	public void onClick(View widget) {
		 Log.d("LinkClickableSpan", "url=="+url);
		 //Actvity was not found for intent, Intent { act=android.intent.action.VIEW dat=/html/article/jiqing/2017/0606/397352_2.html (has extras) }
		 if(url.contains(UrlUtils.QIAN_BAI_LU)){
			 QianBaiLuWebViewActivity.startUmeiWebViewActivity(context, url);
		 }else if(url.contains(UrlUtils.QIAN_BAI_LU_M)){
			 QianBaiLuWebViewActivity.startUmeiWebViewActivity(context, url);
		 }else{
			 url = UrlUtils.QIAN_BAI_LU + url;
			 QianBaiLuWebViewActivity.startUmeiWebViewActivity(context, url);
		 }
	}
	

}