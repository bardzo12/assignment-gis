package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.presenter.MapPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.ui.MapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.presenter.RankPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class RankFragment extends TheRunFragment implements RankView {

    @Inject
    RankPresenter mPresenter;

    @BindView(R.id.tabs2)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager2)
    ViewPager mViewPager;

    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_rank, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        mPresenter.setViewPager(viewPager, getFragmentManager());
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

    public void changeFavoriteInTeamsFragment() {
        mPresenter.changeFavoriteInTeamsFragment();
    }

    public void changeFavoriteInFavoriteFragment() {
        mPresenter.changeFavoriteInFavoriteFragment();
    }
}