package sk.pixwell.therun.presentation.shared.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sk.pixwell.therun.data.cache.Cache;
import sk.pixwell.therun.data.cache.CacheImpl;
import sk.pixwell.therun.data.executor.JobExecutor;
import sk.pixwell.therun.data.repository.DataRepository;
import sk.pixwell.therun.data.sharedprefs.ISharedPreferences;
import sk.pixwell.therun.data.sharedprefs.SharedPreferencesImpl;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@Module
public class TheRunApplicationModule {
    private TheRunApplication application;

    public TheRunApplicationModule(TheRunApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    Repository provideRepository(DataRepository dataRepository) {
        return dataRepository;
    }


    @Provides @Singleton
    ISharedPreferences provideSharedPreferences(SharedPreferencesImpl sharedPreferences) {
        return sharedPreferences;
    }

    @Provides @Singleton
    Cache provideCache(CacheImpl userCache) {
        return userCache;
    }



}
