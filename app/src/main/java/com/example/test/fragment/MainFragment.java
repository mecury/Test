package com.example.test.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.test.R;
import com.example.test.activity.queuehelpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 海飞 on 2016/4/6.
 */
public class MainFragment extends Fragment {


    @Bind(R.id.lin_queue)
    LinearLayout linQueue;
    @Bind(R.id.lin_ticket)
    LinearLayout linTicket;

    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_first, container, false);
        ButterKnife.bind(this, view);//findViewById
        context = getActivity().getApplicationContext();
        //帮忙排队
        linQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),queuehelpActivity.class);
                startActivity(intent);
            }
        });
        //帮忙取票
        linTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),queuehelpActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
