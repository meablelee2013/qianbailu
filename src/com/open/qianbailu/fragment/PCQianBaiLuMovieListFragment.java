/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9上午10:43:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.qianbailu.activity.PCQianBaiLuMoveDetailFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMMovieListFragment;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.PCQianBaiLuMovieService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9上午10:43:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PCQianBaiLuMovieListFragment extends QianBaiLuMMovieListFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_VLIST_CLASSID;

	public static PCQianBaiLuMovieListFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuMovieListFragment fragment = new PCQianBaiLuMovieListFragment();
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
	public MovieJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieJson mMovieJson = new MovieJson();
		mMovieJson.setList(PCQianBaiLuMovieService.parseMovie(url, pageNo));
		return mMovieJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		pageNo = 1;
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
					pageNo = 1;
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
				list.get((int)id).setState(1);
				mQianBaiLuMMovieListAdapter.notifyDataSetChanged();
				PCQianBaiLuMoveDetailFragmentActivity.startPCQianBaiLuMoveDetailFragmentActivity(getActivity(), list.get((int) id).getLinkurl());
			}
		});
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
			list.clear();
			list.addAll(result.getList());
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		mQianBaiLuMMovieListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
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
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		case MESSAGE_ADAPTER_CALL_ONITEM:
			PCQianBaiLuMoveDetailFragmentActivity.startPCQianBaiLuMoveDetailFragmentActivity(getActivity(), list.get(msg.arg1).getLinkurl());
			break;
		case MESSAGE_ADAPTER_COLLECTION:
			MovieBean bean = list.get(msg.arg1);
			OpenDBBean openbean = new OpenDBBean();
			openbean.setUrl(bean.getLinkurl());
			openbean.setType(6);
			openbean.setImgsrc(bean.getThumb());
			openbean.setTitle(bean.getTitle());
			openbean.setTypename(bean.getVmtype());
			openbean.setTime("");
			QianBaiLuOpenDBService.insert(getActivity(), openbean);
			break;
		default:
			break;
		}
	}
}
