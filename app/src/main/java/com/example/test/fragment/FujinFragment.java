package com.example.test.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.adapter.FuiAdapter;
import com.example.test.entity.HelpData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 海飞 on 2016/4/6.
 */
public class FujinFragment extends Fragment implements AdapterView.OnItemClickListener{

    @Bind(R.id.lv_thinglist)
    ListView lvThinglist;
    private ArrayList<HelpData> mdata;
    private Context context;
    private FuiAdapter adapter;
    private HelpData[] nDatas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmetn_fujin_second, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        lvThinglist.setOnItemClickListener(this);
        BmobQuery<HelpData> query = new BmobQuery<HelpData>();
        query.setLimit(10);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(context, new FindListener<HelpData>() {
            @Override
            public void onSuccess(List<HelpData> list) {
                nDatas = new HelpData[list.size()];
                int i = 0;
                for (HelpData helpData:list) {
                    nDatas[i] = helpData;
                    i++;
                }
                mdata = new ArrayList<HelpData>();
                for (int j=0;j<list.size();j++){
                    mdata.add(nDatas[j]);
                }
                adapter = new FuiAdapter(mdata,context);
                lvThinglist.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "你点击了第"+position+"项", Toast.LENGTH_SHORT).show();
    }
}




















