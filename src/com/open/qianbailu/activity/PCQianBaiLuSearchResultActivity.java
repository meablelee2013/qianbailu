/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:51:40
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.qianbailu.activity.m.QianBaiLuMSearchResultActivity;
import com.open.qianbailu.bean.SearchBean;
import com.open.qianbailu.fragment.PCQianBaiLuSearchResultListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMSearchResultListFragment;
import com.open.qianbailu.json.m.SearchJson;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:51:40
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuSearchResultActivity extends QianBaiLuMSearchResultActivity{
	  
	 /* (non-Javadoc)
	 * @see com.open.qianbailu.activity.m.QianBaiLuMSearchResultActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		url = UrlUtils.PC_QIAN_BAI_LU_SEARCH_RESULT;
	}
	
	@Override
	public void onCallback(SearchJson result) {
		// TODO Auto-generated method stub
		titleList.clear();
		/**
		 * <select name="type" style="height:32px;margin-left:10px">
				  <option value ="3">电影</option>
				  <option value ="2">图片</option>
				  <option value="1" selected="selected">小说</option>
				</select>
		 */

		Fragment fragment;
		for (SearchBean bean : result.getTypelist()) {
			titleList.add(bean.getTitle());
			String url2 = url+"&type="+bean.getType();
			if(bean.getType()==1){
				fragment = PCQianBaiLuSearchResultListFragment.newInstance(url2, true,bean.getType());
			}else{
				fragment = PCQianBaiLuSearchResultListFragment.newInstance(url2, false,bean.getType());
			}
			listRankFragment.add(fragment);
		}
		 
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}

	
	public static void startPCQianBaiLuSearchResultActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuSearchResultActivity.class);
		context.startActivity(intent);
	}
}
