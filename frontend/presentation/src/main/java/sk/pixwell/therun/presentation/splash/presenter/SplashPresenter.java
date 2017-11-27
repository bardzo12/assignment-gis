package sk.pixwell.therun.presentation.splash.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesSingleton;
import sk.pixwell.therun.presentation.splash.ui.SplashView;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
public class SplashPresenter implements Presenter<SplashView> {

    private SplashView splashView;
    private GetStages getStagesUseCase;
    private GetTrack getTrack;
    private Context context;
    private Boolean stagesOK = false;
    private Boolean trackOK = false;
    private GetMeInfo getMeInfo;
    
    @Inject
    public SplashPresenter(Context context, GetStages getStagesUseCase, GetTrack getTrack, GetMeInfo getMeInfo) {
        this.getStagesUseCase = getStagesUseCase;
        this.getTrack = getTrack;
        this.context = context;
        this.getMeInfo = getMeInfo;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.splashView = null;
    }

    public void setView(@NonNull SplashView view) {
        this.splashView = view;
        if(StagesSingleton.getInstance().getStages().size() == 0)
            getStagesUseCase.execute(new StagesSubscriber());
        if(PointsOfStageSingleton.getInstance().getPoints().size() == 0)
            getTrack.execute(new TrackSubscriber());
    }

    public void initialize()  {
    }

    public void controlToken() {
        getMeInfo.execute(new Subscriber() {
            @Override
            public void onCompleted() {
                Timber.d("Compelete delete");
            }

            @Override
            public void onError(Throwable e) {
                splashView.navigateToDashboard();
            }

            @Override
            public void onNext(Object o) {
                splashView.navigateToDashboard((Runner) o);
            }
        });
    }

    @RxLogSubscriber
    private class StagesSubscriber extends DefaultSubscriber<List<Stage>> {
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
        public void onNext(List<Stage> result) {
            StagesSingleton.getInstance().setmStages(result);
            stagesOK = true;
            if(trackOK)
                splashView.finishSplash();
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
            trackOK = true;
            if(stagesOK)
                splashView.finishSplash();
        }

    }

}
