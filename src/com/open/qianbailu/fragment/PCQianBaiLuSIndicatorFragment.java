/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:40:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.List;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMIndicatorFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.PCQianBaiLuIndicatorService;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:40:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuSIndicatorFragment  extends QianBaiLuMIndicatorFragment {
	public String url = UrlUtils.PC_QIAN_BAI_LU_VLIST_S;
	
	public static PCQianBaiLuSIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuSIndicatorFragment fragment = new PCQianBaiLuSIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public NavMJson call() throws Exception {
		// TODO Auto-generated method stub
		NavMJson mNavMJson = new NavMJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mNavMJson.setList(PCQianBaiLuIndicatorService.parseMNav(url,"小说"));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuIndicatorService-parseMNav-小说");
			    openbean.setTitle(gson.toJson(mNavMJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuIndicatorService-parseMNav-小说");
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mNavMJson = gson.fromJson(result, NavMJson.class);
		}
		return mNavMJson;
	}
	@Override
	public void onCallback(NavMJson result) {
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		
		Fragment fragment;
		for (NavMBean bean : result.getList()) {
			titleList.add(bean.getTitle());
			if(bean.getTitle().equals("小说首页")){
				fragment = PCQianBaiLuNavSIndicatorExpandableListFragment.newInstance(url, true,4);
			}else{
				fragment = PCQianBaiLuSListFragment.newInstance(bean.getHref(), false);
//				fragment = CommonV4Fragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	
}