package sk.pixwell.therun.presentation.walkthrough.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;
import sk.pixwell.therun.presentation.walkthrough.ui.WalkthroughActivity;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Component(dependencies = TheRunApplicationComponent.class, modules = {WalkthroughModule.class, TheRunActivityModule.class})
public interface WalkthroughComponent {
    void inject(WalkthroughActivity walkthroughActivity);
}
