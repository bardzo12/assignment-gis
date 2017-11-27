package sk.pixwell.therun.presentation.LandUse.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.Administrative.injection.AdministrativeModule;
import sk.pixwell.therun.presentation.Administrative.ui.AdministrativeActivity;
import sk.pixwell.therun.presentation.LandUse.ui.LandUseActivity;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Component (dependencies = TheRunApplicationComponent.class, modules = {TheRunActivityModule.class, LandUseModule.class})
public interface LandUseComponent {
    void inject(LandUseActivity activity);
}
