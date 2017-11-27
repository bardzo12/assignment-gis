package sk.pixwell.therun.presentation.shared.activity;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.CheckInStage;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.QRCheckInPresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class TheRunActivityModule {
    private final Activity activity;

    public TheRunActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    /*@PerActivity
    @Provides
    CheckInStage provideCheckInStageUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new CheckInStage(threadExecutor, postExecutionThread, repository);
    }*/


}
