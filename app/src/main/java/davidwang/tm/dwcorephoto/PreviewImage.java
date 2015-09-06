package davidwang.tm.dwcorephoto;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import davidwang.tm.model.ImageBDInfo;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.tools.ImageLoaders;
import davidwang.tm.view.HackyViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

public class PreviewImage extends BaseActivity implements OnPageChangeListener {

	private int index = 0;
	private ViewPager viewpager;
	private ArrayList<ImageInfo> ImgList;

	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private SamplePagerAdapter pagerAdapter;

	private float moveheight;
	private int type;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browseimage);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		findID();
		Listener();
		InData();
		getValue();
	}

	@Override
	public void findID() {
		// TODO Auto-generated method stub
		super.findID();
		viewpager = (HackyViewPager) findViewById(R.id.bi_viewpager);
	}

	@Override
	public void Listener() {
		// TODO Auto-generated method stub
		super.Listener();
		viewpager.setOnPageChangeListener(this);
	}

	@Override
	public void InData() {
		// TODO Auto-generated method stub
		super.InData();
		index = getIntent().getIntExtra("index", 0);
		type = getIntent().getIntExtra("type",0);
		ImgList = (ArrayList<ImageInfo>)getIntent().getSerializableExtra("data");
		imageInfo = ImgList.get(index);
		bdInfo = (ImageBDInfo)getIntent().getSerializableExtra("bdinfo");
		pagerAdapter = new SamplePagerAdapter();
		viewpager.setAdapter(pagerAdapter);
		viewpager.setCurrentItem(index);
		if (type == 1){
			moveheight = dip2px(70);
		}else if (type == 2){
			moveheight = (Width - 3 * dip2px(2))/3;
		}
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (showimg == null){
			return;
		}
		ImageInfo info = ImgList.get(arg0);
		ImageLoaders.setsendimg(info.url, showimg);
		if (type == 1){
			int move_index = arg0 - index;
			to_y = move_index * moveheight;
		}else if (type == 2){
			int	a = index / 3;
			int b = index % 3;
			int a1 = arg0 / 3;
			int b1 = arg0 % 3;
			float img_w = (Width - 3 * dip2px(2))/3;
			to_y = (a1 - a) * img_w + (a1 - a) * dip2px(2);
			to_x = (b1 - b) * img_w + (b1 - b) * dip2px(2);

		}
	}

	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return ImgList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			String path = ImgList.get(position).url;
			ImageLoader.getInstance().displayImage(path, photoView, options,
					animateFirstListener);
			// Now just add PhotoView to ViewPager and return it
			photoView.setOnViewTapListener(new OnViewTapListener() {  
                
                @Override  
                public void onViewTap(View arg0, float arg1, float arg2) {
					viewpager.setVisibility(View.GONE);
					showimg.setVisibility(View.VISIBLE);
					setShowimage();
//                    finish();
                }  
            });
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	private class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				imageView.setImageBitmap(loadedImage);
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 监听返回点击事件
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_OK);
			finish();
		}
		// 返回true,吞掉back事件
		return true;
	}

	@Override
	protected void EndSoring() {
		super.EndSoring();
		viewpager.setVisibility(View.VISIBLE);
		showimg.setVisibility(View.GONE);
	}

	@Override
	protected void EndMove() {
		super.EndMove();
		finish();
	}
}