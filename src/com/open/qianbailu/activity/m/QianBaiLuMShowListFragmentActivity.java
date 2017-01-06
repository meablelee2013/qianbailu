/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午4:59:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity.m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.fragment.m.QianBaiLuMBIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMShowListFragment;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 看图list
 * @author :fengguangjing
 * @createTime:2017-1-5下午4:59:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuMShowListFragmentActivity extends CommonFragmentActivity{
	private String url = UrlUtils.QIAN_BAI_LU_SHOW;
	 /* (non-Javadoc)
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_show_image_list_fragment);
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
		Fragment fragment = QianBaiLuMShowListFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_image, fragment).commit();
	}

	public static void startQianBaiLuMShowListFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuMShowListFragmentActivity.class);
		context.startActivity(intent);
	}
}
