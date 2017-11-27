package sk.pixwell.therun.presentation.walkthrough.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.walkthrough.injection.DaggerWalkthroughComponent;
import sk.pixwell.therun.presentation.walkthrough.injection.WalkthroughComponent;
import sk.pixwell.therun.presentation.walkthrough.injection.WalkthroughModule;
import sk.pixwell.therun.presentation.walkthrough.presenter.WalkthroughPresenter;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class WalkthroughActivity extends TheRunActivity implements HasComponent<WalkthroughComponent>, WalkthroughView {

    @Inject
    WalkthroughPresenter mPresenter;

    @OnClick(R.id.runnerButton)
    void runnerMode(){
        this.navigator.navigateToLogin(this);
    }

    @OnClick(R.id.fansButton)
    void fansMode(){
        this.navigator.navigateToFansMode(this, this);
    }

    private WalkthroughComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        ButterKnife.bind(this);

        initializeInjector();

        initializeLayout();

    }

    private void initializeLayout() {
        mPresenter.setView(this);
    }

    private void initializeInjector() {
        component = DaggerWalkthroughComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .walkthroughModule(new WalkthroughModule())
                .build();
        component.inject(this);

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WalkthroughActivity.class);
    }


    @Override
    public WalkthroughComponent getComponent() {
        return component;
    }

    @Override
    public void navigateToDashboard(Runner runner) {
        navigator.navigateToRunnerMode(this, runner, this);
    }
}
