package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;
import java.util.List;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.data.executor.GetStagesInfo;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.adapters.ViewPagerAdapter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.QRCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.VerifiedFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.iBeaconCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui.CheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui.CheckInView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.ui.MapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.ReportsFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.SupportFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.presenter.RunPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.ui.RunFragment;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardView;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesInfoSingleton;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInPresenter implements Presenter<CheckInView>{

    private CheckInView view;
    private Context context;
    DesignDemoPagerAdapter adapter;
    GetStagesInfo getStagesInfo;
    List<StageInfo> stageInfoList;

    public CheckInPresenter(Context context, GetStagesInfo getStagesInfo) {
        this.context = context;
        this.getStagesInfo = getStagesInfo;
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
    public void setView(@NonNull CheckInView view) {
        this.view = view;
    }


    public void setViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        /*FragmentTransaction ft = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentManager.getFragments()){
            if(!(fragment instanceof RunFragment) && !(fragment instanceof RankFragment) && !(fragment instanceof MapFragment) && !(fragment instanceof CheckInFragment))
                ft.remove(fragment);

        }

        ft.commit();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        adapter.addFragment(new QRCheckInFragment().newInstance(), "QR KÓD");
        adapter.addFragment(new iBeaconCheckInFragment().newInstance(), "iBEACON");
        adapter.addFragment(new VerifiedFragment().newInstance(), "OVERENÉ");
        viewPager.setAdapter(adapter);*/
        if(StagesInfoSingleton.getInstance().getStages().size() == 0)
            getStagesInfo.execute(new GetStagesInfoSubscriber());
        adapter = new DesignDemoPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
    }

    public void setPhotoPermission(boolean photoPermission) {
        adapter.setPhotoPermission(photoPermission);
    }

    public void pageSelected(int position) {
        adapter.initializeLayout(position);
    }

    public void setBluetoothPermission(boolean result) {
        adapter.setBluetoothPermission(result);
    }

    public void bluetoothOf() {
        adapter.setError();
    }

    public void bluetoothOn() {
        adapter.errorOf();
    }

    public void setViewBeacon(Boolean result, Beacon beacon) {
        if(result) {
            for (StageInfo data : StagesInfoSingleton.getInstance().getStages())
                if (data.getUuid().equalsIgnoreCase(beacon.getId1().toString()) &&
                        data.getMajor().equalsIgnoreCase(beacon.getId2().toString()) &&
                        data.getMinor().equalsIgnoreCase(beacon.getId3().toString()))
                    adapter.setViewBeacon(result, data);
        }else {
            adapter.setViewBeacon(false, null);
        }
    }

    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

        QRCheckInFragment qrCheckInfragment;
        iBeaconCheckInFragment iBeaconCheckInFragment;
        VerifiedFragment verifiedFragment;
        
        public DesignDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        
        @Override
        public Fragment getItem(int position) {
            if(position == 0 ) {
                qrCheckInfragment = new QRCheckInFragment();
                return qrCheckInfragment;
            }else if(position == 1) {
                iBeaconCheckInFragment = new iBeaconCheckInFragment().newInstance();
                return iBeaconCheckInFragment;
            }else {
                verifiedFragment = new VerifiedFragment().newInstance();
                return verifiedFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "QR KÓD";
            else if(position == 1)
                return  "iBEACON";
            else
                return "OVERENÉ";
        }

        public void setPhotoPermission(boolean photoPermission) {
            qrCheckInfragment.setPhotoPermission(photoPermission);
        }

        public void initializeLayout(int position) {
            switch (position){
                case 0:
                    //qrCheckInfragment.initializeLayout();
                    break;
                case 1:
                    if(iBeaconCheckInFragment != null)
                        iBeaconCheckInFragment.initializeLayout();
                    else{
                        iBeaconCheckInFragment = new iBeaconCheckInFragment().newInstance();
                        iBeaconCheckInFragment.initializeLayout();
                    }
                    break;
                case 2:
                    verifiedFragment.initializeLayout();
                    break;
            }
        }

        public void setBluetoothPermission(boolean result) {
            iBeaconCheckInFragment.setBluetoothPermission(result);
        }

        public void setError() {
            iBeaconCheckInFragment.setError();
        }

        public void errorOf() {
            iBeaconCheckInFragment.errorOf();
        }

        public void setViewBeacon(Boolean result, StageInfo stageInfo) {
            if(iBeaconCheckInFragment != null)
                iBeaconCheckInFragment.setViewBeacon(result, stageInfo);
        }
    }

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
