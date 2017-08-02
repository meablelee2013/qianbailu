/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:11:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.open.qianbailu.activity.PCQianBaiLuShowListFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMShowListFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.jsoup.PCQianBaiLuShowImageService;
import com.open.qianbailu.utils.NetWorkUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:11:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PCQianBaiLuShowListFragment extends QianBaiLuMShowListFragment {
//	public String url = UrlUtils.PC_QIAN_BAI_LU_SHOW;

	public static PCQianBaiLuShowListFragment newInstance(String url,int type, boolean isVisibleToUser) {
		PCQianBaiLuShowListFragment fragment = new PCQianBaiLuShowListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public ShowJson call() throws Exception {
		// TODO Auto-generated method stub
		ShowJson mShowJson;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mShowJson = PCQianBaiLuShowImageService.parsePicture(url);
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuShowImageService-parsePicture");
			    openbean.setTitle(gson.toJson(mShowJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuShowImageService-parsePicture");
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mShowJson = gson.fromJson(result, ShowJson.class);
		}
		return mShowJson;
	}
	
	public void showPreNext(){
		text_pretitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(text_pretitle.getTag().toString().length()>0){
					if (!text_pretitle.getTag().toString().contains("很抱歉已经没有了") && !text_pretitle.getTag().toString().equals(url)) {
						PCQianBaiLuShowListFragmentActivity.startPCQianBaiLuShowListFragmentActivity(getActivity(), text_pretitle.getTag().toString());
					}
				}
				
			}
		});
		
		text_nexttitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(text_nexttitle.getTag().toString().length()>0){
					if (!text_nexttitle.getTag().toString().contains("很抱歉已经没有了") && !text_nexttitle.getTag().toString().equals(url)) {
						PCQianBaiLuShowListFragmentActivity.startPCQianBaiLuShowListFragmentActivity(getActivity(), text_nexttitle.getTag().toString());
					}
				}
				
			}
		});
		 
	}

}