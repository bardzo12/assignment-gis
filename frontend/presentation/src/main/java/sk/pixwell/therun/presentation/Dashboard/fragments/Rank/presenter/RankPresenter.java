package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.FavoriteListFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.TeamListFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankView;
import sk.pixwell.therun.presentation.Presenter;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class RankPresenter implements Presenter<RankView> {

    private RankView view;
    private Context context;
    private RankPagerAdapter adapter;

    public RankPresenter(Context context) {
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
    public void setView(@NonNull RankView view) {
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
        adapter.addFragment(new TeamListFragment().newInstance(), "TÍMY");
        adapter.addFragment(new RunnersListFragment().newInstance(), "JEDNOTLIVCI");
        adapter.addFragment(new FavoriteListFragment().newInstance(), "OBĽÚBENÍ");
        viewPager.setAdapter(adapter);*/
        adapter = new RankPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
    }

    public void changeFavoriteInTeamsFragment() {
        adapter.changeFavoriteInTeamsFragment();

    }

    public void changeFavoriteInFavoriteFragment() {
        adapter.changeFavoriteInFavoriteFragment();
    }

    static class RankPagerAdapter extends FragmentStatePagerAdapter {

        private TeamListFragment teamListFragment;
        private FavoriteListFragment favoriteListFragment;

        public RankPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0 ) {
                teamListFragment = new TeamListFragment().newInstance();
                return teamListFragment;
            }else {
                favoriteListFragment =  new FavoriteListFragment().newInstance();
                return favoriteListFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "TÍMY";
            else
                return "OBĽÚBENÍ";
        }

        public void changeFavoriteInTeamsFragment() {
            favoriteListFragment.initializeLayout();
        }

        public void changeFavoriteInFavoriteFragment() {
            teamListFragment.initializeLayout();
        }
    }

}
