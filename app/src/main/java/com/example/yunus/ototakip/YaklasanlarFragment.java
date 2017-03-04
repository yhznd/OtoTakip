package com.example.yunus.ototakip;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.itsronald.widget.ViewPagerIndicator;

import org.w3c.dom.Text;


public class YaklasanlarFragment extends Fragment {


    public ViewPager viewPager = null;
    public ViewPagerIndicator indicator;
    public  YaklasanlarFragment(){}
    CustomPageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_yaklasanlar_fragment, container, false);
       viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomPageAdapter(getContext()));
        return view;
    }

}
