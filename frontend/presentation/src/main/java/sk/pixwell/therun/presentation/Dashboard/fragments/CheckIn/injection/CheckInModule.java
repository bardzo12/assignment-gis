package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.data.executor.GetStagesInfo;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.QRCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.iBeaconCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.presenter.CheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.presenter.DashboardPresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Module
public class CheckInModule {

    @Provides
    CheckInPresenter provideCheckInPresenter(Context context, GetStagesInfo getStagesInfo){
        return new CheckInPresenter(context, getStagesInfo);
    }

    @PerActivity
    @Provides
    GetStagesInfo provideGetStagesInfoUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetStagesInfo(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    iBeaconCheckInPresenter provideiBeaconCheckInFragmentPresenter(Context context, CheckInStage checkInStage){
        return new iBeaconCheckInPresenter(context, checkInStage);
    }

    @Provides
    QRCheckInPresenter provideQRCheckInFragmentPresenter(Context context, CheckInStage checkInStage){
        return new QRCheckInPresenter(context, checkInStage);
    }

    @PerActivity
    @Provides
    CheckInStage provideCheckInStageUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new CheckInStage(threadExecutor, postExecutionThread, repository);
    }

}
