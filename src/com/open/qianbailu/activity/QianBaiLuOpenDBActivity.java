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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.adapter.db.OpenDBListAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;
import com.open.qianbailu.json.db.OpenDBJson;
import com.open.qianbailu.utils.ExcelUtils;
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
public class QianBaiLuOpenDBActivity extends CommonFragmentActivity<OpenDBJson> implements OnClickListener, OnItemClickListener {
	private PullToRefreshListView mPullRefreshListView;
	private OpenDBListAdapter mOpenDBListAdapter;
	private List<OpenDBBean> list = new ArrayList<OpenDBBean>();

	// private Button selectBtn;
	// private Button insertBtn;
	// private Button updateBtn;
	// private Button deleteBtn;
	// private TextView contentTv;
	// private QianBaiLuDBHelper mQianBaiLuDBHelper;

	Button btn_txt;
	Button btn_excel;
	
	private String[] title = { "序号", "标题", "类型名称", "时间", "类型", "地址" , "图片地址", "下载地址"  };
	ArrayList<ArrayList<String>>  excellist = new ArrayList<ArrayList<String>>();
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
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		// selectBtn = (Button) findViewById(R.id.btn_selecet);
		// insertBtn = (Button) findViewById(R.id.btn_insert);
		// updateBtn = (Button) findViewById(R.id.btn_update);
		// deleteBtn = (Button) findViewById(R.id.btn_delete);
		// contentTv = (TextView) findViewById(R.id.txt_content);

		btn_txt = (Button) findViewById(R.id.btn_txt);
		btn_excel = (Button) findViewById(R.id.btn_excel);
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
		btn_txt.setOnClickListener(this);
		btn_excel.setOnClickListener(this);
		mPullRefreshListView.setOnItemClickListener(this);
		// selectBtn.setOnClickListener(this);
		// insertBtn.setOnClickListener(this);
		// updateBtn.setOnClickListener(this);
		// deleteBtn.setOnClickListener(this);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(QianBaiLuOpenDBActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});

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
		// mQianBaiLuDBHelper =
		// QianBaiLuDBHelper.getInstance(getApplicationContext());
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		mOpenDBListAdapter = new OpenDBListAdapter(this, weakReferenceHandler, list);
		mPullRefreshListView.setAdapter(mOpenDBListAdapter);

		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.btn_selecet:
		// List<Map> list =
		// mQianBaiLuDBHelper.queryListMap("select * from user", null);
		// contentTv.setText(String.valueOf(list));
		// break;
		// case R.id.btn_insert:
		// mQianBaiLuDBHelper.insert("user", new String[] { "name", "gender",
		// "age" }, new Object[] { "qiangyu", "male", 23 });
		// break;
		// case R.id.btn_update:
		// mQianBaiLuDBHelper.update("user", new String[] { "name", "gender",
		// "age" }, new Object[] { "yangqiangyu", "male", 24 }, new String[] {
		// "name" }, new String[] { "qiangyu" });
		// break;
		// case R.id.btn_delete:
		// mQianBaiLuDBHelper.delete("user", new String[] { "name" }, new
		// String[] { "qiangyu" });
		// break;
		case R.id.btn_excel:
			for (int i=0;i<list.size();i++) {
				ArrayList<String> beanList=new ArrayList<String>();
				OpenDBBean bean = list.get(i);
				beanList.add(""+i);
				beanList.add(""+bean.getTitle());
				beanList.add(""+bean.getTypename());
				beanList.add(""+bean.getTime());
				beanList.add(""+bean.getType());
				beanList.add(""+bean.getUrl());
				beanList.add(""+bean.getImgsrc());
				beanList.add(""+bean.getDownloadurl());
				excellist.add(beanList);
			}
			File filee = new File(getSDPath() +"/"+getPackageName()+ "/excel");
			makeDir(filee);
			ExcelUtils.initExcel(filee.toString() + "/收藏.xls", title);
			ExcelUtils.writeObjListToExcel(excellist, getSDPath() +"/"+getPackageName()+  "/excel/收藏.xls", this);
			break;
		case R.id.btn_txt:
			// 保存
			try {
				StringBuilder collection = new StringBuilder();
				collection.append("  序号   ").append("   标题   ").append("   类型名称   ").append("   时间   ").append("   类型   ").append("   地址   ").append("   图片地址   ").append("   下载地址   ").append("\n");
				for (int i=0;i<list.size();i++) {
					OpenDBBean bean = list.get(i);
					collection.append("   "+i+"   ").append("   "+bean.getTitle()+"   ").append("   "+bean.getTypename()+"   ").append("   "+bean.getTime()+"   ").append("   "+bean.getType()+"   ").append("   "+bean.getUrl()+"   ").append("   "+bean.getImgsrc()+"   ").append("   "+bean.getDownloadurl()+"   ").append("\n");
				}
				String sdcard = Environment.getExternalStorageDirectory().toString();
				File file = new File(sdcard + "/" + getPackageName() + "/novel/");
				if (!file.exists()) {
					file.mkdirs();
				}
				File imageFile = new File(file.getAbsolutePath(), "收藏.txt");
				imageFile.deleteOnExit();
				imageFile.createNewFile();
				FileOutputStream outStream = null;
				outStream = new FileOutputStream(imageFile);
				outStream.write(collection.toString().getBytes());
				outStream.flush();
				outStream.close();
				
				Toast.makeText(this, "导出成功", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
	
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}
		String dir = sdDir.toString();
		return dir;

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
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
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
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if ((id) != -1 && list != null && list.size() > 0) {
			OpenDBBean bean = list.get((int) id);
			switch (bean.getType()) {
			case 1:
				QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
				break;
			case 2:
				QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(QianBaiLuOpenDBActivity.this, bean.getUrl());
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
