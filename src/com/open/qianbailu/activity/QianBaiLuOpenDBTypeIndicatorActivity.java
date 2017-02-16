/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:26:07
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
import com.open.qianbailu.fragment.QianBaiLuOpenDBTypeIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 根据type取收藏
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:26:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuOpenDBTypeIndicatorActivity extends CommonFragmentActivity<NavMJson> {
	private String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_p_fragment);
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
		Fragment fragment = QianBaiLuOpenDBTypeIndicatorFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_p, fragment).commit();
	}

	public static void startQianBaiLuOpenDBTypeIndicatorActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuOpenDBTypeIndicatorActivity.class);
		context.startActivity(intent);
	}
}
