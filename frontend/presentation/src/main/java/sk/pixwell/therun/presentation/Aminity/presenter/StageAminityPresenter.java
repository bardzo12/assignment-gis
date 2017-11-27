package sk.pixwell.therun.presentation.Aminity.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import sk.pixwell.therun.data.executor.GetAmenity;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.AmenityPoint;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Aminity.ui.StageAminityView;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageAminityPresenter implements Presenter<StageAminityView> {


    private StageAminityView view;
    private GetTrack getTrack;
    private GetAmenity getAmenity;

    public StageAminityPresenter(GetTrack getTrack, GetAmenity getAmenity){
        this.getTrack = getTrack;
        this.getAmenity = getAmenity;
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
    public void setView(@NonNull StageAminityView view) {
        this.view = view;}

    public void initializeLayout() {

    }

    public void getPoints() {
        getTrack.execute(new TrackSubscriber());
    }

    public void find(boolean mClinic, boolean mDentist, boolean mDoctor, boolean mHospital, boolean mPharmacy, int progress) {
        getAmenity.execute(new AmenityPointSubscriber(), mClinic, mDentist, mDoctor, mHospital, mPharmacy, progress, view.getStage());
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

    @RxLogSubscriber
    private class AmenityPointSubscriber extends DefaultSubscriber<List<AmenityPoint>> {
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
        public void onNext(List<AmenityPoint> result) {
            Timber.i(result.toString());
            view.setResult(result);
        }
    }

}
