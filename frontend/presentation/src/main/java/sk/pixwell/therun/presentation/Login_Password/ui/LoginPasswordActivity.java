package sk.pixwell.therun.presentation.Login_Password.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.Login_Password.injection.DaggerLoginPasswordComponent;
import sk.pixwell.therun.presentation.Login_Password.injection.LoginPasswordComponent;
import sk.pixwell.therun.presentation.Login_Password.injection.LoginPasswordModule;
import sk.pixwell.therun.presentation.Login_Password.presenter.LoginPasswordPresenter;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LoginPasswordActivity extends TheRunActivity implements HasComponent<LoginPasswordComponent>, LoginPasswordView {


    @OnClick(R.id.loginButton)
    void next(){
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            Timber.i("App version: %s", version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String password = mPresenter.getPassword();
        mPresenter.login(email, password);
    }

    @Inject
    LoginPasswordPresenter mPresenter;

    @OnClick(R.id.back_button)
    void back(){
        super.onBackPressed();
    }

    @BindView(R.id.password_edittext_layout)
    TextInputLayout mPasswordLayout;

    @BindView(R.id.email_edittext)
    TextInputEditText mPassword;

    private LoginPasswordComponent component;
    private String email;
    private static final String INTENT_EXTRA_PARAM_EMAIL = "INTENT_PARAM_EMAIL";
    private static final String INSTANCE_STATE_PARAM_EMAIL = "STATE_PARAM_EMAIL";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        ButterKnife.bind(this);

        initializeActivity(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);

    }

    private void initializeInjector() {
        component = DaggerLoginPasswordComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .loginPasswordModule(new LoginPasswordModule())
                .build();
        component.inject(this);
    }


    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.email = getIntent().getStringExtra(INTENT_EXTRA_PARAM_EMAIL);
        } else {
            this.email = savedInstanceState.getString(INSTANCE_STATE_PARAM_EMAIL);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(INSTANCE_STATE_PARAM_EMAIL, this.email);
        }
        super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public static Intent getCallingIntent(Context context, String result) {
        Intent callingIntent = new Intent(context, LoginPasswordActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_EMAIL, result);
        return callingIntent;
    }

    @Override
    public LoginPasswordComponent getComponent() {
        return component;
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Načítavanie");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public void setError(String result) {
        mPasswordLayout.setError(result);
    }

    @Override
    public void navigateToDashboard(Runner result) {
        this.navigator.navigateToRunnerMode(this, result, this);
    }

    @Override
    public void networkConnection() {
        mPasswordLayout.setError("Nie ste pripojený k Internetu");
    }
}
