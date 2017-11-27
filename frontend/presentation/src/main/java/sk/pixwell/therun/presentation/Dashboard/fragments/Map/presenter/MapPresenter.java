package sk.pixwell.therun.presentation.Dashboard.fragments.Map.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.LiveMapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.StagesFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.ui.MapView;
import sk.pixwell.therun.presentation.Presenter;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class MapPresenter implements Presenter<MapView> {

    private MapView view;
    private Context context;
    private DesignDemoPagerAdapter adapter;

    public MapPresenter(Context context) {
        this.context = context;
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
    public void setView(@NonNull MapView view) {
        this.view = view;
    }

    public void setViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        /*FragmentTransaction ft = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentManager.getFragments()){
            if(!(fragment instanceof MapFragment))
                ft.remove(fragment);

        }

        ft.commit();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        adapter.addFragment(new LiveMapFragment(), "LIVE POZÍCIA");
        adapter.addFragment(new iBeaconCheckInFragment(), "Ďalši");
        adapter.addFragment(new VerifiedFragment(), "Ďalší");
        viewPager.setAdapter(adapter);*/
        adapter = new DesignDemoPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
    }

    public void callPermission(boolean b) {
        adapter.setLocationPermission(b);
    }

    /*public void afterDrawer() {
        adapter.afterDrawer();
    }*/

    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

        LiveMapFragment liveMapFragment;
        StagesFragment stagesFragment;

        public DesignDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if(position == 0 ) {
                liveMapFragment = new LiveMapFragment().newInstance();
                return liveMapFragment;
            }else{
                stagesFragment = new StagesFragment().newInstance();
                //stagesFragment.initializeLayout();
                return stagesFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "LIVE POZÍCIE";
            else
                return  "ETAPY";

        }

        public void setLocationPermission(boolean locationPermission) {
            liveMapFragment.setLocationPermission(locationPermission);
        }

        public void initializeLayout(int position) {
            switch (position){
                case 0:
                    liveMapFragment.initializeLayout();
                    break;
                case 1:
                    stagesFragment.initializeLayout();
                    break;
            }
        }

        /*public void afterDrawer() {
            liveMapFragment.afterDrawer();
        }*/
    }
}
