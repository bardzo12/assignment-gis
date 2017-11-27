package sk.pixwell.therun.presentation.Aminity.injection;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.GetAmenity;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Aminity.presenter.StageAminityPresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Module
public class StageAminityModule {

    @Provides
    StageAminityPresenter provideStageAminityPresenter(GetTrack getTrack, GetAmenity getAmenity){
        return new StageAminityPresenter(getTrack, getAmenity);
    }

    @PerActivity
    @Provides
    GetTrack provideGetTrackUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetTrack(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    GetAmenity provideGetAmenityUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetAmenity(threadExecutor, postExecutionThread, repository);
    }
}
