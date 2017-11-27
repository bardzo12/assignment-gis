package sk.pixwell.therun.presentation.Dashboard.fragments.Run.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetCheckInInfo;
import sk.pixwell.therun.domain.CheckIn;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.CurrentCountdownTimer;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.ReportsFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.ui.RunView;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesInfoSingleton;
import timber.log.Timber;

import static sk.pixwell.therun.presentation.shared.navigation.Navigator.RUNNER_MODE;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class RunPresenter implements Presenter<RunView> {

    private RunView view;
    private Context context;
    private FragmentStatePagerAdapter adapter;
    private RunnerPagerAdapter adapter1;
    private FansPagerAdapter adapter2;
    private int mode;
    private GetCheckInInfo getCheckInInfo;
    private Boolean firstTime = true;

    public RunPresenter(Context context, GetCheckInInfo getCheckInInfo) {
        this.context = context;
        this.getCheckInInfo = getCheckInInfo;
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
    public void setView(@NonNull RunView view) {
        this.view = view;
    }

    public void setViewPager(final ViewPager viewPager, final FragmentManager fragmentManager, int mode) {

        if(firstTime) {
            this.mode = mode;
            firstTime = false;
            if (mode == RUNNER_MODE) {
                RunnerPagerAdapter runnerPagerAdapter = new RunnerPagerAdapter(fragmentManager);
                adapter = runnerPagerAdapter;
                adapter1 = (RunnerPagerAdapter) adapter;
                viewPager.setAdapter(adapter);
                /*new Timer().scheduleAtFixedRate(new TimerTask() {

                                                    @Override
                                                    public void run() {
                                                        getCheckInInfo.execute(new CheckInPresenterSubscriber(fragmentManager, viewPager));
                                                    }

                                                },
                        0,
                        10000);*/
            } else {
                adapter = new FansPagerAdapter(fragmentManager);
                adapter2 = (FansPagerAdapter) adapter;
                viewPager.setAdapter(adapter);
            }
        }
    }

   /*public void callPermission(boolean b) {
        if(mode == RUNNER_MODE)
            adapter1.callPemission(b);
    }

    public void enterToEnd(int i) {
        if(adapter1 != null) {
            adapter1.enterToEnd(i);
        }
    }

    public void setEndORun(boolean endEast, boolean endWest) {
        if(adapter1 !=  null)
            adapter1.setEndORun(endEast, endWest);
    }

    public void setStartORun(boolean startEast, boolean startWest) {
        if(adapter1 !=  null)
            adapter1.setStartORun(startEast, startWest);
    }*/

    static class RunnerPagerAdapter extends FragmentStatePagerAdapter {

        //TODO toto treba prerobiť na checkInFragment
        private TimeLineFragment timeLineCheckInFragment;
        //private TimeLineFragment timeLineFragment;
        private List<CheckIn> checkInList;
        private CheckIn eastCheckIn = new CheckIn();
        private CheckIn westCheckIn = new CheckIn();
        Boolean eastChange = false;
        Boolean westChange = false;
        private RunView runView;


        public RunnerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0 ) {
                timeLineCheckInFragment = new TimeLineFragment().newInstance();
                return timeLineCheckInFragment;
            }
            else
                return new ReportsFragment().newInstance();
            /*else
                return new SupportFragment().newInstance();*/
        }

        /*public void callPemission(Boolean result){
            timeLineCheckInFragment.callPermission(result);
        }*/

        @Override
        public int getCount() {
            //TODO SUPPORT fragment
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "NÁSTENKA";
            else
                return  "HLÁSENIA";
            /*else
                return "PODPORA";*/
        }

        /*private void showNoActivateStartButton(Date date) {
            Timber.i("Mode: No active start button");
            CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
            timeLineCheckInFragment.setNoActiviteStartButton(timer);
        }

        private void showRunnerRun(Date date, String type) {
            Timber.i("Mode: Beží niekto");
            CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
            timeLineCheckInFragment.showRunnerRun(timer,type);
        }

        private void showActiviteStartButton(Date date, String type) {
            Timber.i("Mode: Activate start button");
            CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
            timeLineCheckInFragment.showActiviteStartButton(timer, type);
        }

        private void showActivateCheckIn(Date date, String type) {
            Timber.i("Mode: Activate check in");
            CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
            timeLineCheckInFragment.showActivateCheckIn(timer, type);
        }

        private void showInfo(Date date) {
            Timber.i("Mode: Show INFO");
            CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
            timeLineCheckInFragment.showInfo(timer);
        }

        public void setCheckIn(List<CheckIn> result) {
            this.checkInList = result;
            for(CheckIn data : result) {
                if (data.getCheckInType().equals("west")) {
                    westCheckIn = data;
                } else {
                    eastCheckIn = data;
                }
            }
            //control number of check in - 0: no check in, 1: east of west, 2: east and west
            if(checkInList.size() > 0){
                //control if check in change
                /*if(controlCheckIns(result)) {
                    if (checkInList.size() > 1) {
                        if(east.getFinishedAt() != null)
                            setOneCheckIn();
                        else


                    } else {
                        setOneCheckIn();
                    }
                }*/
                /* if(checkInList.size() == 1)
                    setOneCheckIn();
                else
                    setTwoCheckIn(eastCheckIn, westCheckIn);
            } else {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, 2017);
                cal.set(Calendar.MONTH, Calendar.JUNE);
                cal.set(Calendar.DAY_OF_MONTH, 2);
                cal.set(Calendar.HOUR, 9);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                Date date = cal.getTime();
                //TODO toto som mal odkomentované
                //showInfo(date);
            }

        }

        private void setTwoCheckIn(CheckIn east, CheckIn west) {
            Date date = new Date();
            if (east.getCheckAt() == null) {
                if (east.getPlanedAt().compareTo(date) < 0)
                    showActivateCheckIn(east.getPlannedStartAt(), east.getCheckInType());
                else
                    showInfo(east.getPlannedStartAt());
            } else {
                if (east.getStartedAt() == null) {
                    if (east.getPlannedStartAt().compareTo(date) < 0)
                        showActiviteStartButton(east.getPlannedStartAt(), east.getCheckInType());
                    else
                        showNoActivateStartButton(east.getPlannedStartAt());
                } else {
                    if(east.getFinishedAt() == null)
                        showRunnerRun(east.getPlannedStartAt(), east.getCheckInType());
                    else {

                        //west
                        if (west.getCheckAt() == null) {
                            if (west.getPlanedAt().compareTo(date) < 0)
                                showActivateCheckIn(west.getPlannedStartAt(), west.getCheckInType());
                            else
                                showInfo(west.getPlannedStartAt());
                        } else {
                            if (west.getStartedAt() == null) {
                                if (west.getPlannedStartAt().compareTo(date) < 0)
                                    showActiviteStartButton(west.getPlannedStartAt(), west.getCheckInType());
                                else
                                    showNoActivateStartButton(west.getPlannedStartAt());
                            } else {
                                if(west.getFinishedAt() == null)
                                    showRunnerRun(west.getPlannedStartAt(), west.getCheckInType());
                                else {
                                    showInfo(west.getPlannedStartAt());
                                }
                            }

                        }
                    }
                }

            }
        }

        private void setOneCheckIn() {
            Date date = new Date();
            if (checkInList.get(0).getCheckAt() == null) {
                if (checkInList.get(0).getPlanedAt().compareTo(date) < 0)
                    showActivateCheckIn(checkInList.get(0).getPlannedStartAt(), checkInList.get(0).getCheckInType());
                else
                    showInfo(checkInList.get(0).getPlannedStartAt());
            } else {
                if (checkInList.get(0).getStartedAt() == null) {
                    if (checkInList.get(0).getPlannedStartAt().compareTo(date) < 0)
                        showActiviteStartButton(checkInList.get(0).getPlannedStartAt(), checkInList.get(0).getCheckInType());
                    else
                        showNoActivateStartButton(checkInList.get(0).getPlannedStartAt());
                } else {
                    if(checkInList.get(0).getFinishedAt() == null)
                        showRunnerRun(checkInList.get(0).getPlannedStartAt(), checkInList.get(0).getCheckInType());
                    else
                        showInfo(checkInList.get(0).getPlannedStartAt());
                }

            }
        }*/

        private Boolean controlCheckIns(List<CheckIn> result) {
            eastChange = false;
            westChange = false;
            for(CheckIn checkIn: result){
                if(checkIn.getCheckInType().equals("east"))
                    if(eastCheckIn == null || !checkIn.toString().equals(eastCheckIn.toString())){
                        eastChange = true;
                        eastCheckIn = checkIn;
                    }else {
                        eastChange = false;
                    }
                else if(checkIn.getCheckInType().equals("west"))
                    if(westCheckIn == null || !checkIn.toString().equals(westCheckIn.toString())){
                        westChange = true;
                        westCheckIn = checkIn;
                    }else {
                        westChange = false;
                    }

            }
            return eastChange || westChange;
        }


        /*public void enterToEnd(int i) {
            if(timeLineCheckInFragment != null) {
                timeLineCheckInFragment.setEndRun(i);
            }
        }

        public void setEndORun(boolean endEast, boolean endWest) {
            if(timeLineCheckInFragment != null)
                timeLineCheckInFragment.setEndRun(endEast, endWest);
        }

        public void setStartORun(boolean startEast, boolean startWest) {
            if(timeLineCheckInFragment != null)
                timeLineCheckInFragment.setStartORun(startEast, startWest);
        }*/
    }


    static class FansPagerAdapter extends FragmentStatePagerAdapter {

        private TimeLineFragment timeLineCheckInFragment;

        public FansPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            timeLineCheckInFragment = new TimeLineFragment().newInstance();
            return timeLineCheckInFragment;

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "NÁSTENKA";
        }
    }

    /*@RxLogSubscriber
    private class CheckInPresenterSubscriber extends DefaultSubscriber<List<CheckIn>> {

        FragmentManager fragmentManager;
        ViewPager viewPager;
        RunnerPagerAdapter runnerPagerAdapter;


        public CheckInPresenterSubscriber(FragmentManager fragmentManager, ViewPager viewpager) {
            super();
            this.fragmentManager = fragmentManager;
            this.viewPager = viewpager;
            runnerPagerAdapter = new RunnerPagerAdapter(fragmentManager);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<CheckIn> result) {
            if(adapter1 == null){
                adapter = runnerPagerAdapter;
                adapter1 = (RunnerPagerAdapter) adapter;
                viewPager.setAdapter(adapter);
                adapter1.setCheckIn(result);
            }else {
                adapter1.setCheckIn(result);
            }
            Timber.i("Result error: ");
        }


    }
*/

    @RxLogSubscriber
    private class GetStagesInfoSubscriber extends DefaultSubscriber<List<StageInfo>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            Timber.i("Result error: %s", e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<StageInfo> result) {
            StagesInfoSingleton.getInstance().setmStages(result);
        }
    }

}
