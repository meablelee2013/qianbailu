/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import android.os.Message;

import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * fragment电影首页
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuNavMIndicatorExpandableListFragment extends QianBaiLuNavMExpandableListFragment {
	public String url = UrlUtils.QIAN_BAI_LU_M;

	public static QianBaiLuNavMIndicatorExpandableListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuNavMIndicatorExpandableListFragment fragment = new QianBaiLuNavMIndicatorExpandableListFragment();
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
		mNavMJson.setList(QianBaiLuMIndicatorService.parseMHome(url));
		return mNavMJson;
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