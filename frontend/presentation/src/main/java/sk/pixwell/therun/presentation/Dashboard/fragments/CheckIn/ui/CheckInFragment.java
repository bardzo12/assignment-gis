package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.injection.CheckInComponent;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.presenter.CheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;
/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInFragment  extends TheRunFragment implements HasComponent<CheckInComponent>, CheckInView,
        ViewPager.OnPageChangeListener{
    @Inject
    CheckInPresenter mPresenter;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    //private QRCheckInFragment currentFragment;
    CheckInComponent component;
    public static CheckInFragment newInstance() {
        CheckInFragment fragment = new CheckInFragment();
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
        View layout = inflater.inflate(R.layout.fragment_check_in, container, false);
        ButterKnife.bind(this,layout);
        initializeLayout();
        return layout;
    }
    public void initializeLayout() {
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.post(new Runnable()
        {
            @Override
            public void run()
            {
                onPageSelected(mViewPager.getCurrentItem());
            }
        });
        mViewPager.setOffscreenPageLimit(2);
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
    @Override
    public CheckInComponent getComponent() {
        return component;
    }
    public void photoPermission(boolean b) {
        mPresenter.setPhotoPermission(b);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        mPresenter.pageSelected(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        //mPresenter.pageSelected(position);
    }
    public void bluetoothPermission(boolean b) {
        mPresenter.setBluetoothPermission(b);
    }
    public void bluetoothOf() {
        mPresenter.bluetoothOf();
    }
    public void bluetoothOn() {
        mPresenter.bluetoothOn();
    }
    public void setViewBeacon(Boolean result, Beacon beacon) {
        mPresenter.setViewBeacon(result, beacon);
    }
}