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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView.OnTagClickListener;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.NavMJson;
import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMIndicatorService;
import com.open.qianbailu.jsoup.m.QianBaiLuMXiaoShuoService;
import com.open.qianbailu.utils.NetWorkUtils;
import com.open.qianbailu.utils.UrlUtils;
import com.open.qianbailu.view.ZoomTextView;
import com.open.qianbailu.widget.LinkClickableSpan;
import com.open.qianbailu.widget.SpanURLImageGetter;

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
	public TextView text_pretitle, text_nexttitle;
	public TextView text_newstitle,text_detailText;
	public TagContainerLayout tagcontainerLayout;
	public List<String> tagList = new ArrayList<String>();
	public int type;
	
	public static QianBaiLuMXiaoShuoFragment newInstance(String url,int type, boolean isVisibleToUser) {
		QianBaiLuMXiaoShuoFragment fragment = new QianBaiLuMXiaoShuoFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
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
		tagcontainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerLayout);
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
		
		initTag();
	}
	
	public void initTag(){
		tagList.clear();
		tagList.add("字体大");
		tagList.add("字体中");
		tagList.add("字体小");
		tagList.add("收藏");
		tagList.add("保存");
		tagcontainerLayout.setTags(tagList);
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
		 
		showPreNext();
		
		tagEvent();
	}
	
	public void tagEvent(){
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
				float size = 16;
				switch (position) {
				case 0:
					size = 20;
					break;
				case 1:
					size = 16;
					break;
				case 2:
					size = 12;
					break;
				case 3:
				    OpenDBBean openbean = new OpenDBBean();
			        openbean.setUrl(url);
			        openbean.setType(type);
			        openbean.setTitle(text_newstitle.getText().toString());
			        openbean.setTypename( text_detailText.getText().toString().substring(0, 200));
			        QianBaiLuOpenDBService.insert(getActivity(), openbean);
					break;
				case 4:
					//保存
					 try {  
				            String sdcard = Environment.getExternalStorageDirectory().toString();  
				            File file = new File(sdcard + "/"+getActivity().getPackageName()+"/novel/");  
				            if (!file.exists()) {  
				                file.mkdirs();  
				            }  
				            File imageFile = new File(file.getAbsolutePath(),  text_newstitle.getText().toString()+".txt");  
				            imageFile.deleteOnExit();
				            imageFile.createNewFile();
				            FileOutputStream outStream = null;  
				            outStream = new FileOutputStream(imageFile);
				            outStream.write(text_detailText.getText().toString().getBytes());
				            outStream.flush();  
				            outStream.close();  
				        } catch (Exception e) {  
				            e.printStackTrace();  
				        }  
					break;
				default:
					break;
				}
				text_detailText.setTextSize(size);
			}
		});
	}
	
	public void showPreNext(){
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
		XiaoShuoJson mXiaoShuoJson;
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mXiaoShuoJson = QianBaiLuMXiaoShuoService.parseXiaoSHuo(url);
			try {
				//数据存储
				Gson gson = new Gson();
				OpenDBBean  openbean = new OpenDBBean();
	    	    openbean.setUrl(url);
	    	    openbean.setTypename("QianBaiLuMXiaoShuoService-parseXiaoSHuo");
			    openbean.setTitle(gson.toJson(mXiaoShuoJson));
			    QianBaiLuOpenDBService.insert(getActivity(), openbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<OpenDBBean> beanlist =  QianBaiLuOpenDBService.queryListType(getActivity(), url,"QianBaiLuMXiaoShuoService-parseXiaoSHuo");
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
		super.onCallback(result);
		if(result==null){
			return;
		}
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshScrollView.onRefreshComplete();

		text_pretitle.setText(result.getPreTitle());
		text_pretitle.setTag(result.getPreHref());
		text_nexttitle.setText(result.getNextTitle());
		text_nexttitle.setTag(result.getNextHref());

		text_newstitle.setText(result.getNewsTitle() + result.getNeswTime());
		text_detailText.setText(Html.fromHtml(result.getDetailText()));
//		SpanURLImageGetter imgGetter = new SpanURLImageGetter(getContext(), text_detailText);// 实例化URLImageGetter类
//		text_detailText.setText(Html.fromHtml(result.getDetailText(),imgGetter,null));
//		text_detailText.setMovementMethod(LinkMovementMethod.getInstance());  
//		    CharSequence text = text_detailText.getText();   
//	        if(text instanceof Spannable){   
//	            int end = text.length();   
//	            Spannable sp = (Spannable)text_detailText.getText();   
//	            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);    
//	            SpannableStringBuilder style=new SpannableStringBuilder(text);   
////	            style.clearSpans();//should clear old spans   
//	            for(URLSpan url : urls){   
//	            	LinkClickableSpan clickableSpan = new LinkClickableSpan(getContext(),url.getURL());   
//	                style.setSpan(clickableSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
//	            }   
//	            text_detailText.setText(style);   
//	        }
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 2000);
		
		float zoomScale = 1f;// 缩放比例 
		new ZoomTextView(text_detailText, zoomScale); 
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
