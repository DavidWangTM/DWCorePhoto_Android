package davidwang.tm.dwcorephoto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import davidwang.tm.adapter.MixListAdapter;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.model.Mixinfo;
import davidwang.tm.view.PullToZoomListView;

public class MixShowActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    public PullToZoomListView mixlist;
    private MixListAdapter adapterData;
    private ArrayList<Mixinfo> data;

    private RelativeLayout bottomView;
    private EditText editText;
    private Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_show);
        findID();
        InData();
        AddToolbar();
    }

    @Override
    protected void findID() {
        super.findID();
        mixlist = (PullToZoomListView)findViewById(R.id.mixlist);
        mixlist.getHeaderView().setImageResource(R.mipmap.mixheadimg);
        mixlist.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        mixlist.setOnItemClickListener(this);
        mixlist.setOnTouchListener(new ListTouchEvent());
        bottomView = (RelativeLayout)findViewById(R.id.bottomView);
        editText = (EditText)findViewById(R.id.editText);
        sendBtn = (Button)findViewById(R.id.sendBtn);
    }

    @Override
    public void InData() {
        super.InData();
        data = new ArrayList<Mixinfo>();
        Mixinfo info1 = new Mixinfo();
        info1.username = "DavidWang";
        info1.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info1.content = "这是一个单张的演示";
        info1.data = AddData(1,0);
        data.add(info1);

        Mixinfo info2 = new Mixinfo();
        info2.username = "DavidWang";
        info2.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info2.content = "这是一个单张的演示";
        info2.data = AddData(1,1);
        data.add(info2);

        Mixinfo info3 = new Mixinfo();
        info3.username = "DavidWang";
        info3.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info3.content = "这是一个单张的演示";
        info3.data = AddData(1,2);
        data.add(info3);

        for (int i = 2; i < 10; i++){
            Mixinfo info4 = new Mixinfo();
            info4.username = "DavidWang";
            info4.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
            info4.content = "这是"+i+"个单张的演示测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度测试字符长度";
            info4.data = AddData(i,2);
            data.add(info4);
        }

        adapterData = new MixListAdapter(MixShowActivity.this,data);
        mixlist.setAdapter(adapterData);

    }

    private ArrayList<ImageInfo> AddData(int num,int type){
        ArrayList<ImageInfo> data = new ArrayList<ImageInfo>();
        for (int i = 0 ; i < num; i++){
            if (type == 0){
                ImageInfo info = new ImageInfo();
                info.url = "http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg";
                info.width = 1280;
                info.height = 720;
                data.add(info);
            }else if(type == 1) {
                ImageInfo info = new ImageInfo();
                info.url = "http://article.joyme.com/article/uploads/allimg/140812/101I01291-10.jpg";
                info.width = 640;
                info.height = 960;
                data.add(info);
            }else{
                ImageInfo info = new ImageInfo();
                info.url = "http://h.hiphotos.baidu.com/album/scrop%3D236%3Bq%3D90/sign=2fab0be130adcbef056a3959dc921cee/4b90f603738da977c61bb40eb151f8198618e3db.jpg";
                info.width = 236;
                info.height = 236;
                data.add(info);
            }
        }
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("1",position+"haha");
        hideEdit();
    }

    class ListTouchEvent implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            hideEdit();
            return false;
        }

    }

    private void hideEdit(){
        if (bottomView.getVisibility() == View.VISIBLE){
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    bottomView.setVisibility(View.GONE);
                }
            }, 300);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public void SendContent(int index){
        bottomView.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }


}
