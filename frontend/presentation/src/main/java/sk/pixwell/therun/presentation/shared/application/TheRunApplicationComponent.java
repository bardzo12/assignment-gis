package sk.pixwell.therun.presentation.shared.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import sk.pixwell.therun.data.sharedprefs.ISharedPreferences;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.navigation.Navigator;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Singleton
@Component(modules = {TheRunApplicationModule.class})
public interface TheRunApplicationComponent {
    void inject(TheRunActivity theRunActivity);


    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    Repository repository();
    Navigator navigator();
    ISharedPreferences sharedPreferences();
}
