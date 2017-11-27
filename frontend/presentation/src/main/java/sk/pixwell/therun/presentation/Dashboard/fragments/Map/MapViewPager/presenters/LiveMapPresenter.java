package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sk.pixwell.therun.data.exception.NetworkConnectionException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetCaste;
import sk.pixwell.therun.data.executor.GetTeams;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.LiveMapView;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LiveMapPresenter implements Presenter<LiveMapView> {


    private LiveMapView view;
    private Context context;
    private GetTeams getTeamsUseCase;
    private GetTrack getTrack;
    private GetCaste getCaste;

    public LiveMapPresenter(Context context, GetTeams getTeams, GetTrack getTrack, GetCaste getCaste){
        this.context = context;
        this.getTeamsUseCase = getTeams;
        this.getTrack = getTrack;
        this.getCaste = getCaste;
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
    public void setView(@NonNull LiveMapView view) {
        this.view = view;
    }

    public void initializeLayout() {
        //GET teams
        /*new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                getTeamsUseCase.execute(new TeamsSubscriber());
            }

        },
                0,
                60000);*/
    }

    public void getTrack() {
        getTrack.execute(new TrackSubscriber());
    }


    public void getCastlePolygon(Double lat, Double lng) {
        getCaste.execute(new CastlePolygonSubscriber(), 3000, lat, lng);
    }

    @RxLogSubscriber
    private class TeamsSubscriber extends DefaultSubscriber<List<Team>> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }else if (e instanceof NetworkConnectionException && e.getMessage().equals("No internet connection"))
                //view.networkConnection();
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<Team> result) {
            Timber.i("Result okey");
            view.setTeams(result);
        }


    }


    @RxLogSubscriber
    private class CastlePolygonSubscriber extends DefaultSubscriber<List<CastlePolygon>> {
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
        public void onNext(List<CastlePolygon> result) {
            Timber.i("Result");
            view.setCastlePolygons(result);
        }

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
