/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-16下午3:50:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-2-16下午3:50:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuOpenDBTypeIndicatorFragment extends QianBaiLuMIndicatorFragment {
	public String url;

	public static QianBaiLuOpenDBTypeIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuOpenDBTypeIndicatorFragment fragment = new QianBaiLuOpenDBTypeIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		List<NavMBean> listm = new ArrayList<NavMBean>();
		NavMBean navBean = new NavMBean();
		navBean.setTitle("m电影");
		navBean.setType(3);
		listm.add(navBean);

		navBean = new NavMBean();
		navBean.setTitle("m图库");
		navBean.setType(2);
		listm.add(navBean);

		navBean = new NavMBean();
		navBean.setTitle("m小说");
		navBean.setType(1);
		listm.add(navBean);

		navBean = new NavMBean();
		navBean.setTitle("pc电影");
		navBean.setType(6);
		listm.add(navBean);

		navBean = new NavMBean();
		navBean.setTitle("pc图库");
		navBean.setType(5);
		listm.add(navBean);

		navBean = new NavMBean();
		navBean.setTitle("pc小说");
		navBean.setType(4);
		listm.add(navBean);

		mNavMJson.setList(listm);
		return mNavMJson;
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		titleList.clear();

		Fragment fragment = CommonV4Fragment.newInstance(url, false);
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if (bean.getType() == 3) {
				fragment = QianBaiLuOpenDBTypeFragment.newInstance(url, bean.getType(), true);
			} else {
				fragment = QianBaiLuOpenDBTypeFragment.newInstance(url, bean.getType(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}
