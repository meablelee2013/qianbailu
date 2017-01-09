/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity.m;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView.OnTagClickListener;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.bean.SearchBean;
import com.open.qianbailu.json.m.SearchJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMSearchService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMSearchActivity extends CommonFragmentActivity<SearchJson> {
	public String url = UrlUtils.QIAN_BAI_LU_SEARCH;
	public EditText edit_search;
	public Button btn_search;
	public TagContainerLayout tagcontainerLayout;
	public ArrayList<SearchBean> hotlist = new ArrayList<SearchBean>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.CommonFragmentActivity#onCreate(android.os
	 * .Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_search);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		edit_search = (EditText) findViewById(R.id.edit_search);
		btn_search = (Button) findViewById(R.id.btn_search);
		tagcontainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		doAsync(this, this, this);
	}

	@Override
	public SearchJson call() throws Exception {
		// TODO Auto-generated method stub
		SearchJson mSearchJson = new SearchJson();
		mSearchJson.setHotlist(QianBaiLuMSearchService.parseSearchHot(url));
		return mSearchJson;
	}

	@Override
	public void onCallback(SearchJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		List<String> hList = new ArrayList<String>();
		for (SearchBean bean : result.getHotlist()) {
			hList.add(bean.getTitle());
		}
		tagcontainerLayout.setTags(hList);
		hotlist.clear();
		hotlist.addAll(result.getHotlist());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//https://1100.so/wap.php?q=%E4%B8%9C%E4%BA%AC%E7%83%AD&type=3
				String search = edit_search.getText().toString();
				String url2 = "";
				try {
					url2 = UrlUtils.QIAN_BAI_LU_SEARCH_WAB+"?q="+URLEncoder.encode(search, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				QianBaiLuMSearchResultActivity.startQianBaiLuMSearchResultActivity(QianBaiLuMSearchActivity.this, url2);
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
				QianBaiLuMSearchResultActivity.startQianBaiLuMSearchResultActivity(QianBaiLuMSearchActivity.this, hotlist.get(position).getHref().replace("&type=3", "").replace("&type=1", "").replace("&type=2", ""));
			}
		});
	}
 

	public static void startQianBaiLuMSearchActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, QianBaiLuMSearchActivity.class);
		context.startActivity(intent);
	}
}
