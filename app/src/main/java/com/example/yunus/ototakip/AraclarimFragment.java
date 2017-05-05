package com.example.yunus.ototakip;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AraclarimFragment extends Fragment {

    public String userId;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference dref;
    public ListView listview;
    public Dialog progressDialog;
    public ImageView bosArac;
    public ArrayList<String> list=new ArrayList<>();
    public ArrayAdapter<String> adapter;
    public AraclarimFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_araclarim_fragment, container, false);
        progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        firebaseAuth=FirebaseAuth.getInstance();
        listview=(ListView)view.findViewById(R.id.listview);
        userId=firebaseAuth.getCurrentUser().getUid().toString();
        bosArac= (ImageView) view.findViewById(R.id.emptyData);
        dref = FirebaseDatabase.getInstance().getReference();
        adapter=new ArabaAdapter(getActivity(), new ArrayList<String>(),  dref, userId);
        progressDialog.show();
        listview.setAdapter(adapter);
        progressDialog.dismiss();
        listview.setEmptyView(bosArac);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                String gelenPlaka= (String) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity(), AracListele.class);
                intent.putExtra("aracPlaka",gelenPlaka);
                Log.d("Yolla",gelenPlaka);
                startActivity(intent);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AracEkle.class));
            }
        });

        listview.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return view;

    }
}