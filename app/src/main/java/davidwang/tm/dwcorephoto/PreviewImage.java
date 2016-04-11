package davidwang.tm.dwcorephoto;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

    private LinearLayout AddLayout;
    private View moveView;
    private RelativeLayout addrelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browseimage);
        findID();
        Listener();
        InData();
        getValue();
        setToolbar(0xff000000);
        AddInstructionsView();
    }

    @Override
    public void findID() {
        // TODO Auto-generated method stub
        super.findID();
        viewpager = (HackyViewPager) findViewById(R.id.bi_viewpager);
        AddLayout = (LinearLayout) findViewById(R.id.AddLayout);
        moveView = (View) findViewById(R.id.moveView);
        addrelative = (RelativeLayout) findViewById(R.id.addrelative);
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
        type = getIntent().getIntExtra("type", 0);
        ImgList = (ArrayList<ImageInfo>) getIntent().getSerializableExtra("data");
        imageInfo = ImgList.get(index);
        bdInfo = (ImageBDInfo) getIntent().getSerializableExtra("bdinfo");
        pagerAdapter = new SamplePagerAdapter();
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(index);
        moveView.setX(dip2px(10) * index);
        if (ImgList.size() == 1) {
            addrelative.setVisibility(View.GONE);
        }
        if (type == 1) {
            moveheight = dip2px(70);
        } else if (type == 2) {
            moveheight = (Width - 3 * dip2px(2)) / 3;
        } else if (type == 3) {
            moveheight = (Width - dip2px(80) - dip2px(2)) / 3;
        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        moveView.setX(dip2px(5) + dip2px(10) * arg0 + dip2px(10) * arg1);

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        if (showimg == null) {
            return;
        }
        ImageInfo info = ImgList.get(arg0);
        ImageLoaders.setsendimg(info.url, showimg);
        if (type == 1) {
            int move_index = arg0 - index;
            to_y = move_index * moveheight;
        } else if (type == 2) {
            int a = index / 3;
            int b = index % 3;
            int a1 = arg0 / 3;
            int b1 = arg0 % 3;
            to_y = (a1 - a) * moveheight + (a1 - a) * dip2px(2);
            to_x = (b1 - b) * moveheight + (b1 - b) * dip2px(2);
        } else if (type == 3) {
            int a = index / 3;
            int b = index % 3;
            int a1 = arg0 / 3;
            int b1 = arg0 % 3;
            to_y = (a1 - a) * moveheight + (a1 - a) * dip2px(1);
            to_x = (b1 - b) * moveheight + (b1 - b) * dip2px(1);
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewpager.getVisibility() == View.VISIBLE) {
                viewpager.setVisibility(View.GONE);
                showimg.setVisibility(View.VISIBLE);
                setShowimage();
            }
        }
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

    private void AddInstructionsView() {
        for (int i = 0; i < ImgList.size(); i++) {
            View addview = new View(PreviewImage.this);
            addview.setBackgroundColor(0xffffffff);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(dip2px(5), dip2px(5));
            addview.setLayoutParams(p);
            p.setMargins(dip2px(5), 0, 0, 0);
            AddLayout.addView(addview);
        }
    }

}