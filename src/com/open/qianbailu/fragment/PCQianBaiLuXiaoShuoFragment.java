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

import java.util.List;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ParagraphStyle;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.PCQianBaiLuXiaoShuoFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.m.QianBaiLuMShowFootListFragment;
import com.open.qianbailu.fragment.m.QianBaiLuMXiaoShuoFragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.PCQianBaiLuNavService;
import com.open.qianbailu.jsoup.PCQianBaiLuXiaoShuoService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.widget.LinkClickableSpan;
import com.open.qianbailu.widget.SpanTagHandler;
import com.open.qianbailu.widget.SpanURLImageGetter;

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
//	public String url = UrlUtils.PC_QIAN_BAI_LU_XIAO_SHUO;
	private int pagerno =1;
	
	public static PCQianBaiLuXiaoShuoFragment newInstance(String url,int type, boolean isVisibleToUser) {
		PCQianBaiLuXiaoShuoFragment fragment = new PCQianBaiLuXiaoShuoFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
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
		initTag();
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
		tagEvent();
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
		
		text_nexttitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(text_nexttitle.getTag().toString().length()>0){
					if (!text_nexttitle.getTag().toString().contains("很抱歉已经没有了") && !text_nexttitle.getTag().toString().equals(url)) {
						PCQianBaiLuXiaoShuoFragmentActivity.startPCQianBaiLuXiaoShuoFragmentActivity(getActivity(), text_nexttitle.getTag().toString());
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
		XiaoShuoJson mXiaoShuoJson;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			 mXiaoShuoJson = PCQianBaiLuXiaoShuoService.parseXiaoSHuo(url,pagerno);
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("PCQianBaiLuXiaoShuoService-parseXiaoSHuo-"+pagerno);
			    openbean.setTitle(gson.toJson(mXiaoShuoJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"PCQianBaiLuXiaoShuoService-parseXiaoSHuo-"+pagerno);
			String result = beanlist.get(0).getTitle();
			Gson gson = new Gson();
			mXiaoShuoJson = gson.fromJson(result, XiaoShuoJson.class);
		}
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
		if(result==null){
			return;
		}
		
		if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
			text_detailText.setText("");
//			text_detailText.setText(Html.fromHtml(result.getDetailText()));
			
			SpanURLImageGetter imgGetter = new SpanURLImageGetter(getContext(), text_detailText);// 实例化URLImageGetter类
			Spanned spanned =Html.fromHtml(result.getDetailText(),imgGetter,new SpanTagHandler(getContext()));
			text_detailText.setText(spanned);
			text_detailText.setMovementMethod(LinkMovementMethod.getInstance());  
			URLSpan[] obj = spanned.getSpans(0, spanned.length(), URLSpan.class);
			for (int i = 0; i < obj.length; i++) {
				int start = spanned.getSpanStart(obj[i]);
	            int end = spanned.getSpanEnd(obj[i]);
				((Spannable) spanned).removeSpan(obj[i]);
				LinkClickableSpan clickableSpan = new LinkClickableSpan(getContext(),obj[i].getURL());   
//                style.setSpan(clickableSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
				((Spannable) spanned).setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			text_detailText.setText(spanned);
//			    CharSequence text = text_detailText.getText();   
//		        if(text instanceof Spannable){   
//		            int end = text.length();   
//		            Spannable sp = (Spannable)text_detailText.getText();   
//		            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);    
//		            SpannableStringBuilder style=new SpannableStringBuilder(text);   
////		            style.clearSpans();//should clear old spans   
//		            for(URLSpan url : urls){   
//		            	LinkClickableSpan clickableSpan = new LinkClickableSpan(getContext(),url.getURL());   
//		                style.setSpan(clickableSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
//		            }   
//		            text_detailText.setText(style);   
//		        }
			
			text_pretitle.setText(result.getPreTitle());
			text_pretitle.setTag(result.getPreHref());
			text_nexttitle.setText(result.getNextTitle());
			text_nexttitle.setTag(result.getNextHref());

			text_newstitle.setText(result.getNewsTitle() + result.getNeswTime());
		}else if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_END) {
			if(result.getDetailText()!=null && result.getDetailText().length()>0){
				text_detailText.append(Html.fromHtml("<br/><font color='#FF0000'>第"+pagerno+"页</font><br/>"));
//				text_detailText.append(Html.fromHtml(result.getDetailText()));
				SpanURLImageGetter imgGetter = new SpanURLImageGetter(getContext(), text_detailText);// 实例化URLImageGetter类
				Spanned spanned = Html.fromHtml(result.getDetailText(),imgGetter,new SpanTagHandler(getContext()));
				text_detailText.append(spanned);
				text_detailText.setMovementMethod(LinkMovementMethod.getInstance());  
//				    CharSequence text = text_detailText.getText();   
//			        if(text instanceof Spannable){   
//			            int end = text.length();   
//			            Spannable sp = (Spannable)text_detailText.getText();   
//			            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);    
//			            SpannableStringBuilder style=new SpannableStringBuilder(text);   
////			            style.clearSpans();//should clear old spans   
//			            for(URLSpan url : urls){   
//			            	LinkClickableSpan clickableSpan = new LinkClickableSpan(getContext(),url.getURL());   
//			                style.setSpan(clickableSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
//			            }   
//			            text_detailText.setText(style);   
//			        }
				URLSpan[] obj = spanned.getSpans(0, spanned.length(), URLSpan.class);
				for (int i = 0; i < obj.length; i++) {
					int start = spanned.getSpanStart(obj[i]);
		            int end = spanned.getSpanEnd(obj[i]);
					((Spannable) spanned).removeSpan(obj[i]);
					LinkClickableSpan clickableSpan = new LinkClickableSpan(getContext(),obj[i].getURL());   
//	                style.setSpan(clickableSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
					((Spannable) spanned).setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				text_detailText.setText(spanned);
			}
		}
		if(pagerno==1){
			weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 2000);
		}
//		float zoomScale = 1f;// 缩放比例 
//		new ZoomTextView(text_detailText, zoomScale); 
	}
 
 
}
