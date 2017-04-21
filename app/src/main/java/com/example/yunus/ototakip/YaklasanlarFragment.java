package com.example.yunus.ototakip;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itsronald.widget.ViewPagerIndicator;

import java.util.ArrayList;


public class YaklasanlarFragment extends Fragment {


    String userId;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    public ViewPager viewPager = null;
    PagerAdapter adapter;
    public  YaklasanlarFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_yaklasanlar_fragment, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid().toString();
        dref = FirebaseDatabase.getInstance().getReference();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        adapter=new CustomPageAdapter(getActivity(), new ArrayList<String>(),  dref, userId);
        viewPager.setAdapter(adapter);
        return view;
    }

}
