package sk.pixwell.therun.presentation.Dashboard.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import org.altbeacon.beacon.Beacon;

import java.util.List;

import rx.Subscriber;
import sk.pixwell.therun.data.DeleteToken;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardView;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesSingleton;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class DashboardPresenter implements Presenter<DashboardView> {

    private DashboardView dashboardView;
    private Boolean isBeacon = false;
    private Beacon beacon;
    private Context context;
    private DeleteToken deleteToken;;

    public DashboardPresenter(Context context, DeleteToken deleteToken) {
        this.context = context;
        this.deleteToken = deleteToken;
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
    public void setView(@NonNull DashboardView view) {
        dashboardView = view;
    }


    public void navigateToStageDetail(Stage postId) {
        dashboardView.navigateToDetail(postId);
    }

    public void noBeacon() {
        isBeacon = false;
        dashboardView.setViewBeacon(false, beacon);
    }

    public void setBeacon(Beacon beacon) {
        isBeacon = true;
        double distance = 0;
        if(this.beacon != null)
            distance = this.beacon.getDistance() - beacon.getDistance();
        this.beacon = beacon;
        dashboardView.setViewBeacon(true, beacon);
    }

    public Boolean checkInBeacon() {
        if(isBeacon)
            return true;
        else
            return false;

    }

    public void navigateToStageDetailDialog(Stage stage) {
        dashboardView.navigateToStageDetailDialog(stage);
    }

    public void logout() {
        dashboardView.navigateToWalkthrough();
    }

    public void clearToken() {
        deleteToken.execute(new Subscriber() {
            @Override
            public void onCompleted() {
                Timber.d("Compelete delete");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("Error delete");
            }

            @Override
            public void onNext(Object o) {
                Timber.d("Next delete");
            }
        });
    }

    public void logIn() {
        dashboardView.navigateToEmailLogin();
    }

    public void navigateToControlFragment() {

    }

    public void enterToEndEastEnd() {
        dashboardView.enterToEnd(1);
    }

    public void enterToEndWestEnd() {
        dashboardView.enterToEnd(2);
    }

    public void onClickStageAminity(Stage stage) {
        dashboardView.navigateToStageAminity(stage);
    }
}
