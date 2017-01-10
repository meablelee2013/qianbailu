/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:05:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.CommonFragmentActivity;
import com.open.qianbailu.adapter.m.QianBaiLuShowPagerAdapter;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMMovieDetailService;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:05:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuShowPagerAdapterActivity extends CommonFragmentActivity<ShowJson> {
	private ViewPager viewpager;
	public QianBaiLuShowPagerAdapter mQianBaiLuShowPagerAdapter;
	private List<ShowBean> list = new ArrayList<ShowBean>();
	private String url = UrlUtils.QIAN_BAI_LU_MOVIE;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_m_show_viewpager);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		// 初始化viewpager.
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		mQianBaiLuShowPagerAdapter = new QianBaiLuShowPagerAdapter(this, list,weakReferenceHandler);
		viewpager.setAdapter(mQianBaiLuShowPagerAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		ShowJson mShowJson = (ShowJson) getIntent().getSerializableExtra("SHOW_JSON");
		if(mShowJson!=null && mShowJson.getList()!=null&& mShowJson.getList().size()>0){
			position = mShowJson.getCurrentPosition();
			list.clear();
			list.addAll(mShowJson.getList());
			mQianBaiLuShowPagerAdapter.notifyDataSetChanged();
			if(position>=0 && position<list.size()){
				weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 10);
			}
			
		}else{
			doAsync(this, this, this);
		}
	}

	@Override
	protected void bindEvent() {
		super.bindEvent();
		// 初始化.
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public ShowJson call() throws Exception {
		// TODO Auto-generated method stub
		ShowJson mShowJson = new ShowJson();
		mShowJson.setList(QianBaiLuMMovieDetailService.parseShow(url));
		return mShowJson;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#handlerMessage(android.os
	 * .Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_SCREEN_ORIENTATION:
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			} else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			}else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			}
			break;
		case MESSAGE_DEFAULT_POSITION:
			viewpager.setCurrentItem(position);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		} else{ 
		 super.onBackPressed();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(ShowJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mQianBaiLuShowPagerAdapter.notifyDataSetChanged();
	}
	public static void startQianBaiLuShowPagerAdapterActivity(Context context, ShowJson mShowJson) {
		Intent intent = new Intent();
		intent.putExtra("SHOW_JSON", mShowJson);
		intent.setClass(context, QianBaiLuShowPagerAdapterActivity.class);
		context.startActivity(intent);
	}
}
