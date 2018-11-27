package com.bwei.zhouzitao.zhouzitao1123.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bwei.zhouzitao.zhouzitao1123.Bean;
import com.bwei.zhouzitao.zhouzitao1123.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class XlistBaseAdapter extends BaseAdapter {
    private Context context;

    public XlistBaseAdapter(Context context) {
        this.context = context;
    }

    private List<Bean.DataBean> list = new ArrayList<>();

    public void setList(List<Bean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(list.get(position).getThumbnail_pic_s02()) && !TextUtils.isEmpty(list.get(position).getThumbnail_pic_s03())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        int type = getItemViewType(i);
        switch (type) {
            case 0://三张图片
                ViewHolder1 viewHolder1;
                if (view == null) {
                    viewHolder1 = new ViewHolder1();
                    view = View.inflate(context, R.layout.layout_item2, null);
                    viewHolder1.name1 = view.findViewById(R.id.name1);
                    viewHolder1.pic1 = view.findViewById(R.id.pic1);
                    viewHolder1.pic2 = view.findViewById(R.id.pic2);
                    viewHolder1.pic3 = view.findViewById(R.id.pic3);
                    view.setTag(viewHolder1);
                } else {
                    viewHolder1 = (ViewHolder1) view.getTag();
                }
                //赋值
                viewHolder1.name1.setText(list.get(i).getTitle());
                if (!TextUtils.isEmpty(list.get(i).getThumbnail_pic_s())){
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),viewHolder1.pic1,options);
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s02(),viewHolder1.pic2,options);
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s03(),viewHolder1.pic3,options);
                }
                break;
            case 1://一张图片
                ViewHolder2 viewHolder2;
                if (view == null) {
                    viewHolder2 = new ViewHolder2();
                    view = View.inflate(context, R.layout.layout_item1, null);
                    viewHolder2.name2 = view.findViewById(R.id.name2);
                    viewHolder2.desc = view.findViewById(R.id.desc);
                    viewHolder2.pic = view.findViewById(R.id.pic);
                    view.setTag(viewHolder2);
                } else {
                    viewHolder2 = (ViewHolder2) view.getTag();
                }
                //赋值
                viewHolder2.name2.setText(list.get(i).getTitle());
                viewHolder2.desc.setText(list.get(i).getDate());
                ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),viewHolder2.pic,options);
                break;
        }
        return view;
    }

    class ViewHolder1 {
        TextView name1;
        ImageView pic1, pic2, pic3;
    }

    class ViewHolder2 {
        TextView name2, desc;
        ImageView pic;
    }
}
