/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午11:00:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.qianbailu.adapter.m.QianBaiLuMSListAdapter;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMPictureService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午11:00:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMSListFragment extends QianBaiLuMPictureListFragment {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	private QianBaiLuMSListAdapter mQianBaiLuMSListAdapter;

	public static QianBaiLuMSListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMSListFragment fragment = new QianBaiLuMSListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mQianBaiLuMSListAdapter = new QianBaiLuMSListAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMSListAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieJson mMovieJson = new MovieJson();
		// http://m.100av.us/list.php?classid=12&style=0&bclassid=11
		mMovieJson.setList(QianBaiLuMPictureService.parsePicture(url, pageNo));
		return mMovieJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MovieJson result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
			pageNo = 0;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		mQianBaiLuMSListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
	}

}