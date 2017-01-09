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
public class PCQianBaiLuMovieDetailFragment extends QianBaiLuMMovieDetailFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_MOVIE;
	 
	public static PCQianBaiLuMovieDetailFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuMovieDetailFragment fragment = new PCQianBaiLuMovieDetailFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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

