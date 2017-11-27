package sk.pixwell.therun.presentation.splash.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.data.executor.GetTrack;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.splash.presenter.SplashPresenter;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class SplashModule {
	
	@Provides
	SplashPresenter provideSplashPresenter(Context context, GetStages getStages, GetTrack getTrack, GetMeInfo getMeInfo){
		return new SplashPresenter(context, getStages, getTrack, getMeInfo);
	}

	@PerActivity
	@Provides
	GetStages provideGetStagesUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new GetStages(threadExecutor, postExecutionThread, repository);
	}

	@PerActivity
	@Provides
	GetTrack provideGetTrackUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new GetTrack(threadExecutor, postExecutionThread, repository);
	}

	@PerActivity
	@Provides
	GetMeInfo provideGetMeInfoUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new GetMeInfo(threadExecutor, postExecutionThread, repository);
	}

}
