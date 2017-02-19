/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:46:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.qianbailu.R;
import com.open.qianbailu.fragment.ContboxPullScrollFragment;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 详细页面
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:46:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ContboxScrollFragmentActivity extends CommonFragmentActivity  {
	private String url  = UrlUtils.PC_QIAN_BAI_LU_VOD;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.activity.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_pulllistview);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		} else {
			url = UrlUtils.PC_QIAN_BAI_LU_VOD;
		}
		 
		Fragment fragment = ContboxPullScrollFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_plistview, fragment).commit();
	}

	public static void startContboxFragmentActivity(Context context, String title, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, ContboxScrollFragmentActivity.class);
		context.startActivity(intent);
	}

}
