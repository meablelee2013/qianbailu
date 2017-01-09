/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:31:30
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
import com.open.qianbailu.activity.m.QianBaiLuMBIndicatorFragmentActivity;
import com.open.qianbailu.fragment.PCQianBaiLuBIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMBIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * pc图库
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:31:30
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuBIndicatorFragmentActivity extends CommonFragmentActivity<NavMJson> {
	private String url = UrlUtils.PC_QIAN_BAI_LU_M_VLIST_B;

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
		Fragment fragment = PCQianBaiLuBIndicatorFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_p, fragment).commit();
	}

	public static void startPCQianBaiLuBIndicatorFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuBIndicatorFragmentActivity.class);
		context.startActivity(intent);
	}
}

