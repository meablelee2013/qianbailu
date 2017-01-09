/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:32:40
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import android.support.v4.app.Fragment;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.m.QianBaiLuMBIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMPictureListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuNavMBIndicatorExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuIndicatorService;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:32:40
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuBIndicatorFragment extends QianBaiLuMIndicatorFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_M_VLIST_B;
	
	public static PCQianBaiLuBIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuBIndicatorFragment fragment = new PCQianBaiLuBIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(PCQianBaiLuIndicatorService.parseMNav(url,"图片"));
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
			if(bean.getTitle().equals("图片首页")){
				fragment = PCQianBaiLuNavBIndicatorExpandableListFragment.newInstance(url, true,2);
			}else if(bean.getTitle().equals("成人动漫")){
				//http://www.1111av.co/html/tupian/dongman/
				//http://www.1111av.co/html/tupian/dongman/index.html
				fragment = PCQianBaiLuPictureListFragment.newInstance(bean.getHref().replace("index.html", ""), false);
//				fragment = CommonV4Fragment.newInstance(bean.getHref(), false);
			}else{
				fragment = PCQianBaiLuPictureListFragment.newInstance(bean.getHref(), false);
//				fragment = CommonV4Fragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	
}
