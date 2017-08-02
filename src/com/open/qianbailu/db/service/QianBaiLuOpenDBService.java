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

import android.content.ContentValues;
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
	
	public static List<OpenDBBean> queryList(Context mContext,boolean isseqDesc){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			String sql = "";
			if(isseqDesc){
				sql = mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_ORDER_ID)[0];
			}else{
				sql = mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL)[0];
			}
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(sql
					,
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
				openbean.setDownloadurl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6]));
				list.add(openbean);
				
				Log.i(TAG, "queryList  i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<OpenDBBean> queryListType(Context mContext,String url,String typename){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(
					mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE_URL_TYPENAME)[0],
					new String[]{url,typename});
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
				openbean.setDownloadurl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6]));
				list.add(openbean);
				
				Log.i(TAG, "queryList url =="+url+" ;typename=="+typename+";i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static List<OpenDBBean> queryListDescDate(Context mContext){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(
					mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_DESC)[0],
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
				openbean.setDownloadurl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6]));
				list.add(openbean);
				
				Log.i(TAG, "queryList  i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<OpenDBBean> queryListType(Context mContext,int type,boolean isseqDesc){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			String sql = "";
			if(isseqDesc){
				sql = mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE_TYPE_BY_ID)[0];
			}else{
				sql = mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE_TYPE)[0];
			}
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(sql
					,
					new String[]{type+""});
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
				openbean.setDownloadurl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6]));
				list.add(openbean);
				
				Log.i(TAG, "queryList type =="+type+" ;i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<OpenDBBean> queryListTypeByDateDesc(Context mContext,int type){
		List<OpenDBBean> list = new ArrayList<OpenDBBean>();
		try {
			List<Map> maps	= QianBaiLuDBHelper.getInstance(mContext).queryListMap(
					mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE_TYPE_BY_DATE)[0],
					new String[]{type+""});
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
				openbean.setDownloadurl((String)maps.get(i).get(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6]));
				list.add(openbean);
				
				Log.i(TAG, "queryList type =="+type+" ;i==" +i+maps.get(i).toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void insert(Context mContext,OpenDBBean openbean){
		openbean.setTime(DateFormat.format("yyyy年MM月dd日 kk:mm",new Date())+"");
		if(openbean.getTypename()==null || openbean.getTypename().length()==0){
			openbean.setTypename(openbean.getType()+"");
		}
		Object[] ojects = new Object[7];
		ojects[0] = openbean.getType();
		ojects[1] = openbean.getImgsrc();
		ojects[2] = openbean.getTitle();
		ojects[3] = openbean.getTime();
		ojects[4] = openbean.getTypename();
		ojects[5] = openbean.getUrl();
		ojects[6] = openbean.getDownloadurl();
		
		Map map = QianBaiLuDBHelper.getInstance(mContext).queryItemMap(mContext.getResources().getStringArray(R.array.QUERY_ALL_TABLE_SQL_WHERE_URL_TYPENAME)[0], 
				new String[]{openbean.getUrl(),openbean.getTypename()});
		if(map!=null && !map.isEmpty()){
//			Toast.makeText(mContext, "已经下载过", Toast.LENGTH_SHORT).show();
			Log.d("更新", "======");
			updateByUrlTypeName(mContext,openbean.getUrl(),openbean.getTypename(),openbean.getTitle());
			return;
		}
		
		QianBaiLuDBHelper.getInstance(mContext).insert(mContext.getResources().getStringArray(R.array.CREATE_TABLE_NAME)[0],
				mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD), 
				ojects);
//		Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
		Log.d("收藏成功", "======");
		Log.i(TAG, "insert=="+openbean.getTime()+openbean.getUrl()+openbean.getType()+openbean.getDownloadurl());
	}
	
	public static void updateByUrlTypeName(Context mContext,String url ,String typename,String title){
		QianBaiLuDBHelper.getInstance(mContext).update(mContext.getResources().getStringArray(R.array.CREATE_TABLE_NAME)[0], 
				new String[]{mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[2]},
				new String[]{title},
				new String[]{mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[5],mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[4]},
				new String[]{url,typename});
//		Toast.makeText(mContext, "更新收藏成功", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "update =url="+url+";typename=="+typename+";title==");
	}
	
	public static void delete(Context mContext,OpenDBBean openbean){
		QianBaiLuDBHelper.getInstance(mContext).delete(mContext.getResources().getStringArray(R.array.CREATE_TABLE_NAME)[0], 
				new String[]{mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[5]},
				new String[]{openbean.getUrl()});
		Toast.makeText(mContext, "取消收藏成功", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "delete=="+openbean.getUrl());
	}

	public static void insertBatch(Context mContext,List<OpenDBBean> list){
		List<ContentValues> clist = new ArrayList<ContentValues>();
		ContentValues mContentValues;
		for(OpenDBBean bean:list){
			mContentValues = new ContentValues();
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[0], bean.getType());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[1], bean.getImgsrc());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[2], bean.getTitle());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[3], bean.getTime());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[4], bean.getTypename());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[5], bean.getUrl());
			mContentValues.put(mContext.getResources().getStringArray(R.array.CREATE_TABLE_FIELD)[6], bean.getDownloadurl());
			clist.add(mContentValues);
		}
		QianBaiLuDBHelper.getInstance(mContext).insertBatch(mContext.getResources().getStringArray(R.array.CREATE_TABLE_NAME)[0],
				clist);
		Toast.makeText(mContext, "导入成功", Toast.LENGTH_SHORT).show();
	}
}
