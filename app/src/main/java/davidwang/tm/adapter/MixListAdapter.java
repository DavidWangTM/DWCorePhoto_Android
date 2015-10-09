package davidwang.tm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import davidwang.tm.dwcorephoto.MixShowActivity;
import davidwang.tm.dwcorephoto.R;
import davidwang.tm.model.ImageBDInfo;
import davidwang.tm.model.ImageInfo;
import davidwang.tm.model.Mixinfo;
import davidwang.tm.tools.ImageLoaders;

/**
 * Created by DavidWang on 15/10/8.
 */
public class MixListAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<Mixinfo> data;
    private ImageBDInfo bdInfo;
    private MixShowActivity activity;
    private int ImagaId[] = {R.id.img_0,R.id.img_1,R.id.img_2,R.id.img_3,R.id.img_4,R.id.img_5,R.id.img_6,R.id.img_7,R.id.img_8};

    public MixListAdapter(Context context, ArrayList<Mixinfo> data) {
        this.context = context;
        this.data = data;
        bdInfo = new ImageBDInfo();
        activity = (MixShowActivity)context;
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
        Mixinfo info = data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.mix_view, null);
            holder.list_img = (ImageView)convertView.findViewById(R.id.listuserimg);
            holder.username = (TextView)convertView.findViewById(R.id.username);
            holder.usercontent = (TextView)convertView.findViewById(R.id.usercontent);
            holder.gridview = (GridLayout)convertView.findViewById(R.id.gridview);
            holder.showimage = (ImageView)convertView.findViewById(R.id.showimage);

            for (int i = 0; i < 9 ; i++){
                holder.imgview[i] = (ImageView)convertView.findViewById(ImagaId[i]);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoaders.setsendimg(info.userimg, holder.list_img);
        holder.username.setText(info.username);
        holder.usercontent.setText(info.content);
        if (info.data.size() == 1){
            holder.showimage.setVisibility(View.VISIBLE);
            holder.gridview.setVisibility(View.GONE);
            ImageInfo imageInfo = info.data.get(0);
            float w = imageInfo.width;
            float h = imageInfo.height;
            float width = 0.0f;
            float height = 0.0f;
            if (w > h){
                width = activity.Width - dip2px(130);
            }else if(w < h){
                width = activity.Width/3;
            }else if(w == h){
                width = activity.Width/2;
            }
            height = width*h/w;
            ImageLoaders.setsendimg(imageInfo.url, holder.showimage);
            holder.showimage.getLayoutParams().width = (int)width;
            holder.showimage.getLayoutParams().height = (int)height;

        }else if(info.data.size() > 1){
            holder.showimage.setVisibility(View.GONE);
            holder.gridview.setVisibility(View.VISIBLE);
            float width = (activity.Width - dip2px(80))/3;

        }

        return convertView;
    }

    public class ViewHolder {
        ImageView list_img;
        TextView username;
        TextView usercontent;
        GridLayout gridview;
        ImageView showimage;
        ImageView imgview [] = new ImageView[9];


    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
