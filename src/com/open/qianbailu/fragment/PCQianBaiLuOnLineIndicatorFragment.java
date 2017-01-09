/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:34:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import android.support.v4.app.Fragment;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:34:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuOnLineIndicatorFragment extends QianBaiLuMIndicatorFragment{
	public String url = UrlUtils.PC_QIAN_BAI_LU_ONLINE;
	
	public static PCQianBaiLuOnLineIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuOnLineIndicatorFragment fragment = new PCQianBaiLuOnLineIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(PCQianBaiLuIndicatorService.parseMNav(url,"在线"));
		return mNavMJson;
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		
		Fragment fragment;
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if(bean.getTitle().equals("在线视频")){
				fragment = PCQianBaiLuNavIndicatorExpandableListFragment.newInstance(url, true);
			} else{
				fragment = PCQianBaiLuMovieListFragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}