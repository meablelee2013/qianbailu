/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:33:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.db;

import java.util.List;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.weak.WeakReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:33:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class OpenDBListTypeAdapter extends CommonAdapter<OpenDBBean> {
 
	private WeakReferenceHandler weakReferenceHandler ;
	public OpenDBListTypeAdapter(Context mContext,WeakReferenceHandler weakReferenceHandler, List<OpenDBBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_open_db, parent, false);
			viewHolder.text_movieTitle = (TextView) convertView.findViewById(R.id.text_movieTitle);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			viewHolder.text_type = (TextView) convertView.findViewById(R.id.text_type);
			viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
			viewHolder.btn_detail = (Button) convertView.findViewById(R.id.btn_detail);
			viewHolder.btn_uncollection = (Button) convertView.findViewById(R.id.btn_uncollection);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OpenDBBean bean = (OpenDBBean) getItem(position);
		if (bean != null) {
			viewHolder.text_movieTitle.setText(bean.getTitle());
			viewHolder.text_type.setText(bean.getTypename());
			viewHolder.text_time.setText(bean.getTime());

			if(bean.getType()==1 || bean.getType()==4){
				viewHolder.imageview.setVisibility(View.GONE);
			}else{
				viewHolder.imageview.setVisibility(View.VISIBLE);
				if (bean.getImgsrc() != null && bean.getImgsrc().length() > 0) {
					DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
							.cacheInMemory().cacheOnDisc().build();
					ImageLoader.getInstance().displayImage(bean.getImgsrc(), viewHolder.imageview, options, getImageLoadingListener());
				}
			}
		
			viewHolder.btn_detail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Message msg = weakReferenceHandler.obtainMessage();
					msg.what = 9000;
					msg.arg1 = position;
				    weakReferenceHandler.sendMessage(msg);
				}
			});
			viewHolder.btn_uncollection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Message msg = weakReferenceHandler.obtainMessage();
					msg.what = 9002;
					msg.arg1 = position;
				    weakReferenceHandler.sendMessage(msg);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView text_movieTitle;
		TextView text_type;
		TextView text_time;
		Button btn_detail,btn_uncollection;
		ImageView imageview;
	}

}
