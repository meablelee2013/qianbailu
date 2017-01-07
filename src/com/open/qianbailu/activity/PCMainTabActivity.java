package com.open.qianbailu.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMBIndicatorFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMIndicatorFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMMainFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMSIndicatorFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMSearchActivity;

/**
 * 
 ***************************************************************************************************************************************************************************** 
 * 主tab页面
 * 
 * @author :fengguangjing
 * @createTime:2016-10-28上午10:35:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PCMainTabActivity extends TabActivity implements OnCheckedChangeListener {
	private TabHost mTabHost;
	RadioGroup mRadioGroup;
	RadioButton radio1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		setContentView(R.layout.activity_tab_main_pc);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mRadioGroup.setOnCheckedChangeListener(this);
		mTabHost = getTabHost();

		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main_pc1));
		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main_pc2));
		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main_pc3));
		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main_pc4));
		TabSpec tab_main5 = mTabHost.newTabSpec(getString(R.string.tab_main_pc5));
		TabSpec tab_main6 = mTabHost.newTabSpec(getString(R.string.tab_main_pc6));

		tab_main1.setContent(new Intent(this, QianBaiLuMMainFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc1));
		tab_main2.setContent(new Intent(this, QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc2));
		tab_main3.setContent(new Intent(this, QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc3));
		tab_main4.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc4));
		tab_main5.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc5));
		tab_main6.setContent(new Intent(this, QianBaiLuMSearchActivity.class)).setIndicator(getString(R.string.tab_main_pc6));

		mTabHost.addTab(tab_main1);
		mTabHost.addTab(tab_main2);
		mTabHost.addTab(tab_main3);
		mTabHost.addTab(tab_main4);
		mTabHost.addTab(tab_main5);
		mTabHost.addTab(tab_main6);

		mRadioGroup.setOnCheckedChangeListener(this);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio1.setChecked(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio1:
			mTabHost.setCurrentTab(0);
			break;
		case R.id.radio2:
			mTabHost.setCurrentTab(1);
			break;
		case R.id.radio3:
			mTabHost.setCurrentTab(2);
			break;
		case R.id.radio4:
			mTabHost.setCurrentTab(3);
			break;
		case R.id.radio5:
			mTabHost.setCurrentTab(4);
			break;
		case R.id.radio6:
			mTabHost.setCurrentTab(5);
			break;
		default:
			break;
		}

	}

}