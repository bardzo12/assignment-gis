package sk.pixwell.therun.presentation.Dashboard.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.DeleteToken;
import sk.pixwell.therun.data.executor.ChangeFavoriteTeams;
import sk.pixwell.therun.data.executor.CheckInOnStart;
import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.data.executor.EndRun;
import sk.pixwell.therun.data.executor.GetCaste;
import sk.pixwell.therun.data.executor.GetCheckInInfo;
import sk.pixwell.therun.data.executor.GetFavoriteTeams;
import sk.pixwell.therun.data.executor.GetReports;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.data.executor.GetStagesInfo;
import sk.pixwell.therun.data.executor.GetTeamSegments;
import sk.pixwell.therun.data.executor.GetTeams;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.data.executor.StartRun;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.QRCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.VerifiedPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.iBeaconCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.presenter.CheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters.LiveMapPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters.StagesPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.presenter.MapPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.presenters.FavoriteListPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.presenters.TeamListPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.presenter.RankPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.ReportsPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.SupportPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.TimeLineCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.TimeLinePresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.presenter.RunPresenter;
import sk.pixwell.therun.presentation.Dashboard.presenter.DashboardPresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Module
public class DashboardModule {

    @Provides
    DashboardPresenter provideDashboardPresenter(Context context, DeleteToken deleteToken){
        return new DashboardPresenter(context, deleteToken);
    }

    @PerActivity
    @Provides
    DeleteToken provideDeleteTokenUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new DeleteToken(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    CheckInPresenter provideCheckInFragmentPresenter(Context context, GetStagesInfo getStagesInfo){
        return new CheckInPresenter(context, getStagesInfo);
    }

    @PerActivity
    @Provides
    GetStagesInfo provideGetStagesInfoUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetStagesInfo(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    MapPresenter provideMapFragmentPresenter(Context context){
        return new MapPresenter(context);
    }

    @Provides
    RunPresenter provideRunFragmentPresenter(Context context, GetCheckInInfo getCheckInInfo){
        return new RunPresenter(context, getCheckInInfo);
    }

    @Provides
    RankPresenter provideRankFragmentPresenter(Context context){
        return new RankPresenter(context);
    }


    //CheckInFragments
    @Provides
    QRCheckInPresenter provideQRCheckInFragmentPresenter(Context context, CheckInStage checkInStageUseCase){
        return new QRCheckInPresenter(context, checkInStageUseCase);
    }

    @Provides
    iBeaconCheckInPresenter provideiBeaconCheckInFragmentPresenter(Context context, CheckInStage checkInStage){
        return new iBeaconCheckInPresenter(context, checkInStage);
    }

    @Provides
    VerifiedPresenter provideVerifiedFragmentPresenter(Context context, GetTeamSegments getTeamSegments){
        return new VerifiedPresenter(context, getTeamSegments);
    }

    @PerActivity
    @Provides
    GetTeamSegments provideGetTeamSegmentsUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetTeamSegments(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    CheckInStage provideCheckInStageUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new CheckInStage(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    GetCheckInInfo provideGetCheckInInfoUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetCheckInInfo(threadExecutor, postExecutionThread, repository);
    }


    //RankFragments
    @Provides
    FavoriteListPresenter provideFavoriteListFragmentPresenter(Context context, GetTeams getTeams, ChangeFavoriteTeams changeFavoriteTeams, GetFavoriteTeams getFavoriteTeams){
        return new FavoriteListPresenter(context, getTeams, changeFavoriteTeams, getFavoriteTeams);
    }

    @Provides
    TeamListPresenter provideTeamListFragmentPresenter(Context context, GetTeams getTeams, ChangeFavoriteTeams changeFavoriteTeams, GetFavoriteTeams getFavoriteTeams){
        return new TeamListPresenter(context, getTeams, changeFavoriteTeams, getFavoriteTeams);
    }

    @PerActivity
    @Provides
    ChangeFavoriteTeams provideChangeFavoriteTeamsUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new ChangeFavoriteTeams(threadExecutor, postExecutionThread, repository);
    }
    @PerActivity
    @Provides
    GetFavoriteTeams provideGetFavoriteTeamsUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetFavoriteTeams(threadExecutor, postExecutionThread, repository);
    }

    //RunFragments
    @Provides
    SupportPresenter provideSupportFragmentPresenter(Context context){
        return new SupportPresenter(context);
    }

    @Provides
    ReportsPresenter provideReportsFragmentPresenter(Context context, GetReports getReports){
        return new ReportsPresenter(context, getReports);
    }

    @PerActivity
    @Provides
    GetReports provideGetReportsUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetReports(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    TimeLinePresenter provideTimeLineFragmentPresenter(Context context){
        return new TimeLinePresenter(context);
    }

    @Provides
    TimeLineCheckInPresenter provideTimeLineCheckInFragmentPresenter(Context context, CheckInOnStart checkInOnStart, StartRun startRun, EndRun endRun){
        return new TimeLineCheckInPresenter(context, checkInOnStart, startRun, endRun);
    }

    @PerActivity
    @Provides
    CheckInOnStart provideCheckInOnStartUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new CheckInOnStart(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    StartRun provideStartRunOnStartUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new StartRun(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    EndRun provideEndRunOnStartUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new EndRun(threadExecutor, postExecutionThread, repository);
    }

    //MapFragments
    @Provides
    LiveMapPresenter provideLiveMapFragmentPresenter(Context context, GetTeams getTeams, GetTrack getTrack, GetCaste getCaste){
        return new LiveMapPresenter(context, getTeams, getTrack, getCaste);
    }

    @PerActivity
    @Provides
    GetTrack provideGetTrackUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetTrack(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    GetCaste provideGetCasteUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetCaste(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    StagesPresenter provideStagesFragmentPresenter(Context context, GetStages getStagesUseCase){
        return new StagesPresenter(context, getStagesUseCase);
    }

    @PerActivity
    @Provides
    GetStages provideGetStagesUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetStages(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    GetTeams provideGetTeamsUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetTeams(threadExecutor, postExecutionThread, repository);
    }

}
