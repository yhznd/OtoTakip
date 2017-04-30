package com.example.yunus.ototakip;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

    String userId;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref;
    ListView listview;
    ImageView bosArac;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    public AraclarimFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_araclarim_fragment, container, false);
        View listViewPlaka = inflater.inflate(R.layout.list_item_araba, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        listview=(ListView)view.findViewById(R.id.listview);
        userId=firebaseAuth.getCurrentUser().getUid().toString();
        bosArac= (ImageView) view.findViewById(R.id.emptyData);
        dref = FirebaseDatabase.getInstance().getReference();
        adapter=new ArabaAdapter(getActivity(), new ArrayList<String>(),  dref, userId);
        listview.setEmptyView(bosArac);
        listview.setAdapter(adapter);
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

        return view;

    }
}