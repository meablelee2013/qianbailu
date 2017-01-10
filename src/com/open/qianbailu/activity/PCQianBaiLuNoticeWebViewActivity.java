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

import com.open.qianbailu.utils.UrlUtils;

import android.content.Context;
import android.content.Intent;

/**
 *****************************************************************************************************************************************************************************
 * 网站公告
 * @author :fengguangjing
 * @createTime:2017-1-10上午11:27:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuNoticeWebViewActivity extends QianBaiLuWebViewActivity {

	/* (non-Javadoc)
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#initIntent()
	 */
	@Override
	protected void initIntent() {
		// TODO Auto-generated method stub
		super.initIntent();
		url = UrlUtils.PC_QIAN_BAI_LU_NOTICE;
	}
	
	public static void startPCQianBaiLuNoticeWebViewActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuNoticeWebViewActivity.class);
		context.startActivity(intent);
	}

}
