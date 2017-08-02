package com.open.qianbailu.activity;

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
import com.open.qianbailu.activity.m.QianBaiLuMMainFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.ScreenUtils;
import com.open.qianbailu.utils.UrlUtils;

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
public class PCMainTabActivity extends CommonTabActivity<NavMJson>     {
	private TabHost mTabHost;
	RadioGroup mRadioGroup;
	private String url = UrlUtils.QIAN_BAI_LU;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		setContentView(R.layout.activity_tab_main_pc);
		init();
//
//		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main_pc1));
//		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main_pc2));
//		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main_pc3));
//		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main_pc4));
//		TabSpec tab_main5 = mTabHost.newTabSpec(getString(R.string.tab_main_pc5));
//		TabSpec tab_main6 = mTabHost.newTabSpec(getString(R.string.tab_main_pc6));
//
//		tab_main1.setContent(new Intent(this, QianBaiLuMMainFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc1));
//		tab_main2.setContent(new Intent(this, QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc2));
//		tab_main3.setContent(new Intent(this, QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc3));
//		tab_main4.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc4));
//		tab_main5.setContent(new Intent(this, QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main_pc5));
//		tab_main6.setContent(new Intent(this, QianBaiLuMSearchActivity.class)).setIndicator(getString(R.string.tab_main_pc6));
//
//		mTabHost.addTab(tab_main1);
//		mTabHost.addTab(tab_main2);
//		mTabHost.addTab(tab_main3);
//		mTabHost.addTab(tab_main4);
//		mTabHost.addTab(tab_main5);
//		mTabHost.addTab(tab_main6);
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
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson  mNavMJson = new NavMJson();
		if(NetWorkUtils.isNetworkAvailable(this)){
			mNavMJson.setList(PCQianBaiLuNavService.parseMNav(url));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuNavService-parseMNav");
			    openbean.setTitle(gson.toJson(mNavMJson));
			    QianBaiLuOpenDBService.insert(this, openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(this, url,"PCQianBaiLuNavService-parseMNav");
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
		if(result==null){
			return;
		}
		for(int i=0;i<result.getList().size();i++){
			NavMBean mbean = result.getList().get(i);
			TabSpec tab_main = mTabHost.newTabSpec(mbean.getTitle());
			if(mbean.getTitle().equals("首页")){
				tab_main.setContent(new Intent(this, PCQianBaiLuMainFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("电影")){
			   tab_main.setContent(new Intent(this, PCQianBaiLuIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("图片")){
			   tab_main.setContent(new Intent(this, PCQianBaiLuBIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("小说")){
			   tab_main.setContent(new Intent(this, PCQianBaiLuSIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("在线")){
				tab_main.setContent(new Intent(this, PCQianBaiLuOnLineIndicatorFragmentActivity.class)).setIndicator(mbean.getTitle());
			}else if(mbean.getTitle().equals("搜索")){
				tab_main.setContent(new Intent(this, PCQianBaiLuSearchActivity.class)).setIndicator(mbean.getTitle());
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
			LayoutParams params = new LayoutParams((int)ScreenUtils.getIntToDip(this, 90), LayoutParams.WRAP_CONTENT);
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

}