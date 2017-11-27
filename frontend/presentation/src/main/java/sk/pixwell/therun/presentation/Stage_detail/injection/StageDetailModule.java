package sk.pixwell.therun.presentation.Stage_detail.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.Stage_detail.presenter.StageDetailPresenter;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Module
public class StageDetailModule {

    @Provides
    StageDetailPresenter provideStageDetailPresenter(Context context, GetTrack getTrack){
        return new StageDetailPresenter(context, getTrack);
    }

    @PerActivity
    @Provides
    GetTrack provideGetTrackUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetTrack(threadExecutor, postExecutionThread, repository);
    }

}
