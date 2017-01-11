/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:26:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.adapter.db.OpenDBListAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.QianBaiLuDBHelper;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.db.OpenDBJson;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 收藏
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:26:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuOpenDBActivity extends CommonFragmentActivity<OpenDBJson> implements OnClickListener,OnItemClickListener {
	private ListView listview;
	private OpenDBListAdapter mOpenDBListAdapter;
	private List<OpenDBBean> list = new ArrayList<OpenDBBean>();

//	private Button selectBtn;
//	private Button insertBtn;
//	private Button updateBtn;
//	private Button deleteBtn;
//	private TextView contentTv;
//	private QianBaiLuDBHelper mQianBaiLuDBHelper;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_open_db);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		listview = (ListView) findViewById(R.id.listview);
//		selectBtn = (Button) findViewById(R.id.btn_selecet);
//		insertBtn = (Button) findViewById(R.id.btn_insert);
//		updateBtn = (Button) findViewById(R.id.btn_update);
//		deleteBtn = (Button) findViewById(R.id.btn_delete);
//		contentTv = (TextView) findViewById(R.id.txt_content);

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
		listview.setOnItemClickListener(this);
//		selectBtn.setOnClickListener(this);
//		insertBtn.setOnClickListener(this);
//		updateBtn.setOnClickListener(this);
//		deleteBtn.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
//		mQianBaiLuDBHelper = QianBaiLuDBHelper.getInstance(getApplicationContext());
		
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		mOpenDBListAdapter = new OpenDBListAdapter(this, weakReferenceHandler,list);
		listview.setAdapter(mOpenDBListAdapter);

		doAsync(this, this, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.btn_selecet:
//			List<Map> list = mQianBaiLuDBHelper.queryListMap("select * from user", null);
//			contentTv.setText(String.valueOf(list));
//			break;
//		case R.id.btn_insert:
//			mQianBaiLuDBHelper.insert("user", new String[] { "name", "gender", "age" }, new Object[] { "qiangyu", "male", 23 });
//			break;
//		case R.id.btn_update:
//			mQianBaiLuDBHelper.update("user", new String[] { "name", "gender", "age" }, new Object[] { "yangqiangyu", "male", 24 }, new String[] { "name" }, new String[] { "qiangyu" });
//			break;
//		case R.id.btn_delete:
//			mQianBaiLuDBHelper.delete("user", new String[] { "name" }, new String[] { "qiangyu" });
//			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#call()
	 */
	@Override
	public OpenDBJson call() throws Exception {
		// TODO Auto-generated method stub
		OpenDBJson mOpenDBJson = new OpenDBJson();
		mOpenDBJson.setList(QianBaiLuOpenDBService.queryList(getApplicationContext()));
		return mOpenDBJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.BaseFragmentActivity#onCallback(java.lang
	 * .Object)
	 */
	@Override
	public void onCallback(OpenDBJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mOpenDBListAdapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.BaseFragmentActivity#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_ADAPTER_CALL_ONITEM:
			onItemClick(null, null, msg.arg1, msg.arg1);
			break;
		default:
			break;
		}

	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if((id)!=-1 && list!=null && list.size()>0){
			OpenDBBean bean = list.get((int)id);
			switch (bean.getType()) {
			case 1:
				QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(QianBaiLuOpenDBActivity.this,bean.getUrl());
				break;
		    case 2:
		    	QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(QianBaiLuOpenDBActivity.this,bean.getUrl());
				break;
		    case 3:
		    	QianBaiLuMMoveDetailFragmentActivity.startQianBaiLuMMoveDetailFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
			break;
		    case 4:
		    	PCQianBaiLuXiaoShuoFragmentActivity.startPCQianBaiLuXiaoShuoFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
		    	break;
		    case 5:
		    	PCQianBaiLuShowListFragmentActivity.startPCQianBaiLuShowListFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
			break;
		    case 6:
				PCQianBaiLuMoveDetailFragmentActivity.startPCQianBaiLuMoveDetailFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
				break;
			default:
				QianBaiLuWebViewActivity.startUmeiWebViewActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
				break;
			}
		}
		
	}

}
