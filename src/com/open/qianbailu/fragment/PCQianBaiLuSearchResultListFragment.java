/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午5:01:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.List;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.qianbailu.activity.PCQianBaiLuMoveDetailFragmentActivity;
import com.open.qianbailu.activity.PCQianBaiLuShowListFragmentActivity;
import com.open.qianbailu.activity.PCQianBaiLuXiaoShuoFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMSearchResultListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.json.m.SearchJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.jsoup.PCQianBaiLuSearchService;
import com.open.qianbailu.utils.NetWorkUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午5:01:37
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PCQianBaiLuSearchResultListFragment extends QianBaiLuMSearchResultListFragment {

	public static PCQianBaiLuSearchResultListFragment newInstance(String url, boolean isVisibleToUser, int type) {
		PCQianBaiLuSearchResultListFragment fragment = new PCQianBaiLuSearchResultListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
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
				switch (type) {
				case 1:
					PCQianBaiLuXiaoShuoFragmentActivity.startPCQianBaiLuXiaoShuoFragmentActivity(getActivity(), list.get((int) id).getHref());
					break;
				case 2:
					PCQianBaiLuShowListFragmentActivity.startPCQianBaiLuShowListFragmentActivity(getActivity(), list.get((int) id).getHref());
					break;
				case 3:
					PCQianBaiLuMoveDetailFragmentActivity.startPCQianBaiLuMoveDetailFragmentActivity(getActivity(), list.get((int) id).getHref());
					break;
				default:
					break;
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
	public SearchJson call() throws Exception {
		SearchJson mSearchJson = new SearchJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mSearchJson.setResultlist(PCQianBaiLuSearchService.parseSearchResult(url, pageNo));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuSearchService-parseSearchResult-"+pageNo);
			    openbean.setTitle(gson.toJson(mSearchJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuSearchService-parseSearchResult-"+pageNo);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mSearchJson = gson.fromJson(result, SearchJson.class);
		}
		return mSearchJson;
	}

}
