package com.example.test.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.test.R;

/**
 * Created by 海飞 on 2016/4/9.
 * 点击关于按钮弹出的对话框
 */
public class AboutDialogFragment extends DialogFragment{
    private Button btnAboutsure;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        btnAboutsure = (Button) view.findViewById(R.id.btn_about_sure);
        btnAboutsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
