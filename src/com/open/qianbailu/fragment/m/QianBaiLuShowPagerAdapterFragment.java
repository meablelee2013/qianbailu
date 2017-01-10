/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:33:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.qianbailu.R;
import com.open.qianbailu.adapter.m.QianBaiLuShowPagerAdapter;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMMovieDetailService;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:33:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuShowPagerAdapterFragment extends BaseV4Fragment<ShowJson, QianBaiLuShowPagerAdapterFragment>{
	private ViewPager viewpager;
	public QianBaiLuShowPagerAdapter mQianBaiLuShowPagerAdapter;
	private List<ShowBean> list = new ArrayList<ShowBean>();
	private String url = UrlUtils.QIAN_BAI_LU_MOVIE;
	private WeakActivityReferenceHandler weakActivityReferenceHandler;
	private int position;
	
	public static QianBaiLuShowPagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler) {
		QianBaiLuShowPagerAdapterFragment fragment = new QianBaiLuShowPagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		return fragment;
	}
	
	public static QianBaiLuShowPagerAdapterFragment newInstance(String url, boolean isVisibleToUser,WeakActivityReferenceHandler weakActivityReferenceHandler,List<ShowBean> list,int position) {
		QianBaiLuShowPagerAdapterFragment fragment = new QianBaiLuShowPagerAdapterFragment();
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakActivityReferenceHandler = weakActivityReferenceHandler;
		if(list!=null && list.size()>0){
			fragment.list = list;
			fragment.position = position;
		}
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_show_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		return view;
	}
	
	 /* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mQianBaiLuShowPagerAdapter = new QianBaiLuShowPagerAdapter(getActivity(), list,weakActivityReferenceHandler);
		viewpager.setAdapter(mQianBaiLuShowPagerAdapter);
		if(list!=null && list.size()>0){
			mQianBaiLuShowPagerAdapter.notifyDataSetChanged();
			viewpager.setCurrentItem(position);
		}else{
			doAsync(this, this, this);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public ShowJson call() throws Exception {
		// TODO Auto-generated method stub
		ShowJson mShowJson = new ShowJson();
		mShowJson.setList(QianBaiLuMMovieDetailService.parseShow(url));
		return mShowJson;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(ShowJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mQianBaiLuShowPagerAdapter.notifyDataSetChanged();
	}
}
