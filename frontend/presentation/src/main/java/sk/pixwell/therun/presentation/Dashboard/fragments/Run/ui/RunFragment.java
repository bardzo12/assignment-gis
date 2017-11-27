package sk.pixwell.therun.presentation.Dashboard.fragments.Run.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.presenter.RankPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.presenter.RunPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

import static sk.pixwell.therun.presentation.shared.navigation.Navigator.RUNNER_MODE;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class RunFragment extends TheRunFragment implements RunView {

    public static final String MODE = "MODE";

    @Inject
    RunPresenter mPresenter;

    @BindView(R.id.tabs3)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager3)
    ViewPager mViewPager;

    private int mode;

    public static RunFragment newInstance() {
        RunFragment fragment = new RunFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_run, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void setMode(int i){
        this.mode = i;
    }

    public void initializeLayout() {
        setupViewPager(mViewPager, mode);
        mTabLayout.setupWithViewPager(mViewPager);
    }



    private void setupViewPager(ViewPager viewPager, int mode) {
        mPresenter.setViewPager(viewPager, getFragmentManager(), mode);
    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void callPermission(boolean b) {
        //mPresenter.callPermission (b);
    }

    public void enterToEnd(int i) {
        //mPresenter.enterToEnd(i);
    }

    public void setEndORun(boolean endEast, boolean endWest) {
        /*if(mPresenter != null)
            mPresenter.setEndORun(endEast, endWest);*/
    }

    public void setStartORun(boolean startEast, boolean startWest) {
        //mPresenter.setStartORun(startEast, startWest);
    }
}