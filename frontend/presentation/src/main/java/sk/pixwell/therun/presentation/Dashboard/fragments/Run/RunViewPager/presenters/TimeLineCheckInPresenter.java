package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.Date;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.CheckInOnStart;
import sk.pixwell.therun.data.executor.EndRun;
import sk.pixwell.therun.data.executor.StartRun;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineCheckInView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TimeLineCheckInPresenter implements Presenter<TimeLineCheckInView> {

    private TimeLineCheckInView view;
    private Context context;
    private CheckInOnStart checkInOnStart;
    private StartRun startRun;
    private EndRun endRun;
    private String type;

    public TimeLineCheckInPresenter(Context context,  CheckInOnStart checkInOnStart, StartRun startRun, EndRun endRun) {
        this.context = context;
        this.startRun = startRun;
        this.checkInOnStart = checkInOnStart;
        this.endRun = endRun;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(@NonNull TimeLineCheckInView view) {
        this.view = view;
    }


    public void openCallDialog() {
        view.callDialog("+421 903 560 460");
    }

    public void checkIn(String type) {
        view.showProgress("Overujem Vašu účasť na preteku");
        checkInOnStart.execute(new CheckInPresenterSubscriber(), new Date(), type);
    }

    public void start(Boolean start, String type) {
        if(start) {
            view.showProgress("Štartujem pretek");
            this.type = type;
            startRun.execute(new StartRunSubscriber(), new Date(), type);
        }else
            view.showSnack("Odštartovanie nie je povolené. Počkajte na svoj štart.");
    }

    public void end(Boolean end, String type) {
        if(end) {
            endRun.execute(new EndRunSubscriber(), new Date(), type);
            view.showProgress("Ukončujem pretek");
        } else {
            view.showSnack("Nie ste ešte v cieli");
        }
    }

    @RxLogSubscriber
    private class CheckInPresenterSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
            view.dismissDialog();
        }

        @Override
        public void onError(Throwable e) {
            view.dismissDialog();
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            view.showSnack(e.getMessage());
            Timber.i("Result error: %s", e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            Timber.i("Result error: ");
            view.checkInOkey();
        }
    }

    @RxLogSubscriber
    private class StartRunSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
            view.dismissDialog();
        }

        @Override
        public void onError(Throwable e) {
            view.dismissDialog();
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            Timber.i("Result error: %s", e.getMessage());
            view.showProgress(e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            Timber.i("Result error: ");
            view.startOkey(type);
        }
    }

    @RxLogSubscriber
    private class EndRunSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
            view.dismissDialog();
        }

        @Override
        public void onError(Throwable e) {
            view.dismissDialog();
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            view.showSnack(e.getMessage());
            Timber.i("Result error: %s", e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            Timber.i("Result error: ");
            view.finishOkey();
        }
    }
}
