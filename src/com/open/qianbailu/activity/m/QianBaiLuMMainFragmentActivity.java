package com.open.qianbailu.activity.m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.fragment.m.QianBaiLuMBIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.utils.UrlUtils;

/**
 * 
 ***************************************************************************************************************************************************************************** 
 * fragment主页面
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:11:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMMainFragmentActivity extends CommonFragmentActivity<NavMJson> {
	private String url = UrlUtils.QIAN_BAI_LU_M;

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
		Fragment fragment = QianBaiLuNavMExpandableListFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_p, fragment).commit();
	}

	public static void startQianBaiLuMMainFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuMMainFragmentActivity.class);
		context.startActivity(intent);
	}
}
