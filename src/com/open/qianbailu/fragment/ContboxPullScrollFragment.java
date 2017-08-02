/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:52:57
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.fragment;

import java.util.ArrayList;
import java.util.List;

import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.LinkMovementMethodExt;
import net.nightwhistler.htmlspanner.MyImageSpan;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.format.DateUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.HtmlImgPreviewActivity;
import com.open.qianbailu.bean.ContboxBean;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.ContboxJson;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.jsoup.ContBoxScrollService;
import com.open.qianbailu.utils.NetWorkUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:52:57
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ContboxPullScrollFragment extends BaseV4Fragment<ContboxJson, ContboxPullScrollFragment> implements OnRefreshListener<ScrollView> {
	PullToRefreshScrollView mPullToRefreshScrollView;
	TextView  txt_centerp;
	HtmlSpanner htmlSpanner;
	ArrayList<String> imglist;
	String url;
	
	public static ContboxPullScrollFragment newInstance(String url, boolean isVisibleToUser) {
		ContboxPullScrollFragment fragment = new ContboxPullScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contbox_scrollview, container, false);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_scroll_list);
		txt_centerp = (TextView) view.findViewById(R.id.txt_centerp);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		imglist = new ArrayList<String>();
		htmlSpanner = new HtmlSpanner(getActivity(), dm.widthPixels, handler);
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:// 获取图片路径列表
				String url = (String) msg.obj;
				Log.e("jj", "url>>" + url);
				imglist.add(url);
				break;
			case 2:// 图片点击事件
				int position = 0;
				MyImageSpan span = (MyImageSpan) msg.obj;
				for (int i = 0; i < imglist.size(); i++) {
					if (span.getUrl().equals(imglist.get(i))) {
						position = i;
						break;
					}
				}
				Log.e("jj", "position>>" + position);
				Intent intent = new Intent(getActivity(), HtmlImgPreviewActivity.class);
				Bundle b = new Bundle();
				b.putInt("position", position);
				b.putStringArrayList("imglist", imglist);
				intent.putExtra("b", b);
				startActivity(intent);
				break;
			}
		}

	};
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshScrollView.setOnRefreshListener(this);
	}

	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#call()
	 */
	@Override
	public ContboxJson call() throws Exception {
		// TODO Auto-generated method stub
		ContboxJson mContboxJson = new ContboxJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mContboxJson.setContbox(ContBoxScrollService.parseContbox(url));
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("ContBoxScrollService-parseContbox");
			    openbean.setTitle(gson.toJson(mContboxJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"ContBoxScrollService-parseContbox");
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mContboxJson = gson.fromJson(result, ContboxJson.class);
		}
		return mContboxJson;
	}

	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(ContboxJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result==null){
			return;
		}
		final ContboxBean  contbox = result.getContbox();
		if(contbox!=null){
			new Thread(new Runnable() {
				@Override
				public void run() {
					final Spannable spannable = htmlSpanner.fromHtml(contbox.getCenterp());
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							txt_centerp.setText(spannable);
							txt_centerp.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
						}
					});
				}
			}).start();
//			txt_centerp.setText(Html.fromHtml(contbox.getCenterp(), new URLImageParser(txt_centerp), null));  
		}
		mPullToRefreshScrollView.onRefreshComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener
	 * #onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
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

}
