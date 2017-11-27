package sk.pixwell.therun.presentation.LandUse.injection;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.GetKraje;
import sk.pixwell.therun.data.executor.GetLandUse;
import sk.pixwell.therun.data.executor.GetOkresy;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Administrative.presenter.AdministrativePresenter;
import sk.pixwell.therun.presentation.LandUse.presenter.LandUsePresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Module
public class LandUseModule {

    @Provides
    LandUsePresenter provideLandUsePresenter(GetLandUse getLandUse){
        return new LandUsePresenter(getLandUse);
    }

    @PerActivity
    @Provides
    GetLandUse provideGetLandUseUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetLandUse(threadExecutor, postExecutionThread, repository);
    }
}
