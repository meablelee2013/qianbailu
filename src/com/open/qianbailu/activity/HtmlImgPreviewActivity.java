package com.open.qianbailu.activity;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.open.qianbailu.R;
import com.open.qianbailu.utils.ImageAsyncTask;

public class HtmlImgPreviewActivity extends Activity {

    ViewPager vp;
    TextView tv_position;
    List<String> imgList;
    int mPosition;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_page);
        Bundle b = getIntent().getBundleExtra("b");
        if(b!=null){
       	 imgList = b.getStringArrayList("imglist");
            mPosition=b.getInt("position");
       }
        
        if(imgList==null){
        	imgList = new ArrayList<String>();
        	imgList.add("http://i1.1100lu.xyz/1100/vod/201702/18/vod/akttgwrrwpz.jpg");
        	mPosition = 0;
        }
        inflater = LayoutInflater.from(this);
        initView();
        initViewPaper();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_position.setText((mPosition+1)+"/" + imgList.size());
    }

    private void initViewPaper() {
        vp.setAdapter(new ImagePagerAdapter(imgList));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                tv_position.setText((mPosition + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(mPosition);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private List<String> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(List<String> images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.loading);
            final ImageView image = (ImageView) imageLayout.findViewById(R.id.image);
            Glide.with(HtmlImgPreviewActivity.this)
                    .load(imgList.get(position))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(image);
            image.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(HtmlImgPreviewActivity.this);  
		               builder.setItems(new String[]{HtmlImgPreviewActivity.this.getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {  
		                   @Override  
		                   public void onClick(DialogInterface dialog, int which) {  
		                	   image.setDrawingCacheEnabled(true);  
		                       Bitmap imageBitmap = image.getDrawingCache();  
		                       if (imageBitmap != null) {  
		                           new ImageAsyncTask(HtmlImgPreviewActivity.this,  image,images.get(position)).execute(imageBitmap);  
		                       }  
		                   }  
		               });  
		               builder.show();  
					return false;
				}
			});
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

}
