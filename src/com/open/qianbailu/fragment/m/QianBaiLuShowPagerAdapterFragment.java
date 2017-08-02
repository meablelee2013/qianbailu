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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.m.QianBaiLuShowPagerAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.jsoup.m.QianBaiLuMMovieDetailService;
import com.open.qianbailu.utils.NetWorkUtils;
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
	private TextView text_page_foot;
	
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
		text_page_foot = (TextView) view.findViewById(R.id.text_page_foot);
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
			text_page_foot.setText((position+1)+" / "+list.size());
		}else{
			doAsync(this, this, this);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				text_page_foot.setText((position+1)+" / "+list.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
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
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mShowJson.setList(QianBaiLuMMovieDetailService.parseShow(url));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("QianBaiLuMMovieDetailService-parseShow");
			    openbean.setTitle(gson.toJson(mShowJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"QianBaiLuMMovieDetailService-parseShow");
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mShowJson = gson.fromJson(result, ShowJson.class);
		}
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
		if(result==null){
			return;
		}
		list.clear();
		list.addAll(result.getList());
		mQianBaiLuShowPagerAdapter.notifyDataSetChanged();
		text_page_foot.setText((position+1)+" / "+list.size());
	}
}
