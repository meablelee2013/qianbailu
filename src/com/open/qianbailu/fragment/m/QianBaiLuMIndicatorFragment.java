/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:02:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.indicator.TabPageIndicator;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonFragmentPagerAdapter;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.fragment.CommonV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 电影 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:02:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMIndicatorFragment extends BaseV4Fragment<NavMJson, QianBaiLuMIndicatorFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST;
	public ArrayList<NavMBean> list = new ArrayList<NavMBean>();
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	
	public static QianBaiLuMIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMIndicatorFragment fragment = new QianBaiLuMIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_indicator_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}
	
	 /* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
	}
	
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(QianBaiLuMIndicatorService.parseMNav(url));
		return mNavMJson;
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		
		Fragment fragment;
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if(bean.getTitle().equals("首页")){
				fragment = QianBaiLuNavMIndicatorExpandableListFragment.newInstance(url, true);
			}else{
				fragment = QianBaiLuMMovieListFragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
}
