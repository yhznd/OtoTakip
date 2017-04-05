package com.example.yunus.ototakip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AidActivity extends AppCompatActivity {

    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aid);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AidActivity.this, IpuclariSayfasi.class));
            }
        });

        if (savedInstanceState == null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("file_name", "sample3.pdf");
            PdfRendererBasicFragment ff=new PdfRendererBasicFragment();
            ff.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ff, FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }
}
