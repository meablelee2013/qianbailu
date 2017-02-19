package com.open.qianbailu.fragment;

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
import com.open.qianbailu.adapter.m.QianBaiLuMSListAdapter;
import com.open.qianbailu.fragment.m.QianBaiLuMPictureListFragment;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNewService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuNewFragment extends QianBaiLuMPictureListFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_NEW;
	public QianBaiLuMSListAdapter mQianBaiLuMSListAdapter;

	public static PCQianBaiLuNewFragment newInstance(String url,
			boolean isVisibleToUser) {
		PCQianBaiLuNewFragment fragment = new PCQianBaiLuNewFragment();
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
		// TODO Auto-generated method stub
		mQianBaiLuMSListAdapter = new QianBaiLuMSListAdapter(getActivity(),
				list);
		mPullRefreshListView.setAdapter(mQianBaiLuMSListAdapter);
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
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// Do work to refresh the list here.
						if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
							pageNo = 0;
							weakReferenceHandler
									.sendEmptyMessage(MESSAGE_HANDLER);
						} else if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
							pageNo++;
							weakReferenceHandler
									.sendEmptyMessage(MESSAGE_HANDLER);
						}
					}
				});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				list.get((int) id).setState(1);
				mQianBaiLuMSListAdapter.notifyDataSetChanged();
				PCQianBaiLuMoveDetailFragmentActivity
						.startPCQianBaiLuMoveDetailFragmentActivity(
								getActivity(), list.get((int) id).getLinkurl());
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieJson mMovieJson =   new MovieJson();
		mMovieJson.setList( PCQianBaiLuNewService.parsePicture(url));
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
			if (isautomatic) {
				if (result.getList() != null && result.getList().size() > 0) {
					list.addAll(result.getList());
				}
			} else {
				list.clear();
				list.addAll(result.getList());
				pageNo = 0;
			}
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		mQianBaiLuMSListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
	}

}
