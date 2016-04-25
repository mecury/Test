package com.example.test.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.test.R;
import com.example.test.adapter.FuiAdapter;
import com.example.test.entity.HelpData;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 海飞 on 2016/4/6.
 */
public class FujinFragment extends Fragment implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    private ListView lvThinglist;
    private ArrayList<HelpData> mdata;
    private Context context;
    private FuiAdapter adapter;
    private HelpData[] nDatas;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int limitQuary = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmetn_fujin_second, container, false);
        lvThinglist = (ListView) view.findViewById(R.id.lv_thinglist);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_swipe);

        context = getActivity().getApplicationContext();
        lvThinglist.setOnItemClickListener(this);
        setList(limitQuary);

        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //点击事件的处理
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    public void setList(int limit) {
        BmobQuery<HelpData> query = new BmobQuery<HelpData>();
        query.setLimit(10);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(context, new FindListener<HelpData>() {
            @Override
            public void onSuccess(List<HelpData> list) {
                nDatas = new HelpData[list.size()];
                int i = 0;
                for (HelpData helpData : list) {
                    nDatas[i] = helpData;
                    i++;
                }
                mdata = new ArrayList<HelpData>();
                for (int j = list.size()-1; j >= 0; j--) {
                    mdata.add(nDatas[j]);
                }
                adapter = new FuiAdapter(mdata, context);
                lvThinglist.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        BmobQuery<HelpData> query = new BmobQuery<HelpData>();
        query.setLimit(10);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(context, new FindListener<HelpData>() {
            @Override
            public void onSuccess(List<HelpData> list) {
                nDatas = new HelpData[list.size()];
                int i = 0;
                for (HelpData helpData : list) {
                    nDatas[i] = helpData;
                    i++;
                }
                mdata = new ArrayList<HelpData>();
                for (int j = list.size() - 1; j >= 0; j--) {
                    mdata.add(nDatas[j]);
                }
                adapter = new FuiAdapter(mdata, context);
                lvThinglist.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }
}




















