/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:59:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.qianbailu.R;
import com.open.qianbailu.adapter.CommonExpandableListAdapter;
import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.bean.m.PicKuFilmBean;
import com.open.qianbailu.view.ExpendGridView;
import com.open.qianbailu.view.ExpendListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:59:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuNavMExpandableListAdapter extends CommonExpandableListAdapter<NavMBean, NavMChildBean> {

	public QianBaiLuNavMExpandableListAdapter(Context mContext, List<NavMBean> list) {
		super(mContext, list);
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.open.tencenttv.adapter.CommonExpandableListAdapter#getChild(int,
	// * int)
	// */
	// @Override
	// public UmeiMDdBean getChild(int groupPosition, int childPosition) {
	// // TODO Auto-generated method stub
	// return list.get(groupPosition).getDdlist().get(childPosition);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getChildView(int,
	 * int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildViewHolder mChildViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_pic_child, null);
			mChildViewHolder = new ChildViewHolder();
			mChildViewHolder.gridView = (ExpendGridView) convertView.findViewById(R.id.gridView);
			mChildViewHolder.listview = (ExpendListView) convertView.findViewById(R.id.listview);
			convertView.setTag(mChildViewHolder);
		} else {
			mChildViewHolder = (ChildViewHolder) convertView.getTag();
		}

		mChildViewHolder.picKuL = getGroup(groupPosition).getPicKuL();
		mChildViewHolder.mQianBaiLuNavMPicGridViewAdapter = new QianBaiLuNavMPicGridViewAdapter(mContext, mChildViewHolder.picKuL);
		mChildViewHolder.gridView.setAdapter(mChildViewHolder.mQianBaiLuNavMPicGridViewAdapter);
		mChildViewHolder.mQianBaiLuNavMPicGridViewAdapter.notifyDataSetChanged();

		mChildViewHolder.list = getGroup(groupPosition).getList();
		mChildViewHolder.mQianBaiLuMListAdapter = new QianBaiLuMListAdapter(mContext, mChildViewHolder.list);
		mChildViewHolder.listview.setAdapter(mChildViewHolder.mQianBaiLuMListAdapter);
		mChildViewHolder.mQianBaiLuMListAdapter.notifyDataSetChanged();
		return convertView;
	}

	public class ChildViewHolder {
		ExpendGridView gridView;
		QianBaiLuNavMPicGridViewAdapter mQianBaiLuNavMPicGridViewAdapter;
		List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();

		ExpendListView listview;
		QianBaiLuMListAdapter mQianBaiLuMListAdapter;
		List<NavMChildBean>  list = new ArrayList<NavMChildBean>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getChildrenCount
	 * (int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (getGroup(groupPosition) != null
				&& ((getGroup(groupPosition).getPicKuL() != null && getGroup(groupPosition).getPicKuL().size() > 0) || (getGroup(groupPosition).getList() != null && getGroup(groupPosition)
						.getList().size() > 0))) {
			return 1;
		} else {
			return 0;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getGroupView(int,
	 * boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean arg1, View convertView, ViewGroup parent) {
		GroupViewHolder mGroupViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_nav_group, null);
			mGroupViewHolder = new GroupViewHolder();
			mGroupViewHolder.text_moduleTitle = (TextView) convertView.findViewById(R.id.text_moduleTitle);
			convertView.setTag(mGroupViewHolder);
		} else {
			mGroupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		NavMBean bean = (NavMBean) getGroup(groupPosition);
		if (bean != null) {
			mGroupViewHolder.text_moduleTitle.setText(bean.getTitle());
		}
		return convertView;
	}

	public class GroupViewHolder {
		TextView text_moduleTitle;
	}
}