package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import davidwang.tm.adapter.GridViewAdapter;
import davidwang.tm.model.ImageInfo;

public class GridViewActivity extends BaseActivity {

    public GridView gridView;
    private GridViewAdapter adapter;
    private ArrayList<ImageInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        findID();
        InData();

    }

    @Override
    protected void findID() {
        super.findID();
        gridView = (GridView)findViewById(R.id.gridview);

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
        adapter = new GridViewAdapter(GridViewActivity.this,data);
        gridView.setAdapter(adapter);

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


}
