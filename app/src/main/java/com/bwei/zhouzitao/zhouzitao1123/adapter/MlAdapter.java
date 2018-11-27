package com.bwei.zhouzitao.zhouzitao1123.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bwei.zhouzitao.zhouzitao1123.R;

import java.util.ArrayList;

public class MlAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private TextView ctv;

    public MlAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      view = View.inflate(context, R.layout.citem,null);
        ctv = view.findViewById(R.id.ctv);
        ctv.setText(list.get(i).toString());
        return view;
    }
}
