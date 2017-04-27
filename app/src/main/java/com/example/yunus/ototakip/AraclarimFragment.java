package com.example.yunus.ototakip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AraclarimFragment extends Fragment {

    String userId;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref;
    ListView listview;
    ArrayList<Araba> list=new ArrayList<>();
    ArrayAdapter<Araba> adapter;
    public AraclarimFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_araclarim_fragment, container, false);

        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid().toString();
        listview=(ListView)view.findViewById(R.id.listview);
        adapter=new ArrayAdapter<Araba>(getActivity(),R.layout.support_simple_spinner_dropdown_item,list);
        listview.setAdapter(adapter);

       dref= FirebaseDatabase.getInstance().getReference().child("Arabalar").child(userId);


        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Araba araba=dataSnapshot.getValue(Araba.class);
                araba.setEditTextPlaka(dataSnapshot.getKey());
                list.add(araba);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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