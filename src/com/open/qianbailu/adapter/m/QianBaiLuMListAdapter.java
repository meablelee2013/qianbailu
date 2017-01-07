/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午11:06:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.activity.QianBaiLuWebViewActivity;
import com.open.qianbailu.activity.m.QianBaiLuMMoveDetailFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMShowListFragmentActivity;
import com.open.qianbailu.activity.m.QianBaiLuMXiaoShuoFragmentActivity;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.m.NavMChildBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午11:06:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMListAdapter extends CommonAdapter<NavMChildBean> {

	public QianBaiLuMListAdapter(Context mContext, List<NavMChildBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NavMChildBean bean = (NavMChildBean) getItem(position);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.adapter_qianbailu_nav_m_list, null);
		TextView text_title = (TextView) view.findViewById(R.id.text_title);
		text_title.setText(bean.getTitle());

		text_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (bean.getType()) {
				case 1:
					QianBaiLuMXiaoShuoFragmentActivity.startQianBaiLuMXiaoShuoFragmentActivity(mContext,bean.getHref());
					break;
			    case 2:
			    	QianBaiLuMShowListFragmentActivity.startQianBaiLuMShowListFragmentActivity(mContext,bean.getHref());
					break;
			    case 3:
			    	QianBaiLuMMoveDetailFragmentActivity.startQianBaiLuMMoveDetailFragmentActivity(mContext, bean.getHref());
				break;
				default:
					QianBaiLuWebViewActivity.startUmeiWebViewActivity(mContext, bean.getHref());
					break;
				}
				
			}
		});

		return view;
	}
}