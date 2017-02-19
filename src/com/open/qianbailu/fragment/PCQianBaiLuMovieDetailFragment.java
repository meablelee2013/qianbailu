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

import com.open.qianbailu.fragment.m.QianBaiLuMMovieDetailFragment;
import com.open.qianbailu.json.m.MovieDetailJson;
import com.open.qianbailu.jsoup.PCQianBaiLuMovieDetailService;

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
public class PCQianBaiLuMovieDetailFragment extends QianBaiLuMMovieDetailFragment {
	  
	 
	public static PCQianBaiLuMovieDetailFragment newInstance(String url, boolean isVisibleToUser,int type) {
		PCQianBaiLuMovieDetailFragment fragment = new PCQianBaiLuMovieDetailFragment();
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
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieDetailJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieDetailJson mMovieDetailJson = PCQianBaiLuMovieDetailService.parsePicture(url);
		return mMovieDetailJson;
	}

  
}

