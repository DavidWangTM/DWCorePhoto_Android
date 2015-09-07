package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

public class MixShowActivity extends BaseActivity {

    private ListView mixlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_show);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void findID() {
        super.findID();
        mixlist = (ListView)findViewById(R.id.mixlist);
    }

    @Override
    public void InData() {
        super.InData();
    }


}
