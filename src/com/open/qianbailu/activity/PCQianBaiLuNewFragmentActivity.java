package com.open.qianbailu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.qianbailu.R;
import com.open.qianbailu.fragment.PCQianBaiLuNewFragment;
import com.open.qianbailu.utils.UrlUtils;

/**
 * 最近更新
 * @author think
 *
 */
public class PCQianBaiLuNewFragmentActivity  extends CommonFragmentActivity{
	private String url = UrlUtils.PC_QIAN_BAI_LU_NEW;
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
		Fragment fragment = PCQianBaiLuNewFragment.newInstance(url,true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_image, fragment).commit();
	}

	public static void startPCQianBaiLuNewFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuNewFragmentActivity.class);
		context.startActivity(intent);
	}
}