/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.m.PicKuFilmBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuNavMPicGridViewAdapter extends CommonAdapter<PicKuFilmBean> {

	public QianBaiLuNavMPicGridViewAdapter(Context mContext, List<PicKuFilmBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_nav_pic_gridview, parent, false);
			viewHolder.text_picTitle = (TextView) convertView.findViewById(R.id.text_picTitle);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final PicKuFilmBean bean = (PicKuFilmBean) getItem(position);
		if (bean != null) {
			viewHolder.text_picTitle.setText(bean.getTitle());
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), viewHolder.imageview, options, getImageLoadingListener());
			}
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QianBaiLuMMoveDetailFragmentActivity.startQianBaiLuMMoveDetailFragmentActivity(mContext, bean.getHref());
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView text_picTitle;
		ImageView imageview;
	}

}
