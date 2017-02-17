/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-16下午3:58:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.PCQianBaiLuMoveDetailFragmentActivity;
import com.open.qianbailu.activity.PCQianBaiLuShowListFragmentActivity;
import com.open.qianbailu.activity.PCQianBaiLuXiaoShuoFragmentActivity;
import com.open.qianbailu.activity.QianBaiLuOpenDBActivity;
import com.open.qianbailu.activity.QianBaiLuWebViewActivity;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.adapter.db.OpenDBListTypeAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.db.OpenDBJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-16下午3:58:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuOpenDBTypeFragment extends BaseV4Fragment<OpenDBJson, QianBaiLuOpenDBTypeFragment> implements OnItemClickListener{
	private String url;
	private int type;
	private PullToRefreshListView mPullRefreshListView;
	private OpenDBListTypeAdapter mOpenDBListAdapter;
	private List<OpenDBBean> list = new ArrayList<OpenDBBean>();
	
	public static QianBaiLuOpenDBTypeFragment newInstance(String url,int type, boolean isVisibleToUser) {
		QianBaiLuOpenDBTypeFragment fragment = new QianBaiLuOpenDBTypeFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_movie_listview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mOpenDBListAdapter = new OpenDBListTypeAdapter(getActivity(), weakReferenceHandler,list);
		mPullRefreshListView.setAdapter(mOpenDBListAdapter);
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullRefreshListView.setOnItemClickListener(this);
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
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#call()
	 */
	@Override
	public OpenDBJson call() throws Exception {
		// TODO Auto-generated method stub
		OpenDBJson mOpenDBJson = new OpenDBJson();
		mOpenDBJson.setList(QianBaiLuOpenDBService.queryListType(getActivity(),type));
		return mOpenDBJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.BaseFragmentActivity#onCallback(java.lang
	 * .Object)
	 */
	@Override
	public void onCallback(OpenDBJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		mOpenDBListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.BaseFragmentActivity#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		case MESSAGE_ADAPTER_CALL_ONITEM:
			onItemClick(null, null, msg.arg1, msg.arg1);
			break;
		case MESSAGE_ADAPTER_UN_COLLECTION:
			QianBaiLuOpenDBService.delete(getActivity(), list.get(msg.arg1));
			list.remove(msg.arg1);
			mOpenDBListAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if ((id) != -1 && list != null && list.size() > 0) {
			list.get((int) id).setState(1);
			mOpenDBListAdapter.notifyDataSetChanged();
			OpenDBBean bean = list.get((int) id);
			switch (bean.getType()) {
			case 1:
				QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(getActivity(), bean.getUrl());
				break;
			case 2:
				QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(getActivity(), bean.getUrl());
				break;
			case 3:
				QianBaiLuMMoveDetailFragmentActivity.startQianBaiLuMMoveDetailFragmentActivity(getActivity(), bean.getUrl());
				break;
			case 4:
				PCQianBaiLuXiaoShuoFragmentActivity.startPCQianBaiLuXiaoShuoFragmentActivity(getActivity(), bean.getUrl());
				break;
			case 5:
				PCQianBaiLuShowListFragmentActivity.startPCQianBaiLuShowListFragmentActivity(getActivity(), bean.getUrl());
				break;
			case 6:
				PCQianBaiLuMoveDetailFragmentActivity.startPCQianBaiLuMoveDetailFragmentActivity(getActivity(), bean.getUrl());
				break;
			default:
				QianBaiLuWebViewActivity.startUmeiWebViewActivity(getActivity(), bean.getUrl());
				break;
			}
		}

	}
	
}
