package davidwang.tm.dwcorephoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

import davidwang.tm.model.ImageBDInfo;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.tools.ImageLoaders;


public class SingleShow extends BaseActivity implements View.OnClickListener{


    private ImageView show_img;
    private ArrayList<ImageInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_show);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        findID();
        Listener();

    }

    @Override
    protected void findID() {
        super.findID();
        data = new ArrayList<ImageInfo>();
        bdInfo = new ImageBDInfo();
        imageInfo = new ImageInfo();
        imageInfo.width = 1280;
        imageInfo.height = 720;
        imageInfo.url = "http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg";
        data.add(imageInfo);
        show_img = (ImageView)findViewById(R.id.show_img);
        ImageLoaders.setsendimg(imageInfo.url, show_img);
    }

    @Override
    protected void Listener() {
        super.Listener();
        show_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_img:
                bdInfo.x = show_img.getLeft();
                bdInfo.y = show_img.getTop();
                bdInfo.width = show_img.getLayoutParams().width;
                bdInfo.height = show_img.getLayoutParams().height;
                Intent intent = new Intent(SingleShow.this, PreviewImage.class);
                intent.putExtra("data", (Serializable) data);
                intent.putExtra("bdinfo",bdInfo);
                intent.putExtra("index",0);
                startActivity(intent);
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


}
