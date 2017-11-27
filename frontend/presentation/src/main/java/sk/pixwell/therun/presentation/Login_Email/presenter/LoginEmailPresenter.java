package sk.pixwell.therun.presentation.Login_Email.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import sk.pixwell.therun.presentation.Login_Email.ui.LoginEmailView;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.shared.UIUtils;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
public class LoginEmailPresenter implements Presenter<LoginEmailView> {

    private LoginEmailView view;
    
    @Inject
    public LoginEmailPresenter() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.view = null;
    }

    public void setView(@NonNull LoginEmailView view) {
        this.view = view;
    }

    public void validateEmail(String result) {
        Boolean correct = false;
        if (result == null) {
            correct = false;
        } else {
            correct =  android.util.Patterns.EMAIL_ADDRESS.matcher(result).matches();
        }
        if(correct)
            view.navigateToLoginPassword(result);
        else
            view.error("Zlý formát emailu");
    }
}
