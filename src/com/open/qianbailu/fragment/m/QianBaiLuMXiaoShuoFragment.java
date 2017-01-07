/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:05:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment.m;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMXiaoShuoService;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 小说
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:05:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMXiaoShuoFragment extends BaseV4Fragment<XiaoShuoJson, QianBaiLuMXiaoShuoFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_M_VLIST_B_CLASSID;
	public PullToRefreshScrollView mPullToRefreshScrollView;
	private TextView text_pretitle, text_nexttitle;
	private TextView text_newstitle,text_detailText;

	public static QianBaiLuMXiaoShuoFragment newInstance(String url, boolean isVisibleToUser) {
		QianBaiLuMXiaoShuoFragment fragment = new QianBaiLuMXiaoShuoFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_xiaoshuo, container, false);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scroll);
		text_newstitle = (TextView) view.findViewById(R.id.text_newstitle);
		text_pretitle = (TextView) view.findViewById(R.id.text_pretitle);
		text_nexttitle = (TextView) view.findViewById(R.id.text_nexttitle);
		text_detailText = (TextView) view.findViewById(R.id.text_detailText);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		 
		Fragment fragment = QianBaiLuMShowFootListFragment.newInstance(url, true,1);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_foot, fragment).commit();
		mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		 

		text_pretitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!text_pretitle.getTag().toString().contains("很抱歉已经没有了") && !text_pretitle.getTag().toString().equals(url)) {
					QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(getActivity(), text_pretitle.getTag().toString());
				}
			}
		});
		text_nexttitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!text_nexttitle.getTag().toString().contains("很抱歉已经没有了") && !text_nexttitle.getTag().toString().equals(url)) {
					QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(getActivity(), text_nexttitle.getTag().toString());
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
		XiaoShuoJson mXiaoShuoJson = QianBaiLuMXiaoShuoService.parseXiaoSHuo(url);
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
		super.onCallback(result);
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshScrollView.onRefreshComplete();

		text_pretitle.setText(result.getPreTitle());
		text_pretitle.setTag(result.getPreHref());
		text_nexttitle.setText(result.getNextTitle());
		text_nexttitle.setTag(result.getNextHref());

		text_newstitle.setText(result.getNewsTitle() + result.getNeswTime());
		
		text_detailText.setText(result.getDetailText());
		
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 200);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		case MESSAGE_DEFAULT_POSITION:
			mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
			break;
		default:
			break;
		}
	}
}
