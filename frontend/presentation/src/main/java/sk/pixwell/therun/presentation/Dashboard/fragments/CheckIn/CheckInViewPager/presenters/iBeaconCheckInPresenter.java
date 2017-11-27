package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.Calendar;
import java.util.Date;

import sk.pixwell.therun.data.exception.UnVisibleException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.iBeaconCheckInView;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class iBeaconCheckInPresenter implements Presenter<iBeaconCheckInView> {

    private iBeaconCheckInView view;
    private Context context;
    private CheckInStage checkInStage;

    public iBeaconCheckInPresenter(Context context, CheckInStage checkInStage) {
        this.context = context;
        this.checkInStage = checkInStage;
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
    public void setView(@NonNull iBeaconCheckInView view) {
        this.view = view;
    }


    public void checkInStage(StageInfo stage) {
        checkInStage.execute(new CheckInSubscriber(), stage);
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
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            } else if(e instanceof UnVisibleException){
                switch (e.getMessage()){
                    case "Segment start is already set":
                        view.showToast("Tento segment bol už odštartovaný");
                        break;
                    case  "Team checkin for section west does not exist":
                        view.showToast("Tento segment nie je určený pre Váš tím");
                        break;
                    case "Team is not checked for this section":
                        view.showToast("Najskôr je potrebné checknuť sa pred štartom pretekov");
                        break;
                    case  "The team has not yet started":
                        view.showToast("Váš tím ešte neodštartoval");
                        break;
                    default:
                        view.showToast("Nesprávny čas overenia");
                        break;
                }
            }
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            view.showToast("Etapa bola overená");
        }


    }
}
