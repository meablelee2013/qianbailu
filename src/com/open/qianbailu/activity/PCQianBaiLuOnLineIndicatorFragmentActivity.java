/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:32:49
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
import com.open.qianbailu.fragment.PCQianBaiLuIndicatorFragment;
import com.open.qianbailu.fragment.PCQianBaiLuOnLineIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:32:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuOnLineIndicatorFragmentActivity extends CommonFragmentActivity<NavMJson>{
	private String url = UrlUtils.PC_QIAN_BAI_LU_ONLINE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_fragment);
		init();
	}

	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
	}

	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		Fragment fragment = PCQianBaiLuOnLineIndicatorFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_indicator, fragment).commit();
	}

	public static void startPCQianBaiLuOnLineIndicatorFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuOnLineIndicatorFragmentActivity.class);
		context.startActivity(intent);
	}
}