package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import davidwang.tm.view.PullToZoomListView;

public class MixShowActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private PullToZoomListView mixlist;
    private String[] adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_show);
        findID();
        AddToolbar();
    }


    @Override
    protected void findID() {
        super.findID();
        mixlist = (PullToZoomListView)findViewById(R.id.mixlist);
        adapterData = new String[] { "Activity","Service","Content Provider","Intent","BroadcastReceiver","ADT","Sqlite3","HttpClient","DDMS","Android Studio","Fragment","Loader" };

        mixlist.setAdapter(new ArrayAdapter<String>(MixShowActivity.this,
                android.R.layout.simple_list_item_1, adapterData));

        mixlist.getHeaderView().setImageResource(R.mipmap.mixheadimg);
        mixlist.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        mixlist.setOnItemClickListener(this);
    }

    @Override
    public void InData() {
        super.InData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("1",position+"haha");
    }
}
