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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuNavMPicGridViewAdapter;
import com.open.qianbailu.bean.m.PicKuFilmBean;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMNavService;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.view.ExpendGridView;

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
public class QianBaiLuMDianYingFootListFragment extends BaseV4Fragment<NavMJson, QianBaiLuMDianYingFootListFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_MOVIE;
	public ExpendGridView gridView;
	public QianBaiLuNavMPicGridViewAdapter mQianBaiLuNavMPicGridViewAdapter;
	public List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
	private TextView txt_moduleTitle;

	public static QianBaiLuMDianYingFootListFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMDianYingFootListFragment fragment = new QianBaiLuMDianYingFootListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_dianying_foot_view, container, false);
		gridView = (ExpendGridView) view.findViewById(R.id.gridView);
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
		mQianBaiLuNavMPicGridViewAdapter = new QianBaiLuNavMPicGridViewAdapter(getActivity(), picKuL);
		gridView.setAdapter(mQianBaiLuNavMPicGridViewAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				QianBaiLuMMoveDetailFragmentActivity.startQianBaiLuMMoveDetailFragmentActivity(getActivity(), picKuL.get((int)id).getHref());
//			}
//		});
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
		mNavMJson.setList(QianBaiLuMNavService.parseMDianyingFoot(url));
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
		picKuL.clear();
		if(result.getList()!=null && result.getList().size()>0){
			picKuL.addAll(result.getList().get(0).getPicKuL());
			mQianBaiLuNavMPicGridViewAdapter.notifyDataSetChanged();
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
