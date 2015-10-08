package davidwang.tm.dwcorephoto;

import android.os.Bundle;
import android.widget.ListView;

public class MixShowActivity extends BaseActivity {

    private ListView mixlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_show);
        AddToolbar();
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
