package sk.pixwell.therun.presentation.Administrative.injection;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.GetKraje;
import sk.pixwell.therun.data.executor.GetOkresy;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Administrative.presenter.AdministrativePresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Module
public class AdministrativeModule {

    @Provides
    AdministrativePresenter provideStageAminityPresenter(GetKraje getKraje, GetOkresy getOkresy){
        return new AdministrativePresenter(getKraje, getOkresy);
    }

    @PerActivity
    @Provides
    GetKraje provideGetKrajeUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetKraje(threadExecutor, postExecutionThread, repository);
    }

    @PerActivity
    @Provides
    GetOkresy provideGetOkresyUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetOkresy(threadExecutor, postExecutionThread, repository);
    }
}
