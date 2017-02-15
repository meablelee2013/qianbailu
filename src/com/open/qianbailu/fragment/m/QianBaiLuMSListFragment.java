/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午11:00:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuMSListAdapter;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMPictureService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 小说列表
 * @author :fengguangjing
 * @createTime:2017-1-3上午11:00:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMSListFragment extends QianBaiLuMPictureListFragment  {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	public QianBaiLuMSListAdapter mQianBaiLuMSListAdapter;
	
	public static QianBaiLuMSListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMSListFragment fragment = new QianBaiLuMSListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		mPullRefreshListView.getRefreshableView().addFooterView(footview);
		// TODO Auto-generated method stub
		mQianBaiLuMSListAdapter = new QianBaiLuMSListAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMSListAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
		edit_current.setText(""+pageNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					pageNo = 0;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(getActivity(), list.get((int)id).getLinkurl());
			}
		});
		text_fisrt.setOnClickListener(this);
		text_pre.setOnClickListener(this);
		text_current.setOnClickListener(this);
		text_next.setOnClickListener(this);
		text_last.setOnClickListener(this);
		edit_current.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieJson mMovieJson = QianBaiLuMPictureService.parsePictureJson(url, pageNo);
		// http://m.100av.us/list.php?classid=12&style=0&bclassid=11
		return mMovieJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MovieJson result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			if(isautomatic){
				if (result.getList() != null && result.getList().size() > 0) {
					list.addAll(result.getList());
				}
			}else{
				list.clear();
				list.addAll(result.getList());
				pageNo = 0;
			}
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		maxPageNo = result.getMaxpageno();
		mQianBaiLuMSListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
		edit_current.setText(""+pageNo);
		isautomatic = false;
	}
	

}
