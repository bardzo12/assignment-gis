package sk.pixwell.therun.presentation.shared.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.QRCheckInView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TheRunPresenter {

    private Context context;
    private CheckInStage checkInStageUseCase;

    public TheRunPresenter(Context context, CheckInStage checkInStageUseCase) {
        this.context = context;
        this.checkInStageUseCase = checkInStageUseCase;
    }

    public void checkInStage(int stage) {
        //checkInStageUseCase.execute(new CheckInSubscriber(), stage);
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
}
