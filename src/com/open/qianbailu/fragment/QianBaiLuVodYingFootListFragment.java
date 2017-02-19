package com.open.qianbailu.fragment;

import com.open.qianbailu.fragment.m.QianBaiLuMDianYingFootListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;

public class QianBaiLuVodYingFootListFragment extends
		QianBaiLuMDianYingFootListFragment {
	public static QianBaiLuVodYingFootListFragment newInstance(String url,
			boolean isVisibleToUser) {
		QianBaiLuVodYingFootListFragment fragment = new QianBaiLuVodYingFootListFragment();
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
		mNavMJson.setList(QianBaiLuMNavService.parseVodyingFoot(url));
		return mNavMJson;
	}
}
