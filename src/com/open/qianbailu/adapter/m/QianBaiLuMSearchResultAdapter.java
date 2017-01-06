/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午5:50:55
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
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.SearchBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午5:50:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMSearchResultAdapter extends CommonAdapter<SearchBean> {

	public QianBaiLuMSearchResultAdapter(Context mContext, List<SearchBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_s_search_result, parent, false);
			viewHolder.text_movieTitle = (TextView) convertView.findViewById(R.id.text_movieTitle);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final SearchBean bean = (SearchBean) getItem(position);
		if (bean != null) {
			viewHolder.text_movieTitle.setText(bean.getTitle());

		}
		return convertView;
	}

	class ViewHolder {
		TextView text_movieTitle;

	}

}
