/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10上午9:58:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10上午9:58:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuNavExpandableListFragment extends QianBaiLuNavMExpandableListFragment {
	public String url = UrlUtils.QIAN_BAI_LU;
	
	public static PCQianBaiLuNavExpandableListFragment newInstance(String url,
			boolean isVisibleToUser) {
		PCQianBaiLuNavExpandableListFragment fragment = new PCQianBaiLuNavExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
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
		mNavMJson.setList(PCQianBaiLuNavService.parseMHome(url));
		return mNavMJson;
	}
}
