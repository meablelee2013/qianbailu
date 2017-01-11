/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:01:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.m;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonPagerAdapter;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.utils.ImageAsyncTask;
import com.open.qianbailu.view.ZoomImageView;
import com.open.qianbailu.weak.WeakActivityReferenceHandler;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午2:01:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QianBaiLuShowPagerAdapter extends CommonPagerAdapter<ShowBean>{
	WeakActivityReferenceHandler weakActivityReferenceHandler;
	public QianBaiLuShowPagerAdapter(Context mContext, List<ShowBean> list,WeakActivityReferenceHandler weakActivityReferenceHandler) {
		super(mContext, list);
		this.weakActivityReferenceHandler = weakActivityReferenceHandler;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final ShowBean bean = (ShowBean) getItem(position);
		final ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_qianbailu_m_show_viewpager, null);
		mViewHolder.imageview = (ZoomImageView) convertView.findViewById(R.id.imageview);
		mViewHolder.txt_save = (TextView) convertView.findViewById(R.id.txt_save);
		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), mViewHolder.imageview, options, null);
			}
		}

		mViewHolder.imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
				weakActivityReferenceHandler.sendEmptyMessage(7000);
			}
		});
		mViewHolder.txt_save.setOnClickListener(new View.OnClickListener() {  
	           @Override  
	           public void onClick(View v) {  
	               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  
	               builder.setItems(new String[]{mContext.getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {  
	                   @Override  
	                   public void onClick(DialogInterface dialog, int which) {  
	                	   mViewHolder.imageview.setDrawingCacheEnabled(true);  
	                       Bitmap imageBitmap = mViewHolder.imageview.getDrawingCache();  
	                       if (imageBitmap != null) {  
	                           new ImageAsyncTask(mContext,  mViewHolder.imageview,bean.getSrc()).execute(imageBitmap);  
	                       }  
	                   }  
	               });  
	               builder.show();  
	           }  
	       });  
		container.addView(convertView);
		return convertView;
	}
	
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
//		final ImageView imageview = new ImageView (mContext);
//		if (bean != null) {
//			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
//				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
//						.cacheInMemory().cacheOnDisc().build();
//				ImageLoader.getInstance().displayImage(bean.getSrc(),imageview, options, null);
//			}
//		}
//
//		 imageview.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
//				weakReferenceHandler.sendEmptyMessage(7000);
//			}
//		});
//		imageview.setOnLongClickListener(new View.OnLongClickListener() {  
//	           @Override  
//	           public boolean onLongClick(View v) {  
//	               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  
//	               builder.setItems(new String[]{mContext.getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {  
//	                   @Override  
//	                   public void onClick(DialogInterface dialog, int which) {  
//	                	   imageview.setDrawingCacheEnabled(true);  
//	                       Bitmap imageBitmap = imageview.getDrawingCache();  
//	                       if (imageBitmap != null) {  
//	                           new ImageAsyncTask(mContext,  imageview,bean.getSrc()).execute(imageBitmap);  
//	                       }  
//	                   }  
//	               });  
//	               builder.show();  
//	               return true;  
//	           }  
//	       });  
//		container.addView(imageview, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		return imageview;
//	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	private class ViewHolder {
		ZoomImageView imageview;
		TextView txt_save;
	}

}