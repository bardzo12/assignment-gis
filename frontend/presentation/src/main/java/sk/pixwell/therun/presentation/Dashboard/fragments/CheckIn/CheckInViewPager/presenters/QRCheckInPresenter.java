package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sk.pixwell.therun.data.exception.UnVisibleException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.data.executor.GetOfflineCheckIn;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.QRCheckInView;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class QRCheckInPresenter implements Presenter<QRCheckInView> {

    private QRCheckInView view;
    private Context context;
    private CheckInStage checkInStageUseCase;

    public QRCheckInPresenter(Context context, CheckInStage checkInStageUseCase) {
        this.context = context;
        this.checkInStageUseCase = checkInStageUseCase;
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
    public void setView(@NonNull QRCheckInView view) {
        this.view = view;
    }


    public void controlCameraPermission() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { 
            // Android M Permission check 
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { 
                final AlertDialog.Builder builder = new AlertDialog.Builder(context); 
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null); 
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {  
                    @Override 
                    public void onDismiss(DialogInterface dialog) {
                        //requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_COARSE_LOCATION); 
                    }  
                });
                builder.show(); 
            }
        }*/
    }

    public void showPhotoPermissionResult(boolean photoPermission) {
        if(photoPermission)
            view.photoPermissionGranted();
        else
            view.photoPermissionDenied();
    }

    public void checkInStage(StageInfo stage) {
        Date nowTime = Calendar.getInstance().getTime();
        stage.setTime(nowTime);
        checkInStageUseCase.execute(new CheckInSubscriber(), stage);
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
            }
            if(e instanceof UnVisibleException){
                switch (e.getMessage()){
                    case "Segment start is already set":
                        view.showSnack("Tento segment bol už odštartovaný");
                        break;
                    case  "Team checkin for section west does not exist":
                        view.showSnack("Tento segment nie je určený pre Váš tím");
                        break;
                    case "Team is not checked for this section":
                        view.showSnack("Najskôr je potrebné checknuť sa pred štartom pretekov");
                        break;
                    case  "The team has not yet started":
                        view.showSnack("Váš tím ešte neodštartoval");
                        break;
                    default:
                        view.showSnack("Nesprávny čas overenia");
                        break;
                }
            }
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            view.showSnack("Etapa bola overená");
        }


    }
}
