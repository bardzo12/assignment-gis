package sk.pixwell.therun.presentation.Login_Email.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.Login_Email.injection.DaggerLoginEmailComponent;
import sk.pixwell.therun.presentation.Login_Email.injection.LoginEmailComponent;
import sk.pixwell.therun.presentation.Login_Email.injection.LoginEmailModule;
import sk.pixwell.therun.presentation.Login_Email.presenter.LoginEmailPresenter;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LoginEmailActivity extends TheRunActivity implements HasComponent<LoginEmailComponent>, LoginEmailView {

    @Inject
    LoginEmailPresenter mPresenter;

    @BindView(R.id.email_edittext)
    EditText mEmail;

    @BindView(R.id.email_edittext_layout)
    TextInputLayout mInputEmail;

    @OnClick(R.id.nextButton)
    void next(){
        mPresenter.validateEmail(mEmail.getText().toString());
    }

    @OnClick(R.id.back_button)
    void back(){
        super.onBackPressed();
    }

    private LoginEmailComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        ButterKnife.bind(this);

        initializeInjector();

        mPresenter.setView(this);

    }

    private void initializeInjector() {
        component = DaggerLoginEmailComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .loginEmailModule(new LoginEmailModule())
                .build();
        component.inject(this);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginEmailActivity.class);
    }

    @Override
    public LoginEmailComponent getComponent() {
        return component;
    }

    @Override
    public void navigateToLoginPassword(String result) {
        this.navigator.navigateToPassword(this, this, result);
    }

    @Override
    public void error(String result) {
        mInputEmail.setError(result);
    }
}
