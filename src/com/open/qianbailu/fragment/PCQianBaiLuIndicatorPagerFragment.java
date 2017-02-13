/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9上午10:25:13
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
import com.open.qianbailu.fragment.m.QianBaiLuMMovieListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuNavMIndicatorExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9上午10:25:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuIndicatorPagerFragment extends QianBaiLuMIndicatorFragment{
	public String url = UrlUtils.PC_QIAN_BAI_LU_VLIST;
	
	public static PCQianBaiLuIndicatorPagerFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuIndicatorPagerFragment fragment = new PCQianBaiLuIndicatorPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(PCQianBaiLuIndicatorService.parseMNav(url,"电影"));
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
			if(bean.getTitle().equals("电影首页")){
				fragment = PCQianBaiLuNavIndicatorExpandableListFragment.newInstance(url, true);
			}else if(bean.getTitle().equals("在线视频")){
				bean.setHref(UrlUtils.QIAN_BAI_LU+"/list/9.html");
				fragment = PCQianBaiLuMovieListPagerFragment.newInstance(bean.getHref(), false);
			} else{
				fragment = PCQianBaiLuMovieListPagerFragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}
