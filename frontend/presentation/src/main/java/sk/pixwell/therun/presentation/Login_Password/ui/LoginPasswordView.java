package sk.pixwell.therun.presentation.Login_Password.ui;

import sk.pixwell.therun.domain.Runner;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface LoginPasswordView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    String getPassword();
    void setError(String s);
    void navigateToDashboard(Runner result);
    void onUpdateNeeded();
    void networkConnection();
}
