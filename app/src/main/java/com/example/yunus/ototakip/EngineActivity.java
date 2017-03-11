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



    }



}
