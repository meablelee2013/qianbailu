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

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
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
public class QianBaiLuMSListFragment extends QianBaiLuMPictureListFragment implements OnClickListener{
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	public QianBaiLuMSListAdapter mQianBaiLuMSListAdapter;
	private View footview;
	private Button text_fisrt;
	private Button text_pre;
	private EditText edit_current;
	private Button text_current;
	private Button text_next;
	private Button text_last;
	private boolean isautomatic;
	private int maxPageNo ;
	
	public static QianBaiLuMSListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMSListFragment fragment = new QianBaiLuMSListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_movie_listview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pc_qianbailu_movie_pager_foot, null);
		text_fisrt = (Button) footview.findViewById(R.id.text_fisrt);
		text_pre = (Button) footview.findViewById(R.id.text_pre);
		edit_current = (EditText) footview.findViewById(R.id.edit_current);
		text_current = (Button) footview.findViewById(R.id.text_current);
		text_next = (Button) footview.findViewById(R.id.text_next);
		text_last = (Button) footview.findViewById(R.id.text_last);
		return view;
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
		edit_current.setText(""+pageNo);
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_fisrt:
			pageNo = 0;
			break;
		case R.id.text_last:
			pageNo = maxPageNo;
			break;
		case R.id.text_pre:
			if(pageNo<=1){
				pageNo = 1;
			}
			pageNo = pageNo-1;
			break;
		case R.id.text_next:
			pageNo = pageNo+1;
			if(pageNo>=maxPageNo){
				pageNo=maxPageNo;
			}
			break;
		case R.id.text_current:
			String pageNostr = edit_current.getText().toString();
			if(pageNostr!=null){
				pageNo = Integer.parseInt(pageNostr.replace(" ", ""));
			}
			break;
		default:
			break;
		}
		isautomatic = true;
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
	}

}
