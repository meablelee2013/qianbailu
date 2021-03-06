/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:28:18
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.m.QianBaiLuMListAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.view.ExpendListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:28:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMShowFootListFragment extends BaseV4Fragment<NavMJson, QianBaiLuMShowFootListFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_SHOW;
	public ExpendListView listview;
	public QianBaiLuMListAdapter mQianBaiLuMListAdapter;
	public List<NavMChildBean> list = new ArrayList<NavMChildBean>();
	private TextView txt_moduleTitle;
	private int type;

	public static QianBaiLuMShowFootListFragment newInstance(String url, boolean isVisibleToUser,int type) {
		QianBaiLuMShowFootListFragment fragment = new QianBaiLuMShowFootListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_show_image_foot_view, container, false);
		listview = (ExpendListView) view.findViewById(R.id.listview);
		txt_moduleTitle = (TextView) view.findViewById(R.id.txt_moduleTitle);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mQianBaiLuMListAdapter = new QianBaiLuMListAdapter(getActivity(), list);
		listview.setAdapter(mQianBaiLuMListAdapter);
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
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mNavMJson.setList(QianBaiLuMNavService.parseMShowFoot(url,type));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("QianBaiLuMNavService-parseMShowFoot-"+type);
			    openbean.setTitle(gson.toJson(mNavMJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"QianBaiLuMNavService-parseMShowFoot-"+type);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mNavMJson = gson.fromJson(result, NavMJson.class);
		}
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
		if(result==null){
			return;
		}
		list.clear();
		if(result.getList()!=null && result.getList().size()>0){
			list.addAll(result.getList().get(0).getList());
			mQianBaiLuMListAdapter.notifyDataSetChanged();
			
			txt_moduleTitle.setText(result.getList().get(0).getTitle());
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
