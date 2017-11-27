package sk.pixwell.therun.presentation.Login_Password.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.executor.CredentialsLogin;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.Login_Password.presenter.LoginPasswordPresenter;
import sk.pixwell.therun.presentation.PerActivity;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class LoginPasswordModule {

	@PerActivity
	@Provides
	CredentialsLogin provideEmailUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new CredentialsLogin(repository, threadExecutor, postExecutionThread);
	}

    @PerActivity
    @Provides
    GetMeInfo provideGetMeInfoUseCase(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetMeInfo(threadExecutor, postExecutionThread, repository);
    }

	@PerActivity
	@Provides
	LoginPasswordPresenter provideLoginPasswordPresenter(GetMeInfo getMeInfo, CredentialsLogin emailAuthUseCase, Context context){
		return new LoginPasswordPresenter(getMeInfo, emailAuthUseCase, context);
	}

}
