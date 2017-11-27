package sk.pixwell.therun.presentation.Login_Email.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.Login_Email.ui.LoginEmailActivity;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

@PerActivity
@Component(dependencies = TheRunApplicationComponent.class, modules = {LoginEmailModule.class, TheRunActivityModule.class})
public interface LoginEmailComponent {
    void inject(LoginEmailActivity loginEmailActivity);
}
