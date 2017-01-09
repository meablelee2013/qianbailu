/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:51:00
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:51:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMMovieListAdapter extends CommonAdapter<MovieBean> {

	public QianBaiLuMMovieListAdapter(Context mContext, List<MovieBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_movie_list, parent, false);
			viewHolder.text_movieTitle = (TextView) convertView.findViewById(R.id.text_movieTitle);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			viewHolder.text_type = (TextView) convertView.findViewById(R.id.text_type);
			viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
			viewHolder.btn_detail = (Button) convertView.findViewById(R.id.btn_detail);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MovieBean bean = (MovieBean) getItem(position);
		if (bean != null) {
			viewHolder.text_movieTitle.setText(bean.getTitle());
			viewHolder.text_type.setText(bean.getVmtype());
			viewHolder.text_time.setText(bean.getProduceyear());

			if (bean.getThumb() != null && bean.getThumb().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getThumb(), viewHolder.imageview, options, getImageLoadingListener());
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView text_movieTitle;
		TextView text_type;
		TextView text_time;
		Button btn_detail;
		ImageView imageview;
	}

}
