/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午1:41:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.m.QianBaiLuMShowAdapter;
import com.open.qianbailu.fragment.m.QianBaiLuMDianYingFootListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMMovieDetailFragment;
import com.open.qianbailu.json.m.MovieDetailJson;
import com.open.qianbailu.jsoup.PCQianBaiLuMovieDetailService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午1:41:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuVodDetailFragment extends QianBaiLuMMovieDetailFragment {
	  
	 
	public static PCQianBaiLuVodDetailFragment newInstance(String url, boolean isVisibleToUser,int type) {
		PCQianBaiLuVodDetailFragment fragment = new PCQianBaiLuVodDetailFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}

//	/* (non-Javadoc)
//	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
//	 */
//	@Override
//	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onViewCreated(view, savedInstanceState);
//		url = UrlUtils.PC_QIAN_BAI_LU_MOVIE;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		ListView listview = mPullRefreshListView.getRefreshableView();
		listview.addHeaderView(headview);
		listview.addFooterView(footview);
		
		Fragment fragment = QianBaiLuVodYingFootListFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_foot, fragment).commit();

		mQianBaiLuMShowAdapter = new QianBaiLuMShowAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMShowAdapter);
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieDetailJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieDetailJson mMovieDetailJson = PCQianBaiLuMovieDetailService.parsePicture(url);
		return mMovieDetailJson;
	}

  
}

