package sk.pixwell.therun.presentation.splash.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;
import sk.pixwell.therun.presentation.splash.ui.SplashActivity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Component(dependencies = TheRunApplicationComponent.class, modules = {SplashModule.class, TheRunActivityModule.class})
public interface SplashComponent {
    void inject(SplashActivity profileActivity);
}
