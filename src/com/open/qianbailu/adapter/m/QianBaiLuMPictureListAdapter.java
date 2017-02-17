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
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.m.MovieBean;

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
public class QianBaiLuMPictureListAdapter extends CommonAdapter<MovieBean> {

	public QianBaiLuMPictureListAdapter(Context mContext, List<MovieBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_picture_list, parent, false);
			viewHolder.text_movieTitle = (TextView) convertView.findViewById(R.id.text_movieTitle);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MovieBean bean = (MovieBean) getItem(position);
		if (bean != null) {
			viewHolder.text_movieTitle.setText(bean.getTitle());
			if(bean.getState()==1){
				viewHolder.text_movieTitle.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
			}else{
				viewHolder.text_movieTitle.setTextColor(mContext.getResources().getColor(android.R.color.black));
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView text_movieTitle;

	}

}
