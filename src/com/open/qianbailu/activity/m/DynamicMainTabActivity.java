package com.open.qianbailu.activity.m;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.gson.Gson;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonTabActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.ScreenUtils;
import com.open.qianbailu.utils.UrlUtils;

/**
 * 
 ***************************************************************************************************************************************************************************** 
 * 动态主tab页面
 * 
 * @author :fengguangjing
 * @createTime:2016-10-28上午10:35:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class DynamicMainTabActivity extends CommonTabActivity<NavMJson> {
	private TabHost mTabHost;
	private RadioGroup mRadioGroup;
	private String url = UrlUtils.QIAN_BAI_LU_M;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		setContentView(R.layout.activity_tab_main_dynamic);
		init();
	}
	
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mTabHost = getTabHost();
		doAsync(this, this, this);
	}




	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		
//		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main1));
//		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main2));
//		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main3));
//		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main4));
//
//		tab_main1.setContent(new Intent(this, QianBaiLuMMainFragmentActivity.class)).setIndicator(getString(R.string.tab_main1));
//		tab_main2.setContent(new Intent(this, QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main2));
//		tab_main3.setContent(new Intent(this, QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main3));
//		tab_main4.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main4));
//
//		mTabHost.addTab(tab_main1);
//		mTabHost.addTab(tab_main2);
//		mTabHost.addTab(tab_main3);
//		mTabHost.addTab(tab_main4);
	}


	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
	}


	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson  mNavMJson = new NavMJson();
		if(NetWorkUtils.isNetworkAvailable(this)){
			mNavMJson.setList(QianBaiLuMNavService.parseMNav(url));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("QianBaiLuMNavService-parseMNav");
			    openbean.setTitle(gson.toJson(mNavMJson));
			    QianBaiLuOpenDBService.insert(this, openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(this, url,"QianBaiLuMNavService-parseMNav");
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mNavMJson = gson.fromJson(result, NavMJson.class);
		}
		return mNavMJson;
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		for(int i=0;i<result.getList().size();i++){
			NavMBean mbean = result.getList().get(i);
			TabSpec tab_main = mTabHost.newTabSpec(mbean.getTitle());
			if(mbean.getTitle().equals("首页")){
				tab_main.setContent(new Intent(this, QianBaiLuMMainFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("电影")){
			   tab_main.setContent(new Intent(this, QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("图库")){
			   tab_main.setContent(new Intent(this, QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("小说")){
			   tab_main.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}
			mTabHost.addTab(tab_main);
			
			View viewRadio = LayoutInflater.from(this).inflate(R.layout.layout_tab_main_dynamic_radio, null);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			radio.setText(mbean.getTitle());
			
			final int position = i;
			radio.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mTabHost.setCurrentTab(position);
					setCurrentTab(position);
				}
			});
			if(i==0){
				radio.setChecked(true);
			}else {
				radio.setChecked(false);
			}
			LayoutParams params = new LayoutParams((int)ScreenUtils.getIntToDip(this, 95), LayoutParams.WRAP_CONTENT);
			mRadioGroup.addView(viewRadio,params);
		}
	}
	
	protected void setCurrentTab(int position){
		for(int i=0;i<mRadioGroup.getChildCount();i++){
			View viewRadio = mRadioGroup.getChildAt(i);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			if(i!=position){
				radio.setChecked(false);
			} else{
				radio.setChecked(true);
			}
		}
	}
	
	protected int getCurrentTab(){
		int position = 0;
		for(int i=0;i<mRadioGroup.getChildCount();i++){
			View viewRadio = mRadioGroup.getChildAt(i);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			if(radio.isChecked()){
				position = i;
			}
		}
		return position;
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
//	 * .widget.RadioGroup, int)
//	 */
//	@Override
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		switch (checkedId) {
//		case R.id.radio1:
//			mTabHost.setCurrentTab(0);
//			break;
//		case R.id.radio2:
//			mTabHost.setCurrentTab(1);
//			break;
//		case R.id.radio3:
//			mTabHost.setCurrentTab(2);
//			break;
//		case R.id.radio4:
//			mTabHost.setCurrentTab(3);
//			break;
//		default:
//			break;
//		}
//
//	}

}