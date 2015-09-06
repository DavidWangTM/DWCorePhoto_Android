package davidwang.tm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

import davidwang.tm.dwcorephoto.GridViewActivity;
import davidwang.tm.dwcorephoto.PreviewImage;
import davidwang.tm.dwcorephoto.R;
import davidwang.tm.model.ImageBDInfo;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.tools.ImageLoaders;

/**
 * Created by DavidWang on 15/9/6.
 */
public class GridViewAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ImageInfo> data;
    private ImageBDInfo bdInfo;

    public GridViewAdapter(Context context, ArrayList<ImageInfo> data) {
        this.context = context;
        this.data = data;
        bdInfo = new ImageBDInfo();
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
        ImageInfo info = data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.grid_view, null);
            holder.gridimage = (ImageView)convertView.findViewById(R.id.gridimage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ImageLoaders.setsendimg(info.url, holder.gridimage);
        holder.gridimage.setOnClickListener(new ImageOnclick(position,holder.gridimage));

        return convertView;
    }

    public class ViewHolder {
        ImageView gridimage;
    }

    private class ImageOnclick implements View.OnClickListener{

        private int index;
        private ImageView imageView;

        public ImageOnclick(int index ,ImageView imageView) {
            this.index = index;
            this.imageView = imageView;
        }

        @Override
        public void onClick(View v) {

            GridViewActivity activity = (GridViewActivity)context;
            View c = activity.gridView.getChildAt(0);
            int top = c.getTop();
            int firstVisiblePosition = activity.gridView.getFirstVisiblePosition() /3;

            int a,b;
            a = index / 3;
            b = index % 3;
            Log.e("1", "高==" + top + "=" + firstVisiblePosition + "b="+b);
            bdInfo.width = (activity.Width - 3 * dip2px(2))/3;
            bdInfo.height = bdInfo.width;
            bdInfo.x = dip2px(1) + b * bdInfo.width + b * dip2px(2);
            bdInfo.y = dip2px(1) + bdInfo.height * (a - firstVisiblePosition) + top + a *dip2px(2);
            Intent intent = new Intent(context, PreviewImage.class);
            intent.putExtra("data", (Serializable) data);
            intent.putExtra("bdinfo",bdInfo);
            intent.putExtra("index",index);
            intent.putExtra("type",2);
            context.startActivity(intent);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
