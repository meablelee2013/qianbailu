/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:39:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.utils;

import com.open.qianbailu.activity.PCQianBaiLuDownLoadWebViewActivity;
import com.open.qianbailu.activity.QianBaiLuWebViewActivity;
import com.open.qianbailu.db.service.QianBaiLuOpenDBService;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午4:39:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class DownLoadUtils {
	
	public static void downLoad(Context context,String url){
		try {
			//http://m.4400av.com/play.php?id=2718 云点播
			if(url.contains("ed2k://|file") ){
				//电驴
				//打开百度云 com.baidu.netdisk
				//com.baidu.netdisk.ui.UrlLinkActivity
				Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.baidu.netdisk");
				context.startActivity(intent);
//				Intent intent = new Intent();
//				intent.setClassName("com.baidu.netdisk", "com.baidu.netdisk.ui.MainActivity");
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
			} else  {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
				intent.addCategory("android.intent.category.DEFAULT");
				context.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			PCQianBaiLuDownLoadWebViewActivity.startPCQianBaiLuDownLoadWebViewActivity(context, null);
		} 
		
	}

}
