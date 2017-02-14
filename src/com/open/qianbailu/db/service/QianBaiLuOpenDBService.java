/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午3:23:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.db.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.open.qianbailu.R;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.db.QianBaiLuDBHelper;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午3:23:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuOpenDBService {
	public static final String TAG = QianBaiLuOpenDBService.class.getSimpleName();
	
	public static List<OpenDBBean> queryList(Context mContext){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(
					mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL)[0],
					null);
			OpenDBBean openbean;
			//[{typename=类型：亚洲, title=片名：最新pacopacomama 010317_235 新年女体盛~巫女多次疯狂被中出~[大咲萌], time=时间：2017-01-05 02:59:17, type=3, id=1, url=http://m.100av.us/vshow.php?id=11174, imgsrc=http://mi3.1100lu.xyz/m/vod/2017-01-05/586d4604b21a2.jpg}]
			for(int i=0;i<maps.size();i++){
				openbean = new OpenDBBean();
				openbean.setId((Integer)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_KEY)[0]));
				openbean.setType((Integer)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[0]));
				openbean.setImgsrc((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[1]));
				openbean.setTitle((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[2]));
				openbean.setTime((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[3]));
				openbean.setTypename((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[4]));
				openbean.setUrl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[5]));
				
				list.add(openbean);
				
				Log.i(TAG, "queryList  i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void insert(Context mContext,OpenDBBean openbean){
		openbean.setTime(DateFormat.format("yyyy年MM月dd日 kk:mm",new Date())+"");
		Object[] ojects = new Object[6];
		ojects[0] = openbean.getType();
		ojects[1] = openbean.getImgsrc();
		ojects[2] = openbean.getTitle();
		ojects[3] = openbean.getTime();
		ojects[4] = openbean.getTypename();
		ojects[5] = openbean.getUrl();
		
		Map map = QianBaiLuDBHelper.getInstance(mContext).queryItemMap(mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE)[0], 
				new String[]{openbean.getUrl()});
		if(map!=null && !map.isEmpty()){
			Toast.makeText(mContext, "已经下载过", Toast.LENGTH_SHORT).show();
			return;
		}
		
		QianBaiLuDBHelper.getInstance(mContext).insert(mContext.getResources().getStringArray(R.array.CREATE_TABLE_NAME)[0],
				mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD), 
				ojects);
		Log.i(TAG, "insert=="+openbean.getTime()+openbean.getUrl()+openbean.getType());
	}
	

}
