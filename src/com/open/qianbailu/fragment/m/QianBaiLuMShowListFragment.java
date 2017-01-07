/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:05:17
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
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuMShowAdapter;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMShowImageService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 看图list
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:05:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMShowListFragment extends BaseV4Fragment<ShowJson, QianBaiLuMShowListFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	public PullToRefreshListView mPullRefreshListView;
	public QianBaiLuMShowAdapter mQianBaiLuMShowAdapter;
	public List<ShowBean> list = new ArrayList<ShowBean>();
	private View footview;
	private TextView text_pretitle, text_nexttitle;
	private TextView text_newstitle;

	public static QianBaiLuMShowListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMShowListFragment fragment = new QianBaiLuMShowListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_show_image_foot_listview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		text_newstitle = (TextView) view.findViewById(R.id.text_newstitle);
		
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_qianbailu_m_show_image_foot, null);
		text_pretitle = (TextView) footview.findViewById(R.id.text_pretitle);
		text_nexttitle = (TextView) footview.findViewById(R.id.text_nexttitle);
		return view;
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
		ListView listview = mPullRefreshListView.getRefreshableView();
		listview.addFooterView(footview);
		Fragment fragment = QianBaiLuMShowFootListFragment.newInstance(url, true,2);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_foot, fragment).commit();

		mQianBaiLuMShowAdapter = new QianBaiLuMShowAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMShowAdapter);
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
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
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});

		text_pretitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!text_pretitle.getTag().toString().contains("很抱歉已经没有了") && !text_pretitle.getTag().toString().equals(url)) {
					QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(getActivity(), text_pretitle.getTag().toString());
				}
			}
		});
		text_nexttitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!text_nexttitle.getTag().toString().contains("很抱歉已经没有了") && !text_nexttitle.getTag().toString().equals(url)) {
					QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(getActivity(), text_nexttitle.getTag().toString());
				}
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
		ShowJson mShowJson = QianBaiLuMShowImageService.parsePicture(url);
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

		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		list.clear();
		list.addAll(result.getList());

		mQianBaiLuMShowAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();

		text_pretitle.setText(result.getPreTitle());
		text_pretitle.setTag(result.getPreHref());
		text_nexttitle.setText(result.getNextTitle());
		text_nexttitle.setTag(result.getNextHref());

		text_newstitle.setText(result.getNewsTitle() + result.getNeswTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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
