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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuMPictureListAdapter;
import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMPictureService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 图库
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午11:00:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMPictureListFragment extends BaseV4Fragment<MovieJson, QianBaiLuMPictureListFragment> implements OnClickListener {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	public PullToRefreshListView mPullRefreshListView;
	public QianBaiLuMPictureListAdapter mQianBaiLuMPictureListAdapter;
	public List<MovieBean> list = new ArrayList<MovieBean>();
	public int pageNo = 0;
	public View footview;
	public Button text_fisrt;
	public Button text_pre;
	public EditText edit_current;
	public Button text_current;
	public Button text_next;
	public Button text_last;
	public boolean isautomatic;
	public int maxPageNo;

	public static QianBaiLuMPictureListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMPictureListFragment fragment = new QianBaiLuMPictureListFragment();
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
		// TODO Auto-generated method stub
		mPullRefreshListView.getRefreshableView().addFooterView(footview);
		mQianBaiLuMPictureListAdapter = new QianBaiLuMPictureListAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMPictureListAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);

		edit_current.setText("" + pageNo);

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
				list.get((int) id).setState(1);
				mQianBaiLuMPictureListAdapter.notifyDataSetChanged();
				QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(getActivity(), list.get((int) id).getLinkurl());
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
		super.onCallback(result);

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
		maxPageNo = result.getMaxpageno();
		mQianBaiLuMPictureListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
		edit_current.setText("" + pageNo);
		isautomatic = false;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
	}

	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response);
				// Gson gson = new Gson();
				//
				// String result = "{\"list\":"+response.toString()+"}";
				// MovieJson mMovieJson= gson.fromJson(result, MovieJson.class);
				// if (mMovieJson.getList() != null &&
				// mMovieJson.getList().size() > 0) {
				// list.addAll(mMovieJson.getList());
				// }
				// mQianBaiLuMMovieListAdapter.notifyDataSetChanged();
			}
		}, QianBaiLuMPictureListFragment.this);
		requestQueue.add(jsonObjectRequest);
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
			if (pageNo <= 1) {
				pageNo = 1;
			}
			pageNo = pageNo - 1;
			break;
		case R.id.text_next:
			pageNo = pageNo + 1;
			if (pageNo >= maxPageNo) {
				pageNo = maxPageNo;
			}
			break;
		case R.id.text_current:
			String pageNostr = edit_current.getText().toString();
			if (pageNostr != null) {
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
