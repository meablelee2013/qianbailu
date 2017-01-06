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

import android.support.v4.app.Fragment;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 图库
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:02:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMBIndicatorFragment extends QianBaiLuMIndicatorFragment {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B;
	
	public static QianBaiLuMBIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMBIndicatorFragment fragment = new QianBaiLuMBIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		
		Fragment fragment;
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if(bean.getTitle().equals("首页")){
				fragment = QianBaiLuNavMBIndicatorExpandableListFragment.newInstance(url, true);
			}else{
				fragment = QianBaiLuMPictureListFragment.newInstance(bean.getHref(), false);
//				fragment = CommonV4Fragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	
}
