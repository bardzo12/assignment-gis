package sk.pixwell.therun.presentation.shared.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;


import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import javax.inject.Inject;

import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.data.executor.UploadSegmentTime;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Login_Email.ui.LoginEmailActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.ConnectivityReceiver;
import sk.pixwell.therun.presentation.shared.application.App;
import sk.pixwell.therun.presentation.shared.application.TheRunApplication;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;
import sk.pixwell.therun.presentation.shared.navigation.Navigator;
import sk.pixwell.therun.presentation.splash.ui.SplashActivity;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public abstract class TheRunActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    public Navigator navigator;

    CheckInStage checkInStage;
    UploadSegmentTime uploadSegmentTime;
    private final String googlePlay = "https://play.google.com/store/apps/details?id=sk.pixwell.therun";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getApplicationComponent() != null) {
            this.getApplicationComponent().inject(this);
        }

    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link TheRunApplicationComponent}
     */
    protected TheRunApplicationComponent getApplicationComponent() {
        return ((TheRunApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(TheRunActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBar(String message){
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snackbar = Snackbar
                .make(viewGroup, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    protected TheRunActivityModule getActivityModule() {
        return new TheRunActivityModule(this);
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //showSnackBar(String.valueOf(isConnected));
        if(isConnected) {
            uploadSegmentTime = new UploadSegmentTime(getApplicationComponent().repository(),getApplicationComponent().threadExecutor(), getApplicationComponent().postExecutionThread());
            uploadSegmentTime.execute(new CheckInSubscriber());
        }
            //checkInStage = new CheckInStage(getApplicationComponent().threadExecutor(), getApplicationComponent().postExecutionThread(), getApplicationComponent().repository());
        //checkInStage.execute(new CheckInSubscriber(), -8);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        App.getInstance().setConnectivityListener(this);
    }

    @RxLogSubscriber
    private class CheckInSubscriber extends DefaultSubscriber<Boolean> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            Timber.i("Result okey");
        }


    }

    public void onUpdateNeeded() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.TheRunDialogTheme)
                .setTitle("Dostupná nová verzia aplikácie")
                .setMessage("Prosím aktualizujte aplikáciu pre správne fungovanie.")
                .setPositiveButton("Aktualizuj",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(googlePlay);
                            }
                        }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

       /* Intent intent2 = new Intent(this, FakeActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);*/
    }

}
