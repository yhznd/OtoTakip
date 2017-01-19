package com.example.yunus.ototakip;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class YaklasanlarFragment extends Fragment {

    CircularProgressBar c3;
    public  YaklasanlarFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_yaklasanlar_fragment, container, false);
        c3 = (CircularProgressBar) view.findViewById(R.id.circularprogressbar1);
        c3.setTitle("45");
        c3.setSubTitle("GÜN KALDI");
        c3.setProgress(45);
        return view;
    }
}
