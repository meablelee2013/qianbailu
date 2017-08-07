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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.AllClassAdapter;
import com.open.qianbailu.application.QianBaiLuApplication;
import com.open.qianbailu.bean.AllBean;
import com.open.qianbailu.bean.PatchBean;
import com.taobao.sophix.SophixManager;

/**
 ***************************************************************************************************************************************************************************** 
 * 所有类列表
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:26:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuALLActivity extends CommonFragmentActivity implements OnItemClickListener {
	private ListView listview;
	private AllClassAdapter mAllClassAdapter;
	private List<AllBean> list = new ArrayList<AllBean>();
	private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_all);
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
		try {
			// 这样就能获取ActivityInfo了，之后可以获得Activity的name
			ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
			list.clear();
			AllBean allBean;
			for (ActivityInfo info : activities) {
				if (!QianBaiLuALLActivity.class.getName().equals(info.name)) {
					allBean = new AllBean(info.name, getResources().getString(info.descriptionRes));
					list.add(allBean);
				}
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mAllClassAdapter = new AllClassAdapter(this, list);
		listview.setAdapter(mAllClassAdapter);
		SophixManager.getInstance().queryAndLoadNewPatch();
		QianBaiLuApplication.msgDisplayListener = new QianBaiLuApplication.MsgDisplayListener() {
            @Override
            public void handle(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConsole(msg);
                    }
                });
            }
        };
	}
	/**
     * 更新监控台的输出信息
     *
     * @param content 更新内容
     */
    private void updateConsole(String content) {
        try {
        	Gson gson = new Gson();
            PatchBean bean = gson.fromJson(content, PatchBean.class);
            if(bean!=null){
            	if(bean.getHandlePatchVersion()>0){
            		alertNewPatch(content);
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    private void alertNewPatch(String content){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);  
        builder.setItems(new String[]{content+"检测到热更新包，重启生效"}, new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	android.os.Process.killProcess(android.os.Process.myPid());
            }  
        });  
        builder.show();  
    }
	/**
     * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
     */

//    private void requestExternalStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_EXTERNAL_STORAGE_PERMISSION:
//                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    updateConsole("local external storage patch is invalid as not read external storage permission");
//                }
//                break;
//            default:
//        }
//    }
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		listview.setOnItemClickListener(this);
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
		Log.i(TAG, "listView item" + view.getId() + ";postion=" + (int) id + " ========onItemClick ");
		AllBean bean = list.get((int) id);
		if (bean != null && !QianBaiLuALLActivity.class.getName().equals(bean.getClassName())) {
			Intent intent = new Intent();
			intent.setClassName(getPackageName(), bean.getClassName());
			startActivity(intent);
		}
	}

}
