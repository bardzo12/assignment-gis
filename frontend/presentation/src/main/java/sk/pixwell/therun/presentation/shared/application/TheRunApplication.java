package sk.pixwell.therun.presentation.shared.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.common.GoogleApiAvailability;
import com.shamanland.fonticon.FontIconTypefaceHolder;

import sk.pixwell.therun.data.BuildConfig;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class TheRunApplication extends MultiDexApplication {

    private TheRunApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
       //Fabric.with(this, new Crashlytics());
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        FontIconTypefaceHolder.init(getAssets(), "icons.ttf");
        this.initializeInjector();
        this.initializeTimber();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerTheRunApplicationComponent.builder()
                .theRunApplicationModule(new TheRunApplicationModule(this))
                .build();
    }

    public TheRunApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
