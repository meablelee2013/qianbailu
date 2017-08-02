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

import java.util.List;

import android.os.Bundle;
import android.os.Message;
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

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.PCQianBaiLuMoveDetailFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuMMovieListAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMMovieListFragment;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.PCQianBaiLuMovieService;
import com.open.qianbailu.utils.NetWorkUtils;
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
public class PCQianBaiLuMovieListPagerFragment extends QianBaiLuMMovieListFragment implements OnClickListener {
	public String url = UrlUtils.PC_QIAN_BAI_LU_VLIST_CLASSID;
	private View footview;
	private Button text_fisrt;
	private Button text_pre;
	private EditText edit_current;
	private Button text_current;
	private Button text_next;
	private Button text_last;
	private boolean isautomatic;
	private int maxPageNo ;

	public static PCQianBaiLuMovieListPagerFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuMovieListPagerFragment fragment = new PCQianBaiLuMovieListPagerFragment();
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
		mQianBaiLuMMovieListAdapter = new QianBaiLuMMovieListAdapter(getActivity(), weakReferenceHandler, list);
		mPullRefreshListView.setAdapter(mQianBaiLuMMovieListAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public MovieJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieJson mMovieJson;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mMovieJson  = PCQianBaiLuMovieService.parseMovieJson(url, pageNo);
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuMovieService-parseMovieJson-"+pageNo);
			    openbean.setTitle(gson.toJson(mMovieJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuMovieService-parseMovieJson-"+pageNo);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mMovieJson = gson.fromJson(result, MovieJson.class);
		}
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
		text_fisrt.setOnClickListener(this);
		text_pre.setOnClickListener(this);
		text_current.setOnClickListener(this);
		text_next.setOnClickListener(this);
		text_last.setOnClickListener(this);
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
		if(result==null){
			return;
		}
		maxPageNo = result.getMaxpageno();
		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			if(isautomatic){
				if (result.getList() != null && result.getList().size() > 0) {
					list.addAll(result.getList());
				}
			}else{
				list.clear();
				list.addAll(result.getList());
				pageNo = 1;
			}
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		edit_current.setText(""+pageNo);
		mQianBaiLuMMovieListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_fisrt:
			pageNo = 1;
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
