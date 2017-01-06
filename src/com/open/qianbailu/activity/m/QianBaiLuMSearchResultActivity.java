/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.open.indicator.TabPageIndicator;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.adapter.CommonFragmentPagerAdapter;
import com.open.qianbailu.bean.SearchBean;
import com.open.qianbailu.fragment.CommonV4Fragment;
import com.open.qianbailu.fragment.m.QianBaiLuMSearchResultListFragment;
import com.open.qianbailu.json.m.SearchJson;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMSearchResultActivity extends CommonFragmentActivity<SearchJson> {
	private String url = UrlUtils.QIAN_BAI_LU_SEARCH_RESULT;
	ViewPager viewpager;
	TabPageIndicator indicator;
	List<String> titleList = new ArrayList<String>();
	private List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	CommonFragmentPagerAdapter mRankPagerAdapter;
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.CommonFragmentActivity#onCreate(android.os
	 * .Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_search_result);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		doAsync(this, this, this);
	}

	@Override
	public SearchJson call() throws Exception {
		// TODO Auto-generated method stub
		SearchJson mSearchJson = new SearchJson();
		ArrayList<SearchBean> tlist = new ArrayList<SearchBean>();
		SearchBean bean = new SearchBean();
		bean.setTitle("小说");
		bean.setType(1);
		tlist.add(bean);
		
		bean = new SearchBean();
		bean.setTitle("图片");
		bean.setType(2);
		tlist.add(bean);
		
		bean = new SearchBean();
		bean.setTitle("电影");
		bean.setType(3);
		tlist.add(bean);
		mSearchJson.setTypelist(tlist);
		return mSearchJson;
	}

	@Override
	public void onCallback(SearchJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		titleList.clear();
		/**
		 * <select name="type" style="height:32px;margin-left:10px">
				  <option value ="3">电影</option>
				  <option value ="2">图片</option>
				  <option value="1" selected="selected">小说</option>
				</select>
		 */

		Fragment fragment;
		for (SearchBean bean : result.getTypelist()) {
			titleList.add(bean.getTitle());
			String url2 = url+"&type="+bean.getType();
			if(bean.getType()==1){
				fragment = QianBaiLuMSearchResultListFragment.newInstance(url2, true,bean.getType());
			}else{
				fragment = QianBaiLuMSearchResultListFragment.newInstance(url2, false,bean.getType());
			}
			listRankFragment.add(fragment);
		}
		 
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.CommonFragmentActivity#onClick(android.view
	 * .View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}

	public static void startQianBaiLuMSearchResultActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuMSearchResultActivity.class);
		context.startActivity(intent);
	}
}
