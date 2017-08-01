package com.bb.superdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/22 0022.
 * 商家信息详情
 */

public class ModelFragment extends Fragment {
    @Bind(R.id.textView)
    TextView textView;

    private View mView;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, null);
        ButterKnife.bind(this, mView);

        textView.setText(text);
        return mView;
    }

    public ModelFragment(String text){
        this.text = text;
    }

}
