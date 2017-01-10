/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10下午2:33:25
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.open.qianbailu.utils.DownLoadUtils;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10下午2:33:25
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuAndroidJavaScript {
	private Context mcontext;
	private WeakActivityReferenceHandler weakReferenceHandler;
	
	public QianBaiLuAndroidJavaScript(Context mcontext,WeakActivityReferenceHandler weakReferenceHandler){
		this.mcontext = mcontext;
		this.weakReferenceHandler =weakReferenceHandler;
	}
	
	@JavascriptInterface
	public void OnDownloadClick_android(String url){
		System.out.println("OnDownloadClick_android=="+url);
		DownLoadUtils.downLoad(mcontext, url);
	}
	
	@JavascriptInterface
	public void oldurl_android(){
		 System.out.println("oldurl_android");
		 weakReferenceHandler.sendEmptyMessage(8000);
	}

}
