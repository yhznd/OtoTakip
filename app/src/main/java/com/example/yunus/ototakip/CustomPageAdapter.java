package com.example.yunus.ototakip;

/**
 * Created by Yunus on 15.02.2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class CustomPageAdapter extends PagerAdapter implements ValueEventListener {

    Context mContext;
    private Context context;
    private HashMap<String, Araba> arabalar;
    private DatabaseReference databaseReference;
    private String userId;
    private LayoutInflater inflater;
    private List<String> arabaPlakalari;


    public CustomPageAdapter(@NonNull Context context,
                        @NonNull List<String> arabaPlakalar,
                        final DatabaseReference databaseReference,
                        final String userId) {

        this.context = context;
        this.arabaPlakalari = arabaPlakalar;
        arabalar = new HashMap<>();
        this.databaseReference = databaseReference;
        this.userId = userId;
        databaseReference.child("Arabalar")
                .child(userId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String plaka = dataSnapshot.getKey();
                        if(arabalar.get(plaka) == null){
                            databaseReference.child("Arabalar")
                                    .child(userId)
                                    .child(plaka).addValueEventListener(CustomPageAdapter.this);
                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String plaka = dataSnapshot.getKey();
                        if(arabalar.get(plaka) != null){
                            arabaPlakalari.remove(plaka);
                            arabalar.remove(plaka);
                            databaseReference.child("Arabalar")
                                    .child(userId)
                                    .child(plaka)
                                    .removeEventListener(CustomPageAdapter.this);
                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        String plaka = arabaPlakalari.get(position);
        Araba arabaBilgisi = arabalar.get(plaka);
        TextView muayeneTarihiTextView;
        TextView emisyonTarihiTextView;
        TextView sigortaTarihiTextView;
        TextView kaskoTarihiTextView;

        View viewLayout = LayoutInflater.from(context).inflate(R.layout.list_item_custom, container, false);
        muayeneTarihiTextView = (TextView) viewLayout.findViewById(R.id.muayeneTarihiTextView);
        kaskoTarihiTextView = (TextView) viewLayout.findViewById(R.id.kaskoTarihiTextView);
        emisyonTarihiTextView = (TextView) viewLayout.findViewById(R.id.emisyonTarihiTextView);
        sigortaTarihiTextView = (TextView) viewLayout.findViewById(R.id.sigortaTarihiTextView);

        muayeneTarihiTextView.setText(arabaBilgisi.getEditTextEmisyonTarihi());
        kaskoTarihiTextView.setText(arabaBilgisi.getEditTextKaskoTarihi());
        sigortaTarihiTextView.setText(arabaBilgisi.getEditTextSigortaTarihi());
        emisyonTarihiTextView.setText(arabaBilgisi.getEditTextEmisyonTarihi());
        container.addView(viewLayout);
        return viewLayout;
    }



    @Override
    public int getCount() {
        return arabalar.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot)
    {
        Araba araba = dataSnapshot.getValue(Araba.class);
        String plaka = dataSnapshot.getKey();
        araba.setEditTextPlaka(plaka);
        arabalar.put(plaka, araba);
        if(!arabaPlakalari.contains(plaka)){
            arabaPlakalari.add(plaka);
        }

        notifyDataSetChanged();
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }



}