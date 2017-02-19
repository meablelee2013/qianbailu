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

import java.util.ArrayList;
import java.util.List;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView.OnTagClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.PCQianBaiLuNoticeWebViewActivity;
import com.open.qianbailu.adapter.m.QianBaiLuMShowAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.fragment.BaseV4Fragment;
import com.open.qianbailu.json.m.MovieDetailJson;
import com.open.qianbailu.jsoup.m.QianBaiLuMMovieDetailService;
import com.open.qianbailu.utils.DownLoadUtils;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 电影详细
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:05:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMMovieDetailFragment extends BaseV4Fragment<MovieDetailJson, QianBaiLuMMovieDetailFragment> {
	public String url = UrlUtils.QIAN_BAI_LU_MOVIE;
	public PullToRefreshListView mPullRefreshListView;
	public QianBaiLuMShowAdapter mQianBaiLuMShowAdapter;
	public List<ShowBean> list = new ArrayList<ShowBean>();
	public View footview,headview;
	private TextView text_pretitle;
	private List<NavMChildBean> listd = new ArrayList<NavMChildBean>();
	//head
	private ImageView img_movieDetaiImg;
	private TextView text_movie_name;
	private TextView text_movie_type;
	private TextView text_movie_time;
	private TagContainerLayout tagcontainerLayout;
	private TextView text_moduleTitle ;
	public int type;
	
	public static QianBaiLuMMovieDetailFragment newInstance(String url, boolean isVisibleToUser,int type) {
		QianBaiLuMMovieDetailFragment fragment = new QianBaiLuMMovieDetailFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qianbailu_m_movie_detail, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_qianbailu_m_show_image_foot, null);
		text_pretitle = (TextView) footview.findViewById(R.id.text_pretitle);
		
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_qianbailu_m_dianying_head, null);
		text_moduleTitle= (TextView) headview.findViewById(R.id.text_moduleTitle);
		text_movie_name = (TextView) headview.findViewById(R.id.text_movie_name);
		text_movie_type = (TextView) headview.findViewById(R.id.text_movie_type);
		text_movie_time = (TextView) headview.findViewById(R.id.text_movie_time);
		img_movieDetaiImg = (ImageView) headview.findViewById(R.id.img_movieDetaiImg);
		tagcontainerLayout = (TagContainerLayout) headview.findViewById(R.id.tagcontainerLayout);
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
		ListView listview = mPullRefreshListView.getRefreshableView();
		listview.addHeaderView(headview);
		listview.addFooterView(footview);
		
		Fragment fragment = QianBaiLuMDianYingFootListFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_foot, fragment).commit();

		mQianBaiLuMShowAdapter = new QianBaiLuMShowAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mQianBaiLuMShowAdapter);
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
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
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
				/**
				 * ed2k://|file|paco-010317_001.mp4|1953264949|7598C2FA4FA8943DB6112C3D88F6759C|h=2LE3FDTICS3LPUQ2NKP4RF4K67GRMWLL|/;title==电驴下载
 thunder://QUFlZDJrOi8vfGZpbGV8cGFjby0wMTAzMTdfMDAxLm1wNHwxOTUzMjY0OTQ5fDc1OThDMkZBNEZBODk0M0RCNjExMkMzRDg4RjY3NTlDfGg9MkxFM0ZEVElDUzNMUFVRMk5LUDRSRjRLNjdHUk1XTEx8L1pa;title==迅雷下载
 xfplay://dna=BdydmZD2BdIcD0H2DGx3DxDWAHfcA0AfDZudm0e2AejgBdxWmZe3mD|dx=1953264949|mz=paco-010317_001.mp4|zx=nhE0pdOVlZe0mv41Ac4XBdDUmGH0BwxWBdaVrgMSnJ5R|zx=nhE0pdOVlZe0mv41Ac4XBdDUmGH0BwxWBdaVrgMSnJ5R;title==影音先锋第1集
				 */
				try{
					ClipboardManager copy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);  
			                 copy.setText(listd.get(position).getHref()); 
			        DownLoadUtils.downLoad(getActivity(), listd.get(position).getHref());
			        OpenDBBean openbean = new OpenDBBean();
			        openbean.setUrl(url);
			        openbean.setType(type);
			        openbean.setImgsrc(img_movieDetaiImg.getContentDescription().toString());
			        openbean.setTitle(text_moduleTitle.getText().toString());
			        openbean.setTypename(text_movie_type.getText().toString());
			        openbean.setTime(text_movie_time.getText().toString());
			        openbean.setDownloadurl(listd.get(position).getHref());
			        QianBaiLuOpenDBService.insert(getActivity(), openbean);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		text_movie_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(text_movie_time.getText().toString().contains("看片帮助")){
					PCQianBaiLuNoticeWebViewActivity.startPCQianBaiLuNoticeWebViewActivity(getActivity(), null);
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
	public MovieDetailJson call() throws Exception {
		// TODO Auto-generated method stub
		MovieDetailJson mMovieDetailJson = QianBaiLuMMovieDetailService.parsePicture(url);
		return mMovieDetailJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MovieDetailJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);

		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		list.clear();
		list.addAll(result.getList());
		
		listd.clear();
		listd.addAll(result.getListd());
		tagcontainerLayout.removeAllTags();
		for(NavMChildBean cbean:result.getListd()){
			tagcontainerLayout.addTag(cbean.getTitle());
		}

		mQianBaiLuMShowAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
		
		text_pretitle.setText(result.getSec_info_intro());
		text_moduleTitle.setText(result.getMovie_name());
		text_movie_name.setText(result.getMovie_name());
		text_movie_type.setText(result.getMovie_type());
		text_movie_time.setText(result.getMovie_time());
		if (result.getMovieDetaiImg() != null && result.getMovieDetaiImg().length() > 0) {
			DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
					.cacheInMemory().cacheOnDisc().build();
			ImageLoader.getInstance().displayImage(result.getMovieDetaiImg(), img_movieDetaiImg, options, getImageLoadingListener());
			img_movieDetaiImg.setContentDescription(result.getMovieDetaiImg());
		}
		
		if(text_movie_time.getText().toString().contains("看片帮助")){
			text_movie_time.setTextColor(getActivity().getResources().getColor(android.R.color.holo_red_light));
		}
		
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
		default:
			break;
		}
	}
}
