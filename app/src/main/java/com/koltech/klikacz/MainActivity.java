package com.koltech.klikacz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    TextView licznik;

    int licznikklikniec;
    SharedPreferences save;

    private AdView adView;
    private FrameLayout adContainerView;
    String AD_UNIT_ID= "ca-app-pub-4834003578511022/1979993717";


    private static long back_pressed;

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

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        // MobileAds.setRequestConfiguration(
        //        new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345")).build());




        adContainerView = findViewById(R.id.adFrame);

        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });







        save = PreferenceManager.getDefaultSharedPreferences(this);
        licznikklikniec = save.getInt("liczba",0);

        licznik = (TextView) findViewById(R.id.licznik);
        licznik.setText(Integer.toString(licznikklikniec));

    }


    public void klik(View v){
         //o a jednak dzialla
        licznikklikniec++;

        SharedPreferences.Editor myEditor = save.edit();
        myEditor.putInt("liczba",licznikklikniec);
        myEditor.commit();
        licznik.setText(Integer.toString(licznikklikniec));



    }
    public  void  resetuj(View v){
        //resssset
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

        adContainerView = findViewById(R.id.adFrame);

        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });

        if (back_pressed + 4000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), R.string.pop_up_zamykanie, Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();



        save = PreferenceManager.getDefaultSharedPreferences(this);
        licznikklikniec = save.getInt("liczba",0);

        licznik = (TextView) findViewById(R.id.licznik);
        licznik.setText(Integer.toString(licznikklikniec));


    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void loadBanner() {
        // Create an ad request.
        adView = new AdView(this);
        adView.setAdUnitId(AD_UNIT_ID);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }


}
