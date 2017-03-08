package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EngineActivity extends AppCompatActivity {

    public List<String> list_parent;
    public EngineListViewAdapter engine_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView enginelist_view;
    public List<String> motor_sorun1_list;
    public List<String> motor_sorun2_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_engine);
        enginelist_view = (ExpandableListView)findViewById(R.id.engineExpList);
        Hazırla(); // expandablelistview içeriğini hazırlamak için

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EngineActivity.this, IpuclariSayfasi.class));
            }
        });

        engine_adapter = new EngineListViewAdapter(getApplicationContext(), list_parent, list_child);
        enginelist_view.setAdapter(engine_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        enginelist_view.setClickable(true);

    }

    public void Hazırla()
    {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Motor çalışmıyor?");  // ilk başlığı giriyoruz
        list_parent.add("Motor çalışıyor ama hareket etmiyor?");   // ikinci başlığı giriyoruz

        motor_sorun1_list = new ArrayList<String>();  // ilk başlık için alt elemanları tanımlıyoruz
        motor_sorun1_list.add("Motorunuz aşırı ısı sebebiyle zarar görmüş olabilir.");


        motor_sorun2_list = new ArrayList<String>(); // ikinci başlık için alt elemanları tanımlıyoruz
        motor_sorun2_list.add("Motorunuza giden kablolardan biri hasar görmüş olabilir.");


        list_child.put(list_parent.get(0),motor_sorun1_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), motor_sorun2_list); // ikinci başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz


    }

}
