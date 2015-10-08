package davidwang.tm.dwcorephoto;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import davidwang.tm.model.ImageBDInfo;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.tools.ImageLoaders;

/**
 * Created by DavidWang on 15/8/31.
 */
public class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    // 屏幕宽度
    public float Width;
    // 屏幕高度
    public float Height;

    protected  ImageView showimg;

    private final Spring mSpring = SpringSystem
            .create()
            .createSpring()
            .addListener(new ExampleSpringListener());

    private RelativeLayout MainView;

    protected ImageBDInfo bdInfo;
    protected ImageInfo imageInfo;
    private float size,size_h;

    private float img_w;
    private float img_h;

    //原图高
    private float y_img_h;
    protected float to_x = 0;
    protected float to_y = 0;
    private float tx;
    private float ty;
    private int statusBarHeight;
    private int titleBarHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm =getResources().getDisplayMetrics();
        Width = dm.widthPixels;
        Height = dm.heightPixels;
        setToolbar(0xff009688);
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        //statusBarHeight是上面所求的状态栏的高度
        titleBarHeight = contentTop - statusBarHeight;
    }

    protected void setToolbar(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
//        int color = Color.argb(255, Color.red(255), Color.green(255), Color.blue(255));
        tintManager.setTintColor(color);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     *添加头部
     */
    protected void AddToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
    }


    /**
     * 获取资源
     */
    protected void findID() {
        MainView = (RelativeLayout)findViewById(R.id.MainView);
    }

    /**
     * 监听
     */
    protected void Listener() {
    }

    /**
     * 对传递数据处
     */
    protected void initIntent() {

    }

    /**
     * 初始
     */
    public void InData() {
    }

    /**
     * 显示提示信息
     *
     * @param text
     *            提示文本
     */
    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 清空Toast
     */
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    protected void getValue(){
        showimg = new ImageView(this);
        showimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoaders.setsendimg(imageInfo.url,showimg);
        img_w = bdInfo.width;
        img_h = bdInfo.height;
        size = Width/img_w;
        // Wait for layout.
        y_img_h = imageInfo.height*Width/imageInfo.width;
        size_h = y_img_h/img_h;

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int)bdInfo.width,(int)bdInfo.height);
        p.setMargins((int)bdInfo.x,(int)bdInfo.y,0,0);
        showimg.setLayoutParams(p);
        MainView.addView(showimg);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setShowimage();
            }
        }, 300);


    }


    protected void setShowimage(){
        if (mSpring.getEndValue() == 0){
            mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(170, 5));
            tx = Width/2 - (bdInfo.x+img_w/2);
            ty = Height/2-(bdInfo.y+img_h/2);
            MoveView();
            return;
        }
        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 5));
        mSpring.setEndValue(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
//                        show_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                MoveBackView();
            }
        }, 300);

    }

    private class ExampleSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double CurrentValue = spring.getCurrentValue();
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size);
            float mapy =  (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size_h);
            showimg.setScaleX(mappedValue);
            showimg.setScaleY(mapy);
            if (CurrentValue == 1){
                EndSoring();
            }
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

    protected void EndSoring(){

    }

    protected  void EndMove(){

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


    private void MoveView(){

        ObjectAnimator.ofFloat(MainView,"alpha",0.8f).setDuration(0).start();
        MainView.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(showimg, "translationX",tx).setDuration(200),
                ObjectAnimator.ofFloat(showimg, "translationY",ty).setDuration(200),
                ObjectAnimator.ofFloat(MainView,"alpha",1).setDuration(200)

        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                showimg.setScaleType(ImageView.ScaleType.FIT_XY);
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
                ObjectAnimator.ofFloat(showimg, "translationX",to_x).setDuration(200),
                ObjectAnimator.ofFloat(showimg, "translationY",to_y).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                showimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                EndMove();
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
