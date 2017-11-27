package sk.pixwell.therun.presentation.walkthrough.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.DeleteToken;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.walkthrough.presenter.WalkthroughPresenter;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class WalkthroughModule {
	
	@Provides
	WalkthroughPresenter provideWalkthroughPresenter(Context context){
		return new WalkthroughPresenter(context);
	}

}
