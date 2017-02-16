package com.open.qianbailu.fragment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.open.andenginetask.AsyncTaskUtils;
import com.open.andenginetask.CallEarliest;
import com.open.andenginetask.Callable;
import com.open.andenginetask.Callback;
import com.open.andenginetask.IProgressListener;
import com.open.andenginetask.ProgressCallable;
import com.open.qianbailu.weak.WeakReferenceHandler;

/**
 * 
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-10-28上午10:36:57
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class BaseV4Fragment<T,F extends BaseV4Fragment> extends Fragment implements CallEarliest<T>, Callback<T>, Callable<T>, ProgressCallable<T>, Response.Listener<JSONObject>, Response.ErrorListener {
	public static final String TAG = BaseV4Fragment.class.getSimpleName();
	public static final String KEY_CONTENT = BaseV4Fragment.class.getSimpleName() + ":Content";
	public String mContent = "";
	public WeakReferenceHandler weakReferenceHandler;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initValues();
		bindEvent();
	}
	 

	public void setFragment(F f) {
		 weakReferenceHandler = new WeakReferenceHandler(f);
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#setUserVisibleHint(boolean)
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		initUI(isVisibleToUser);
	}
 
	private boolean isFirst = true;

	protected void initUI(final boolean isVisibleToUser) {
		if (weakReferenceHandler != null) {
			weakReferenceHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (getActivity() == null || !isVisibleToUser) {
						initUI(isVisibleToUser);
					} else {
						if(isFirst){
							weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_HANDLER, 50);
							isFirst = false;
						}else{
							weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 50);
						}
						
					}
				}
			}, 200);
		}
	}

	/**
	 * 封装的asynctask方法，此方法没有进度框.
	 * 
	 * @param pCallEarliest
	 *            运行于主线程，最先执行此方法.
	 * @param mCallable
	 *            运行于异步线程,第二执行此方法.
	 * @param mCallback
	 *            运行于主线程,最后执行此方法.
	 */
	public <T> void doAsync(final CallEarliest<T> pCallEarliest, final Callable<T> mCallable, final Callback<T> mCallback) {
		AsyncTaskUtils.doAsync(pCallEarliest, mCallable, mCallback);
	}

	/**
	 * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式.
	 * 
	 * @param pContext
	 *            上下文
	 * @param styleID
	 *            对话框样式
	 *            ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER
	 * @param pTitle
	 *            标题
	 * @param pMessage
	 *            内容
	 * @param pCallEarliest
	 *            运行于主线程，最先执行此方法.
	 * @param progressCallable
	 *            运行于异步线程,用于传递对话框进度.
	 * @param pCallback
	 *            运行于主线程,最后执行此方法.
	 */
	public <T> void doProgressAsync(final Context pContext, final int styleID, final String pTitleResID, final String pMessageResID, final CallEarliest<T> pCallEarliest,
			final ProgressCallable<T> pCallable, final Callback<T> pCallback) {

		AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID, pMessageResID, pCallEarliest, pCallable, pCallback);
	}

	/**
	 * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式.
	 * 
	 * @param pContext
	 *            上下文
	 * @param styleID
	 *            对话框样式
	 *            ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER
	 * @param pTitle
	 *            标题,资源id
	 * @param pMessage
	 *            内容,资源id
	 * @param pCallEarliest
	 *            运行于主线程，最先执行此方法.
	 * @param progressCallable
	 *            运行于异步线程,用于传递对话框进度.
	 * @param pCallback
	 *            运行于主线程,最后执行此方法.
	 */
	public <T> void doProgressAsync(final Context pContext, final int styleID, final int pTitleResID, final int pMessageResID, final CallEarliest<T> pCallEarliest,
			final ProgressCallable<T> pCallable, final Callback<T> pCallback) {

		AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID, pMessageResID, pCallEarliest, pCallable, pCallback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.andenginetask.ProgressCallable#call(com.example.andenginetask
	 * .IProgressListener)
	 */
	@Override
	public T call(IProgressListener pProgressListener) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.andenginetask.Callable#call()
	 */
	@Override
	public T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.andenginetask.Callback#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(T result) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.andenginetask.CallEarliest#onCallEarliest()
	 */
	@Override
	public void onCallEarliest() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 请求网络数据
	 * 
	 * @param href
	 *            :地址
	 */
	public void volleyJson(String href) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.Response.ErrorListener#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	public String makeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)
			url.append('?');
		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			// 不做URLEncoder处理
			// url.append(URLEncoder.encode(String.valueOf(params.get(name)),
			// UTF_8));
		}
		return url.toString().replace("?&", "?");
	}

	/** 刷新 */
	public static final int MESSAGE_HANDLER = 1000;
	/** 请求数据成功 */
	public static final int MESSAGE_HANDLER_COMPLETE = 1001;
	
	/** drop菜单选择 重新刷新数据 */
	public static final int MESSAGE_DROP_HANDLER = 1002;
	
	/** 默认位置 */
	public static final int MESSAGE_DEFAULT_POSITION = 2000;
	
	/** 在适配器里 调用onitemclick事件*/
	public static final int MESSAGE_ADAPTER_CALL_ONITEM = 9000;
	/** 在适配器里 保存*/
	public static final int MESSAGE_ADAPTER_COLLECTION = 9001;
	public static final int MESSAGE_ADAPTER_UN_COLLECTION = 9002;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.interfaces.WeakReferenceHandlerMessage#
	 * referenceHandlerMessage(android.os.Message)
	 */
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
	}

	protected ImageLoadingListener getImageLoadingListener() {
		return animateFirstListener;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public boolean onBackPressed() {
		return false;
	}

	/**
	 * 绑定事件，在onviewcreate调用
	 */
	public void bindEvent() {

	}
	
	public void initValues() {

	}

}
