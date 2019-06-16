package com.koltech.klikacz;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView licznik;

    int licznikklikniec;
    SharedPreferences save;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.setings);
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        save = PreferenceManager.getDefaultSharedPreferences(this);
        licznikklikniec = save.getInt("liczba",0);

        licznik = (TextView) findViewById(R.id.licznik);
        licznik.setText(Integer.toString(licznikklikniec));

    }


    public void klik(View v){

        licznikklikniec++;

        SharedPreferences.Editor myEditor = save.edit();
        myEditor.putInt("liczba",licznikklikniec);
        myEditor.commit();
        licznik.setText(Integer.toString(licznikklikniec));



    }
    public  void  resetuj(View v){
        SharedPreferences.Editor myEditor = save.edit();
        myEditor.putInt("liczba",0);
        myEditor.commit();
    }


   //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
   //           .setAction("Action", null).show();

    @Override
    public void onBackPressed() {

        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.setings);
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        save = PreferenceManager.getDefaultSharedPreferences(this);
        licznikklikniec = save.getInt("liczba",0);

        licznik = (TextView) findViewById(R.id.licznik);
        licznik.setText(Integer.toString(licznikklikniec));


    }
}
