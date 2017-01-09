/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:53:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuBIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * pc图库首页
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:53:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuNavBIndicatorExpandableListFragment extends QianBaiLuNavMExpandableListFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_M_VLIST_B;
    
	public static PCQianBaiLuNavBIndicatorExpandableListFragment newInstance(String url, boolean isVisibleToUser,int type) {
		PCQianBaiLuNavBIndicatorExpandableListFragment fragment = new PCQianBaiLuNavBIndicatorExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#call()
	 */
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(PCQianBaiLuBIndicatorService.parseMHome(url,type));
		return mNavMJson;
	}
 
}