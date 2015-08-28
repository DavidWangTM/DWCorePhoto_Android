package davidwang.tm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import davidwang.tm.dwcorephoto.R;
import davidwang.tm.model.MainInfo;

/**
 * Created by DavidWang on 15/8/25.
 */
public class MainListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<MainInfo> data;

    public MainListAdapter(Context context, ArrayList<MainInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        MainInfo info = data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.main_view, null);
            holder.main_text = (TextView)convertView.findViewById(R.id.main_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.main_text.setText(info.content);



//        animate(holder.main_text).setDuration(2000).rotationYBy(720).x(100).y(100);



        return convertView;
    }

    public class ViewHolder {
        TextView main_text;
    }


}
