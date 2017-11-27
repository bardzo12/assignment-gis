package sk.pixwell.therun.presentation.Dashboard.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.QRCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.VerifiedFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.iBeaconCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui.CheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.LiveMapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.StagesFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.ui.MapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.FavoriteListFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.TeamListFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.ReportsFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.SupportFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineCheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.ui.RunFragment;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Component (dependencies = TheRunApplicationComponent.class, modules = {TheRunActivityModule.class, DashboardModule.class})
public interface DashboardComponent {
    void inject(DashboardActivity commentActivity);
    void inject(CheckInFragment checkInFragment);
    void inject(MapFragment mapFragment);
    void inject(RankFragment mapFragment);
    void inject(RunFragment runFragment);

    void inject(QRCheckInFragment qrCheckInFragment);
    void inject(iBeaconCheckInFragment iBeaconCheckInFragment);
    void inject(VerifiedFragment verifiedFragment);

    void inject(FavoriteListFragment favoriteListFragment);
    void inject(TeamListFragment teamListFragment);

    void inject(ReportsFragment reportsFragment);
    void inject(SupportFragment supportFragment);

    void inject(TimeLineFragment timeLineFragment);
    void inject(TimeLineCheckInFragment timeLineCheckInFragment);


    void inject(LiveMapFragment liveMapFragment);
    void inject(StagesFragment stagesFragment);
}
