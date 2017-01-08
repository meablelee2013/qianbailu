/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-11-23下午1:01:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.weak;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.open.qianbailu.activity.BaseTabActivity;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-11-23下午1:01:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class WeakTabActivityReferenceHandler extends Handler {
	WeakReference<BaseTabActivity> weakReferenceHandler;

	public WeakTabActivityReferenceHandler(BaseTabActivity activity) {
		weakReferenceHandler = new WeakReference<BaseTabActivity>(activity);
	}

	@SuppressLint("NewApi") @Override
	public void handleMessage(Message msg) {
		BaseTabActivity activity = weakReferenceHandler.get();
		if (activity != null && !activity.isFinishing()) {
			if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR1){
				if(!activity.isDestroyed() ){
					activity.handlerMessage(msg);
					super.handleMessage(msg);
				}
			}else{
				activity.handlerMessage(msg);
				super.handleMessage(msg);
			}
			
		}
	}

}
