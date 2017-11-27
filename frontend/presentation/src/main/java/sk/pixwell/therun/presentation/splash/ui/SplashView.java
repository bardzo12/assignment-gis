package sk.pixwell.therun.presentation.splash.ui;

import sk.pixwell.therun.domain.Runner;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface SplashView {

    void finishSplash();
    void navigateToDashboard(Runner o);
    void navigateToDashboard();
}
