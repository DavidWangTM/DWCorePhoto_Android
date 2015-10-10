package davidwang.tm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import davidwang.tm.dwcorephoto.MixShowActivity;
import davidwang.tm.dwcorephoto.PreviewImage;
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
        holder.showimage.setVisibility(View.GONE);
        holder.gridview.setVisibility(View.GONE);
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
            holder.showimage.setOnClickListener(new SingleOnclick(position,holder.showimage));
        }else if(info.data.size() > 1){
            holder.showimage.setVisibility(View.GONE);
            holder.gridview.setVisibility(View.VISIBLE);
            int a = info.data.size()/3;
            int b = info.data.size()%3;
            if (b > 0){
                a++;
            }
            float width = (activity.Width - dip2px(80) - dip2px(2))/3;
            holder.gridview.getLayoutParams().height = (int)(a*width);

            for (int i = 0 ; i < 9 ; i++){
                holder.imgview[i].setVisibility(View.GONE);
            }

            for (int i = 0 ; i < info.data.size(); i++){
                ImageInfo imageInfo = info.data.get(i);
                holder.imgview[i].setVisibility(View.VISIBLE);
                holder.imgview[i].getLayoutParams().width = (int)width;
                holder.imgview[i].getLayoutParams().height = (int)width;
                ImageLoaders.setsendimg(imageInfo.url, holder.imgview[i]);
                holder.imgview[i].setOnClickListener(new GridOnclick(position,holder.imgview[i],i,holder.gridview));
            }
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

    class SingleOnclick implements View.OnClickListener{

        private int index;
        private ImageView imageView;

        public SingleOnclick(int index,ImageView imageView){
            this.index = index;
            this.imageView = imageView;
        }

        @Override
        public void onClick(View v) {

            View c = activity.mixlist.getChildAt(0);
            int top = c.getTop();
            int firstVisiblePosition = activity.mixlist.getFirstVisiblePosition();

            float height  = 0.0f;
            for (int i = 0 ; i < ((index + 1) - firstVisiblePosition) ; i++){
                View view = activity.mixlist.getChildAt(i);
                height += view.getHeight();
            }
            bdInfo.x = imageView.getLeft();
            bdInfo.y = imageView.getTop() +  height + top + activity.mixlist.getTop();
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;
            Intent intent = new Intent(context, PreviewImage.class);
            ArrayList<ImageInfo> info = data.get(index).data;
            Log.e("1",info.toString());
            intent.putExtra("data", (Serializable) info);
            intent.putExtra("bdinfo",bdInfo);
            intent.putExtra("index",0);
            intent.putExtra("type",0);
            context.startActivity(intent);
        }
    }

    class GridOnclick implements View.OnClickListener{

        private int index;
        private int row;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int index,ImageView imageView,int row,GridLayout gridLayout){
            this.index = index;
            this.imageView = imageView;
            this.gridLayout = gridLayout;
            this.row = row;
        }

        @Override
        public void onClick(View v) {
            View c = activity.mixlist.getChildAt(0);
            int top = c.getTop();
            int firstVisiblePosition = activity.mixlist.getFirstVisiblePosition();
            float height  = 0.0f;
            for (int i = 0 ; i < ((index + 1) - firstVisiblePosition) ; i++){
                View view = activity.mixlist.getChildAt(i);
                height += view.getHeight();
            }
            bdInfo.x = imageView.getLeft() + gridLayout.getLeft();
            bdInfo.y = gridLayout.getTop()+ imageView.getTop() + height + top + activity.mixlist.getTop();
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;
            Intent intent = new Intent(context, PreviewImage.class);
            ArrayList<ImageInfo> info = data.get(index).data;
            Log.e("1",info.toString());
            intent.putExtra("data", (Serializable) info);
            intent.putExtra("bdinfo",bdInfo);
            intent.putExtra("index",row);
            intent.putExtra("type",3);
            context.startActivity(intent);
        }
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
