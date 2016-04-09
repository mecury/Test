package com.example.test.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.activity.LoginActivity;
import com.example.test.entity.MyUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 海飞 on 2016/4/6.
 */
public class PersonFragment extends Fragment {


    @Bind(R.id.tv_set_phone)
    TextView tvSetPhone;
    @Bind(R.id.tv_set_nickname)
    TextView tvSetNickname;
    @Bind(R.id.tv_set_sex)
    TextView tvSetSex;
    @Bind(R.id.tv_set_location)
    TextView tvSetLocation;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    @Bind(R.id.btn_about)
    Button btnAbout;

    private String userphone;   //注册的手机号
    private String nickname;    //昵称
    private String location;    //所在城市
    private String sex; //性别
    private MyUser user;

    private Context context;
    private DialogFragment frag;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_three, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        user = new MyUser();

        Intent intent = getActivity().getIntent();
        userphone = intent.getStringExtra("userphone");

        tvSetPhone.setText(userphone);

        initData();

        //注销登陆事件的点击
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.logOut(context);
                BmobQuery.clearAllCachedResults(context);
                MyUser userInfo = BmobUser.getCurrentUser(context, MyUser.class);
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
            }
        });
        //关于按钮的点击
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialogFragment aboutDialog = new AboutDialogFragment();
                aboutDialog.show(getFragmentManager(),"aboutDialog");
            }
        });
        return view;
    }
    /*
    当view视图被销毁的时候调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initData(){
        Log.e("text", "initData方法");
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();

        //查询是否有缓存
        boolean isInCache = query.hasCachedResult(context, MyUser.class);

        if (isInCache){
            query.findObjects(context, new FindListener<MyUser>() {
                @Override
                public void onSuccess(List<MyUser> list) {
                    for (MyUser myUser : list ) {
                        tvSetNickname.setText(myUser.getNickName());
                        Log.e("nickName", myUser.getNickName());
                        if (myUser.getSex()){
                            tvSetSex.setText("男");
                        } else {
                            tvSetSex.setText("女");
                        }
                        tvSetLocation.setText(myUser.getLocation());
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("findOnError", "查找失败");
                }
            });
        } else {
            query.addWhereEqualTo("username", userphone);
            query.findObjects(context, new FindListener<MyUser>() {
                @Override
                public void onSuccess(List<MyUser> list) {
                    for (MyUser myUser : list ) {
                        tvSetNickname.setText(myUser.getNickName());
                        Log.e("nickName", myUser.getNickName());
                        if (myUser.getSex()){
                            tvSetSex.setText("男");
                        } else {
                            tvSetSex.setText("女");
                        }
                        tvSetLocation.setText(myUser.getLocation());
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("onError", "查询失败");
                }
            });
        }

    }
}
