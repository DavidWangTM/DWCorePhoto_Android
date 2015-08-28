package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;


public class SingleShow extends AppCompatActivity implements View.OnClickListener{

    private ImageView show_img;
    private final Spring mSpring = SpringSystem
            .create()
            .createSpring()
            .addListener(new ExampleSpringListener())
            .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(170, 5));

    private DisplayMetrics dm;
    private float size,size_h;

    float w_screen;
    float h_screen;
    float img_w;
    float img_h;

    //原图高
    float y_img_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_show);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        findID();
        lisenter();
        dm =getResources().getDisplayMetrics();
        //1280 720;
        ImageLoader.getInstance().displayImage("http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg",show_img);
        w_screen = dm.widthPixels;
        h_screen = dm.heightPixels;
        img_w = show_img.getLayoutParams().width;
        img_h = show_img.getLayoutParams().height;
        size = w_screen/img_w;
        // Wait for layout.
        y_img_h = 720*w_screen/1280;
        size_h = y_img_h/img_h;
    }

    private void findID(){
        show_img = (ImageView)findViewById(R.id.show_img);
    }

    private void lisenter() {
        show_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_img:
                Log.e("1",show_img.getLeft()+"-"+show_img.getTop());
                if (mSpring.getEndValue() == 0){
                    MoveView(w_screen/2 - (show_img.getLeft()+img_w/2),(h_screen - dip2px(20) - dip2px(50))/2-(show_img.getTop()+img_h/2));
                    return;
                }
                mSpring.setEndValue(0);
                show_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        MoveBackView();
                    }
                }, 300);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class ExampleSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, size);
            float mapy =  (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, size_h);
            show_img.setScaleX(mappedValue);
            show_img.setScaleY(mapy);
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    private void MoveView(float tx,float ty){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(show_img, "translationX", 0, tx).setDuration(200),
                ObjectAnimator.ofFloat(show_img, "translationY", 0, ty).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                show_img.setScaleType(ImageView.ScaleType.FIT_XY);
                mSpring.setEndValue(1);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }

    private void MoveBackView(){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(show_img, "translationX", 0).setDuration(200),
                ObjectAnimator.ofFloat(show_img, "translationY", 0).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }

}
