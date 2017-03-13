package com.example.yunus.ototakip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    public TextView tarih;
    final String PREFS_NAME = "MyPrefsFile";
    final String SHAREDPREF_DATE = "SharedPrefDate";

    private FirebaseAuth firebaseAuth;

    private TextView textViewUseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);


        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,Giris.class));
        }






        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences dateShared = getSharedPreferences(SHAREDPREF_DATE, 0);
        SharedPreferences.Editor dateEditor  = dateShared.edit();
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");

        if (settings.getBoolean("ilk_calisma_zamani", true)) {
            Log.d("Comments", "İlk çalışma");
            Calendar cal = Calendar.getInstance();
            dateEditor.putString("dateVal",dfDate.format(cal.getTime()));
            dateEditor.commit();
            settings.edit().putBoolean("ilk_calisma_zamani", false).commit();
        }

        AppRating.app_launched(this);

        tarih = (TextView) findViewById(R.id.tarihText);
        tarih.setText("Bugün - "+sistemTarihiniGetir());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        fragmentManager = getSupportFragmentManager();
        fragment = new AraclarimFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bb_menu_arac:
                                fragment = new AraclarimFragment();
                                break;
                            case R.id.bb_menu_yaklasan:
                                fragment = new YaklasanlarFragment();
                                break;
                            case R.id.bb_menu_yakin:
                                fragment = new Yakinimdakiler();
                                break;

                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }


                });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Navigation itemlerinin click olaylarını burada yapıyoruz.
        int id = item.getItemId();
        if (id == R.id.nav_kullanici)
        {
            startActivity(new Intent(MainActivity.this,Hesabim.class));

        }
        else if (id == R.id.nav_hatirlatma)
        {
            startActivity(new Intent(MainActivity.this,AyarlarActivity.class));
        }
        else if (id == R.id.nav_ipucu)
        {
            startActivity(new Intent(MainActivity.this,IpuclariSayfasi.class));
        }


        else if (id == R.id.nav_oyla)
        {


        }
        else if (id == R.id.nav_cikis)
        {
                //startActivity(new Intent(MainActivity.this,GirisActivity.class));
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, Giris.class));
            finish();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

    }

    public String sistemTarihiniGetir()
    {
        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy E", new Locale("tr"));
        return sdf.format(takvim.getTime());
    }
}
