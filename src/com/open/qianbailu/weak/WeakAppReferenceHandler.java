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

import android.os.Handler;
import android.os.Message;

import com.open.qianbailu.fragment.BaseAppFragment;

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
public class WeakAppReferenceHandler<F extends BaseAppFragment> extends Handler {
	WeakReference<F> weakReferenceHandler;

	public WeakAppReferenceHandler(F fragment) {
		weakReferenceHandler = new WeakReference<F>(fragment);
	}

	@Override
	public void handleMessage(Message msg) {
		F fragment = weakReferenceHandler.get();
		if (fragment != null && fragment.isVisible() && fragment.getUserVisibleHint()) {
			fragment.handlerMessage(msg);
			super.handleMessage(msg);
		}
	}

}
