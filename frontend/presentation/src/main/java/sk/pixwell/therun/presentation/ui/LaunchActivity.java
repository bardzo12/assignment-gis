package sk.pixwell.therun.presentation.ui;

import android.os.Bundle;

import com.google.android.gms.maps.MapsInitializer;

import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LaunchActivity extends TheRunActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigator.navigateToSplash(this);
        MapsInitializer.initialize(this);
        finish();
    }

}
