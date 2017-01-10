/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10上午11:27:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;

import com.open.qianbailu.js.QianBaiLuAndroidJavaScript;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 *****************************************************************************************************************************************************************************
 *  
 * @author :fengguangjing
 * @createTime:2017-1-10上午11:27:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuDownLoadWebViewActivity extends QianBaiLuWebViewActivity {

	/* (non-Javadoc)
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#initIntent()
	 */
	@Override
	protected void initIntent() {
		// TODO Auto-generated method stub
		super.initIntent();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.newsinfo.CommonActivity#initValue()
	 */
	protected void initValue() {
		// TODO Auto-generated method stub
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webview.addJavascriptInterface(new QianBaiLuAndroidJavaScript(this,weakReferenceHandler), "android");
		webview.setWebViewClient(mWebViewClientBase);
		webview.setWebChromeClient(mWebChromeClientBase);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(true);
		// 扩大比例的缩放
		webSettings.setUseWideViewPort(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		Log.i("WebViewActivity", "url==" + url);
		loadUrl();
	}
	
	public void loadUrl(){
		url = "file:///android_asset/downconvert.html";
		webview.loadUrl(url);
	}
	
	public static void startPCQianBaiLuDownLoadWebViewActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuDownLoadWebViewActivity.class);
		context.startActivity(intent);
	}

	/* (non-Javadoc)
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		if(msg.what ==MESSAGE_JS_ANDROID_CALLBACK){
			ClipboardManager cm =(ClipboardManager)  getSystemService(Context.CLIPBOARD_SERVICE);
			//读取剪贴板数据
			System.out.println("javascript:oldurl_android");
			webview.loadUrl("javascript:oldurl_android('"+cm.getText().toString()+"')"); 
		}
	}
}
