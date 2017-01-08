package com.open.qianbailu.activity.m;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonTabActivity;
import com.open.qianbailu.json.m.NavMJson;

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
public class MainTabActivity extends CommonTabActivity<NavMJson> implements OnCheckedChangeListener {
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
		setContentView(R.layout.activity_tab_main);
		init();
	}

	
	
	
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		mTabHost = getTabHost();
	}




	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		
		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main1));
		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main2));
		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main3));
		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main4));

		tab_main1.setContent(new Intent(this, QianBaiLuMMainFragmentActivity.class)).setIndicator(getString(R.string.tab_main1));
		tab_main2.setContent(new Intent(this, QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main2));
		tab_main3.setContent(new Intent(this, QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main3));
		tab_main4.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main4));

		mTabHost.addTab(tab_main1);
		mTabHost.addTab(tab_main2);
		mTabHost.addTab(tab_main3);
		mTabHost.addTab(tab_main4);
		radio1.setChecked(true);
	}


	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mRadioGroup.setOnCheckedChangeListener(this);
	}


	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		return super.call();
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
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
		default:
			break;
		}

	}

}