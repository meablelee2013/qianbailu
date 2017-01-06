/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.open.qianbailu.R;
import com.open.qianbailu.adapter.m.QianBaiLuNavMExpandableListAdapter;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * fragment主页面
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuNavMExpandableListFragment extends
		BaseV4Fragment<NavMJson, QianBaiLuNavMExpandableListFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_M;
	public ExpandableListView expendablelistview;
	public QianBaiLuNavMExpandableListAdapter mQianBaiLuNavMExpandableListAdapter;
	public List<NavMBean> list = new ArrayList<NavMBean>();

	public static QianBaiLuNavMExpandableListFragment newInstance(String url,
			boolean isVisibleToUser) {
		QianBaiLuNavMExpandableListFragment fragment = new QianBaiLuNavMExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_qianbailu_nav_m_expandable_listview,
				container, false);
		expendablelistview = (ExpandableListView) view
				.findViewById(R.id.expendablelistview);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();

		expendablelistview.setGroupIndicator(null);
		mQianBaiLuNavMExpandableListAdapter = new QianBaiLuNavMExpandableListAdapter(
				getActivity(), list);
		expendablelistview.setAdapter(mQianBaiLuNavMExpandableListAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		expendablelistview.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		expendablelistview
				.setOnGroupExpandListener(new OnGroupExpandListener() {
					@Override
					public void onGroupExpand(int groupPosition) {

					}
				});
		expendablelistview
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {
					@Override
					public void onGroupCollapse(int groupPosition) {

					}
				});
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
		mNavMJson.setList(QianBaiLuMNavService.parseMHome(url));
		return mNavMJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mQianBaiLuNavMExpandableListAdapter.notifyDataSetChanged();

		for (int i = 0; i < mQianBaiLuNavMExpandableListAdapter.getGroupCount(); i++) {
			expendablelistview.expandGroup(i);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
}