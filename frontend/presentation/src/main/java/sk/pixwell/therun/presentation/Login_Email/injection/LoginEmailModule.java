package sk.pixwell.therun.presentation.Login_Email.injection;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.presentation.Login_Email.presenter.LoginEmailPresenter;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class LoginEmailModule {
	
	@Provides
	LoginEmailPresenter provideLoginEmailPresenter(){
		return new LoginEmailPresenter();
	}

}
