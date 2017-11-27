package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.QRCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.iBeaconCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui.CheckInFragment;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Component (dependencies = TheRunApplicationComponent.class, modules = {TheRunActivityModule.class, CheckInModule.class})
public interface CheckInComponent {
    void inject(CheckInFragment checkInFragment);
    void inject(iBeaconCheckInFragment iBeaconCheckInFragment);
    void inject(QRCheckInFragment QRCheckInFragment);
}
