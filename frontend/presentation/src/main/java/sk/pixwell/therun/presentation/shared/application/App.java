package sk.pixwell.therun.presentation.shared.application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import sk.pixwell.therun.presentation.shared.ConnectivityReceiver;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class App extends TheRunApplication{

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
