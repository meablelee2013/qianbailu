/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10上午11:46:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuHotService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-10上午11:46:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuHotExpandableListFragment extends QianBaiLuNavMExpandableListFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_HOT;
    
	public static PCQianBaiLuHotExpandableListFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuHotExpandableListFragment fragment = new PCQianBaiLuHotExpandableListFragment();
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
		mNavMJson.setList(PCQianBaiLuHotService.parseHot(url));
		return mNavMJson;
	}
 
}