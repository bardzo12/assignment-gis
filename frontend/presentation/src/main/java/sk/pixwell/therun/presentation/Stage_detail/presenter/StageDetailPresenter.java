package sk.pixwell.therun.presentation.Stage_detail.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters.StagesPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.LiveMapView;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetailView;
import sk.pixwell.therun.presentation.StagesSingleton;
import sk.pixwell.therun.presentation.splash.presenter.SplashPresenter;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageDetailPresenter implements Presenter<StageDetailView> {


    private StageDetailView view;
    private Context context;
    private GetTrack getTrack;

    public StageDetailPresenter(Context context, GetTrack getTrack){
        this.context = context;
        this.getTrack = getTrack;
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
    public void setView(@NonNull StageDetailView view) {
        this.view = view;
    }

    public void initializeLayout() {

    }

    public void getPoints() {
        getTrack.execute(new TrackSubscriber());
    }

    public void onAdministrativeClick() {
        this.view.navigateToAdministrative();
    }

    public void onUseLandClick() {
        this.view.navigateToUseLand();
    }

    @RxLogSubscriber
    private class TrackSubscriber extends DefaultSubscriber<List<PointOfStage>> {
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
        public void onNext(List<PointOfStage> result) {
            PointsOfStageSingleton.getInstance().setPoints(result);
            view.setTrack(result);
        }
    }

}
