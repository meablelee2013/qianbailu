/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:09:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.adapter.m;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.qianbailu.R;
import com.open.qianbailu.activity.m.QianBaiLuShowPagerAdapterFragmentActivity;
import com.open.qianbailu.adapter.CommonAdapter;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.utils.QRCodeUtils;
import com.open.qianbailu.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:09:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuMShowAdapter extends CommonAdapter<ShowBean> {

	public QianBaiLuMShowAdapter(Context mContext, List<ShowBean> list) {
		super(mContext, list);
	}

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ViewHolder viewHolder = null;
	// if (convertView == null) {
	// viewHolder = new ViewHolder();
	// convertView = mInflater.inflate(R.layout.adapter_qianbailu_m_show_image,
	// parent, false);
	// viewHolder.text_alt = (TextView) convertView.findViewById(R.id.text_alt);
	// viewHolder.imageview = (ImageView)
	// convertView.findViewById(R.id.imageview);
	//
	// convertView.setTag(viewHolder);
	// } else {
	// viewHolder = (ViewHolder) convertView.getTag();
	// }
	// final ShowBean bean = (ShowBean) getItem(position);
	// if (bean != null) {
	// viewHolder.text_alt.setText(bean.getAlt());
	// if (bean.getSrc() != null && bean.getSrc().length() > 0) {
	// DisplayImageOptions options = new
	// DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
	// .cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
	// .build();
	// ImageLoader.getInstance().displayImage(bean.getSrc(),
	// viewHolder.imageview, options, getImageLoadingListener());
	// }
	// }
	// convertView.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// QianBaiLuWebViewActivity.startUmeiWebViewActivity(mContext,
	// bean.getSrc());
	// }
	// });
	// return convertView;
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.adapter_qianbailu_m_show_image, parent, false);
		TextView text_alt = (TextView) view.findViewById(R.id.text_alt);
		ImageView imageview = (ImageView) view.findViewById(R.id.imageview);

		final ShowBean bean = (ShowBean) getItem(position);
		if (bean != null) {
			text_alt.setText(bean.getAlt());
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				if(bean.getSrc().contains(UrlUtils.PC_QIAN_BAI_LU_CODE)){
					//http://pan.baidu.com/share/qrcode?w=800&h=248&url=https://m.lu.sex/show-13-388511.html
					String src = bean.getSrc().split("&url=")[1];
					Bitmap qrBitmap = QRCodeUtils.generateBitmap(src,600, 600);  
				    Bitmap logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);  
				    Bitmap bitmap = QRCodeUtils.addLogo(qrBitmap, logoBitmap);  
				    imageview.setImageBitmap(bitmap); 
				    imageview.setScaleType(ScaleType.CENTER_INSIDE);
				}else{
					imageview.setScaleType(ScaleType.FIT_XY);
					DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
							.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
					ImageLoader.getInstance().displayImage(bean.getSrc(), imageview, options, getImageLoadingListener());
				}
			}
		}
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				QianBaiLuWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
				ShowJson mShowJson = new ShowJson();
				mShowJson.setList(list);
				mShowJson.setCurrentPosition(position);
//				QianBaiLuShowPagerAdapterActivity.startQianBaiLuShowPagerAdapterActivity(mContext, mShowJson);
				QianBaiLuShowPagerAdapterFragmentActivity.startQianBaiLuShowPagerAdapterFragmentActivity(mContext, mShowJson);
			}
		});
		return view;
	}

	class ViewHolder {
		TextView text_alt;
		ImageView imageview;
	}

}
