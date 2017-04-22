package com.example.yunus.ototakip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Yunus on 20.04.2017.
 */

public class ArabaAdapter extends ArrayAdapter<String> implements ValueEventListener{

    private Context context;
    private HashMap<String, Araba> arabalar;
    private DatabaseReference databaseReference;
    private String userId;

    private List<String> arabaPlakalari;

    public ArabaAdapter(@NonNull Context context,
                        @NonNull List<String> arabaPlakalar,
                        final DatabaseReference databaseReference,
                        final String userId) {
        super(context, 0, arabaPlakalar);
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
                                    .child(plaka).addValueEventListener(ArabaAdapter.this);
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
                                    .removeEventListener(ArabaAdapter.this);
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String plaka = getItem(position);
        Araba arabaBilgisi = arabalar.get(plaka);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_araba, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.plakaTextView.setText(arabaBilgisi.getEditTextPlaka());
        return convertView;
    }

    @Override
    public int getCount() {
        return arabalar.size();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
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

    private class ViewHolder{

        public TextView plakaTextView;

        public ViewHolder(View parentView){
            plakaTextView = (TextView) parentView.findViewById(R.id.plakaTextView);

        }
    }
}
