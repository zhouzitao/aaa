package com.bwei.zhouzitao.zhouzitao1123.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.zhouzitao.zhouzitao1123.Bean;
import com.bwei.zhouzitao.zhouzitao1123.R;
import com.bwei.zhouzitao.zhouzitao1123.adapter.XlistBaseAdapter;
import com.bwei.zhouzitao.zhouzitao1123.db.DBDao;
import com.bwei.zhouzitao.zhouzitao1123.utils.HttpUtils;
import com.bwei.zhouzitao.zhouzitao1123.utils.NetWorkUtil;
import com.example.xlistviewlib.XListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ShouFragment extends Fragment {
    private XListView mXlistView;
    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private List<Bean.DataBean> list = new ArrayList<>();
    private XlistBaseAdapter adapter;
    private int page = 1;
    private DBDao dbDao;
    private List<Bean.DataBean> dataBeans;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shou, container, false);
        //初始化控件
        mXlistView = view.findViewById(R.id.xlistview);
        mLinearLayout = view.findViewById(R.id.main);
        mTextView = view.findViewById(R.id.other);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        switch (id){
            case 0:
                mLinearLayout.setVerticalGravity(View.VISIBLE);
                mTextView.setVisibility(View.GONE);
                //设置下拉
                mXlistView.setPullLoadEnable(true);
                //设置适配器
                adapter = new XlistBaseAdapter(getActivity());
                mXlistView.setAdapter(adapter);
                dbDao = new DBDao(getActivity());
                boolean connected= NetWorkUtil.isConnected(getActivity());
                if (connected){
                    doHttp(1);
                    //设置上下拉监听
                    mXlistView.setXListViewListener(new XListView.IXListViewListener() {
                        @Override
                        public void onRefresh() {
                            page=1;
                            list.clear();
                            doHttp(1);
                        }

                        @Override
                        public void onLoadMore() {
                            page++;
                            doHttp(page);
                        }
                    });

                }else{
                    String data = dbDao.query();
                    Bean movieBean= new Gson().fromJson(data,Bean.class);
                    dataBeans = movieBean.getData();
                    list.addAll(dataBeans);
                    adapter.setList(list);
                }
                break;
            default:
                mLinearLayout.setVerticalGravity(View.VISIBLE);
                mTextView.setVisibility(View.GONE);
                break;
        }


    }

    private void doHttp(int page) {
        new HttpUtils().doGet("http://www.xieast.com/api/news/news.php?page="+page+"").result(new HttpUtils.HttpListener() {
            private List<Bean.DataBean> dataBeans;
            @Override
            public void success(String data) {
                dbDao.add(data);
                Bean bean = new Gson().fromJson(data, Bean.class);
                dataBeans = bean.getData();
                list.addAll(dataBeans);
                adapter.setList(list);
                mXlistView.stopRefresh();
                mXlistView.stopLoadMore();
                mXlistView.setRefreshTime("刚刚");

            }
        });
    }
}
