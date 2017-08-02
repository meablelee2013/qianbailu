/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:53:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.List;

import com.google.gson.Gson;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuNavMExpandableListFragment;
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuBIndicatorService;
import com.open.qianbailu.jsoup.PCQianBaiLuMovieService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * pc图库首页
 * @author :fengguangjing
 * @createTime:2017-1-9下午2:53:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuNavBIndicatorExpandableListFragment extends QianBaiLuNavMExpandableListFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_M_VLIST_B;
    
	public static PCQianBaiLuNavBIndicatorExpandableListFragment newInstance(String url, boolean isVisibleToUser,int type) {
		PCQianBaiLuNavBIndicatorExpandableListFragment fragment = new PCQianBaiLuNavBIndicatorExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
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
			mNavMJson.setList(PCQianBaiLuBIndicatorService.parseMHome(url,type));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuBIndicatorService-parseMHome-"+type);
			    openbean.setTitle(gson.toJson(mNavMJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuBIndicatorService-parseMHome-"+type);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mNavMJson = gson.fromJson(result, NavMJson.class);
		}
		return mNavMJson;
	}
 
}