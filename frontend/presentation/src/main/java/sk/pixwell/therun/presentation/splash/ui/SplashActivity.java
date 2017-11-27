package sk.pixwell.therun.presentation.splash.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.StagesSingleton;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.splash.injection.DaggerSplashComponent;
import sk.pixwell.therun.presentation.splash.injection.SplashComponent;
import sk.pixwell.therun.presentation.splash.injection.SplashModule;
import sk.pixwell.therun.presentation.splash.presenter.SplashPresenter;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SplashActivity extends TheRunActivity implements HasComponent<SplashComponent>, SplashView {


    @Inject
    SplashPresenter mPresenter;

    private SplashComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeInjector();

        mPresenter.setView(this);

    }

    private void initializeInjector() {
        component = DaggerSplashComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .splashModule(new SplashModule())
                .build();
        component.inject(this);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(StagesSingleton.getInstance().getStages().size() > 0 && PointsOfStageSingleton.getInstance().getPoints().size() > 0)
            mPresenter.controlToken();
    }

    @Override
    public SplashComponent getComponent() {
        return component;
    }

    public void finishSplash(){
        mPresenter.controlToken();
    }

    @Override
    public void navigateToDashboard(Runner runner) {
        navigator.navigateToRunnerMode(this, runner, this);
        finish();
    }

    @Override
    public void navigateToDashboard() {
        navigator.navigateToWalkthrough(this);
        finish();
    }
}
