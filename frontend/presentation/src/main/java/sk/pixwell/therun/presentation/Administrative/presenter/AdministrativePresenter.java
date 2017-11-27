package sk.pixwell.therun.presentation.Administrative.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import sk.pixwell.therun.data.executor.GetKraje;
import sk.pixwell.therun.data.executor.GetOkresy;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Administrative.ui.AdministrativeView;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class AdministrativePresenter implements Presenter<AdministrativeView> {

    private AdministrativeView view;
    private GetKraje getKraje;
    private GetOkresy getOkresy;

    public AdministrativePresenter(GetKraje getKraje, GetOkresy getOkresy){
        this.getKraje = getKraje;
        this.getOkresy = getOkresy;
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
    public void setView(@NonNull AdministrativeView view) {
        this.view = view;
    }

    public void getKraje(Stage stage) {
        getKraje.execute(new GetKrajeSubscriber(), stage);
    }

    public void getOkresy(Stage stage) {
        getOkresy.execute(new GetOkresySubscriber(), stage);
    }

    @RxLogSubscriber
    private class GetOkresySubscriber extends DefaultSubscriber<List<String>> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<String> result) {
            view.setOkresy(result);
        }
    }

    @RxLogSubscriber
    private class GetKrajeSubscriber extends DefaultSubscriber<List<String>> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<String> result) {
            view.setKraje(result);
        }
    }
}
