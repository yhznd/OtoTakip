package com.example.yunus.ototakip;

/**
 * Created by Yunus on 15.02.2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class CustomPageAdapter extends PagerAdapter {


    String bilgiler[] = {"34 YNS 54","54 E 2554"};
    String tipler[] = {"Muayene Tarihi","Sigorta Tarihi"};
    int yuzdeler[]={90,12};
    private Context mContext;
    public CustomPageAdapter(Context context)
    {
        mContext = context;
    }




    @Override
    public Object instantiateItem(ViewGroup collection, int position)
    {
        String modelObject = "    "+bilgiler[position] +"\n" + tipler[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(400,400);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        lp2.setMargins(0,46,0,0);
        relativeLayout.setLayoutParams(lp);
        TextView textView = new TextView(relativeLayout.getContext());
        textView.setText(modelObject);
        textView.setLayoutParams(lp2);
        relativeLayout.addView(textView);
        ArcProgress arcProgress=new ArcProgress(relativeLayout.getContext());
        arcProgress.setMax(365);
        arcProgress.setProgress(yuzdeler[position]);
        arcProgress.setBottomText("GÜN KALDI");
        arcProgress.setLayoutParams(lp);
        arcProgress.setSuffixText("");
        relativeLayout.addView(arcProgress);
        collection.addView(relativeLayout);
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return bilgiler.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;

    }
}