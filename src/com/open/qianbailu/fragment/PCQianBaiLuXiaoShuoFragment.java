/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:55:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.PCQianBaiLuXiaoShuoFragmentActivity;
import com.open.qianbailu.fragment.m.QianBaiLuMShowFootListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMXiaoShuoFragment;
import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.PCQianBaiLuXiaoShuoService;
import com.open.qianbailu.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-9下午3:55:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PCQianBaiLuXiaoShuoFragment extends QianBaiLuMXiaoShuoFragment{
	public String url = UrlUtils.PC_QIAN_BAI_LU_XIAO_SHUO;
	private int pagerno =1;
	
	public static PCQianBaiLuXiaoShuoFragment newInstance(String url, boolean isVisibleToUser) {
		PCQianBaiLuXiaoShuoFragment fragment = new PCQianBaiLuXiaoShuoFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		Fragment fragment = QianBaiLuMShowFootListFragment.newInstance(url, true,4);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_foot, fragment).commit();
		mPullToRefreshScrollView.setMode(Mode.BOTH);
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
		mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
					pagerno =1;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}else if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_END) {
					pagerno ++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		 
		showPreNext();
		
	}

	public void showPreNext(){
		text_pretitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(text_pretitle.getTag().toString().length()>0){
					if (!text_pretitle.getTag().toString().contains("很抱歉已经没有了") && !text_pretitle.getTag().toString().equals(url)) {
						PCQianBaiLuXiaoShuoFragmentActivity.startPCQianBaiLuXiaoShuoFragmentActivity(getActivity(), text_pretitle.getTag().toString());
					}
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
	public XiaoShuoJson call() throws Exception {
		// TODO Auto-generated method stub
		XiaoShuoJson mXiaoShuoJson = PCQianBaiLuXiaoShuoService.parseXiaoSHuo(url,pagerno);
		return mXiaoShuoJson;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(XiaoShuoJson result) {
		// TODO Auto-generated method stub
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshScrollView.onRefreshComplete();
		text_pretitle.setText(result.getPreTitle());
		text_pretitle.setTag(result.getPreHref());
		text_nexttitle.setText(result.getNextTitle());
		text_nexttitle.setTag(result.getNextHref());

		text_newstitle.setText(result.getNewsTitle() + result.getNeswTime());
		
		if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
			text_detailText.setText("");
			text_detailText.setText(result.getDetailText());
		}else if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_END) {
			if(result.getDetailText()!=null && result.getDetailText().length()>0){
				text_detailText.append("\n");
				text_detailText.append(result.getDetailText());
			}
		}
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 2000);
	}
 
 
}
