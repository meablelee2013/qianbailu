package com.open.qianbailu.activity.m;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.open.indicator.TabPageIndicator;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.adapter.CommonFragmentPagerAdapter;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.CommonV4Fragment;
import com.open.qianbailu.fragment.m.QianBaiLuMBIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.UrlUtils;

/**
 * 
 * @author think
 *
 */
public class QianBaiLuMIndicatorActivity extends CommonFragmentActivity<NavMJson>{
	private String url = UrlUtils.QIAN_BAI_LU_M;
	ArrayList<NavMBean> list = new ArrayList<NavMBean>();
	ViewPager viewpager;
	TabPageIndicator indicator;
	List<String> titleList = new ArrayList<String>();
	private List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	CommonFragmentPagerAdapter mRankPagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_indicator_viewpager);
		init();
	}

	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		doAsync(this, this, this);
	}

	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
	}

	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		mNavMJson.setList(QianBaiLuMNavService.parseMNav(url));
		return mNavMJson;
	}

	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		
		Fragment fragment;
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if(bean.getTitle().equals("首页")){
				fragment = QianBaiLuNavMExpandableListFragment.newInstance(bean.getHref(), true);
			}else if(bean.getTitle().equals("电影")){
				fragment = QianBaiLuMIndicatorFragment.newInstance(bean.getHref(), false);
			}else if(bean.getTitle().equals("图库")){
				fragment = QianBaiLuMBIndicatorFragment.newInstance(bean.getHref(), false);
			}else{
				fragment = CommonV4Fragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}

}
