package com.example.test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.test.fragment.FujinFragment;
import com.example.test.fragment.MainFragment;
import com.example.test.fragment.PersonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.rbtn_main) Button rbtn_main;
    @Bind(R.id.rbtn_fujin) Button rbtn_fujin;
    @Bind(R.id.rbtn_person) Button rbtn_person;

    private MainFragment mainFragment;
    private FujinFragment fujinFragment;
    private PersonFragment personFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        rbtn_main.setOnClickListener(this);
        rbtn_fujin.setOnClickListener(this);
        rbtn_person.setOnClickListener(this);
        select(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbtn_main:
                select(0);
                break;
            case R.id.rbtn_fujin:
                select(1);
                break;
            case R.id.rbtn_person:
                select(2);
                break;
        }
    }

    private void select(int i){
        FragmentManager fm = getFragmentManager();  //获取事务管理器
        FragmentTransaction fragmentTransaction = fm.beginTransaction();    //开启一个事务

        hideFragment(fragmentTransaction);

        switch (i){
            case 0:
                if (mainFragment == null){
                    mainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.fragment_container, mainFragment);
                }else{
                    fragmentTransaction.show(mainFragment);
                }
                break;
            case 1:
                if (fujinFragment == null){
                    fujinFragment = new FujinFragment();
                    fragmentTransaction.add(R.id.fragment_container, fujinFragment);
                }else{
                    fragmentTransaction.show(fujinFragment);
                }
                break;
            case 2:
                if (personFragment == null){
                    personFragment = new PersonFragment();
                    fragmentTransaction.add(R.id.fragment_container, personFragment);
                }else{
                    fragmentTransaction.show(personFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    public void hideFragment(FragmentTransaction fragmentTransaction){
        if (mainFragment != null){
            fragmentTransaction.hide(mainFragment);
        }
        if (fujinFragment != null){
            fragmentTransaction.hide(fujinFragment);
        }
        if (personFragment != null){
            fragmentTransaction.hide(personFragment);
        }
    }
}
