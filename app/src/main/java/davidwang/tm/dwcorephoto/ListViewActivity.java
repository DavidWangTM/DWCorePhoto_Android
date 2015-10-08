package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import davidwang.tm.adapter.ListViewAdapter;
import davidwang.tm.model.ImageInfo;

public class ListViewActivity extends BaseActivity implements View.OnTouchListener,AbsListView.OnScrollListener{

    public ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<ImageInfo> data;
    private RelativeLayout relative;

    private boolean is_top ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        findID();
        InData();
        Listener();
        AddToolbar();
    }

    @Override
    protected void findID() {
        super.findID();
        listView = (ListView)findViewById(R.id.listview);
//        relative = (RelativeLayout)findViewById(R.id.relative);
    }

    @Override
    public void InData() {
        super.InData();
        data = new ArrayList<ImageInfo>();
        for (int i = 0 ; i < 5; i++) {
            ImageInfo model = new ImageInfo();
            model.url = "http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg";
            model.width = 1280;
            model.height = 720;
            data.add(model);
            ImageInfo model1 = new ImageInfo();
            model1.url = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
            model1.width = 1280;
            model1.height = 720;
            data.add(model1);
            ImageInfo model2 = new ImageInfo();
            model2.url = "http://imgsrc.baidu.com/forum/pic/item/8e230cf3d7ca7bcb3acde5a2be096b63f724a8b2.jpg";
            model2.width = 1280;
            model2.height = 720;
            data.add(model2);
            ImageInfo model3 = new ImageInfo();
            model3.url = "http://att.bbs.duowan.com/forum/201210/20/210446opy9p5pghu015p9u.jpg";
            model3.width = 1280;
            model3.height = 720;
            data.add(model3);
            ImageInfo model4 = new ImageInfo();
            model4.url = "http://img5.duitang.com/uploads/item/201404/11/20140411214939_XswXa.jpeg";
            model4.width = 1280;
            model4.height = 720;
            data.add(model4);
        }
        adapter = new ListViewAdapter(ListViewActivity.this,data);
        listView.setAdapter(adapter);

    }

    @Override
    protected void Listener() {
        super.Listener();
        listView.setOnTouchListener(this);
        listView.setOnScrollListener(this);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("1",event.getY()+"-Y");
        int actionMasked = MotionEventCompat.getActionMasked(event);
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem == 0){
            Log.e("log", "滑到顶部");
            is_top = true;
        }else{
            Log.e("log", "滑到中");
            is_top = false;
        }
        if(visibleItemCount+firstVisibleItem==totalItemCount){
//            Log.e("log", "滑到底部");
        }
    }
}
