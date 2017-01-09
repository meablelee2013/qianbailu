/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:47:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import co.lujun.androidtagview.TagView.OnTagClickListener;

import com.open.qianbailu.activity.m.QianBaiLuMSearchActivity;
import com.open.qianbailu.json.m.SearchJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMSearchService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午4:47:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuSearchActivity extends QianBaiLuMSearchActivity{
	public String url = UrlUtils.PC_QIAN_BAI_LU_SEARCH;
	
	@Override
	public SearchJson call() throws Exception {
		// TODO Auto-generated method stub
		SearchJson mSearchJson = new SearchJson();
//		mSearchJson.setHotlist(QianBaiLuMSearchService.parseSearchHot(url));
		return mSearchJson;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//https://1100.so/wap.php?q=%E4%B8%9C%E4%BA%AC%E7%83%AD&type=3
				String search = edit_search.getText().toString();
				String url2 = "";
				try {
					url2 = UrlUtils.PC_QIAN_BAI_LU_SEARCH+"?q="+URLEncoder.encode(search, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PCQianBaiLuSearchResultActivity.startPCQianBaiLuSearchResultActivity(PCQianBaiLuSearchActivity.this, url2);
			}
		});
		tagcontainerLayout.setOnTagClickListener(new OnTagClickListener() {
			@Override
			public void onTagLongClick(int position, String text) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTagCrossClick(int position) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTagClick(int position, String text) {
				PCQianBaiLuSearchResultActivity.startPCQianBaiLuSearchResultActivity(PCQianBaiLuSearchActivity.this, hotlist.get(position).getHref().replace("&type=3", "").replace("&type=1", "").replace("&type=2", ""));
			}
		});
	}
	
	public static void startPCQianBaiLuSearchActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PCQianBaiLuSearchActivity.class);
		context.startActivity(intent);
	}
}
