package sk.pixwell.therun.presentation.LandUse.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import sk.pixwell.therun.data.executor.GetKraje;
import sk.pixwell.therun.data.executor.GetLandUse;
import sk.pixwell.therun.data.executor.GetOkresy;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Administrative.ui.AdministrativeView;
import sk.pixwell.therun.presentation.LandUse.ui.LandUseView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class LandUsePresenter implements Presenter<LandUseView> {

    private LandUseView view;
    private GetLandUse getLandUse;

    public LandUsePresenter(GetLandUse getLandUse){
        this.getLandUse = getLandUse;
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
    public void setView(@NonNull LandUseView view) {
        this.view = view;
        getLandUse.execute(new GetLandUseSubscriber(), view.getStage());
    }

    @RxLogSubscriber
    private class GetLandUseSubscriber extends DefaultSubscriber<List<Landuse>> {
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
        public void onNext(List<Landuse> result) {
            view.setChart(result);
        }
    }
}
