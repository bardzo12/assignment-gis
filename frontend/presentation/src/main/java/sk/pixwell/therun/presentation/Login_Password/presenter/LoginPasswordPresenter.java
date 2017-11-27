package sk.pixwell.therun.presentation.Login_Password.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import sk.pixwell.therun.data.exception.NetworkConnectionException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.CredentialsLogin;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Login_Password.ui.LoginPasswordView;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
public class LoginPasswordPresenter implements Presenter<LoginPasswordView> {

    private LoginPasswordView mView;
    private CredentialsLogin mLoginUseCase;
    private GetMeInfo mMeInfoUseCase;
    private final Context mContext;

    public LoginPasswordPresenter(GetMeInfo mMeInfoUseCase, CredentialsLogin loginUseCase, Context context) {
        this.mLoginUseCase = loginUseCase;
        this.mContext = context;
        this.mMeInfoUseCase = mMeInfoUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void login(String email, String password) {
        mView.showProgress();
        if(password != null && password.length() > 0)
            mLoginUseCase.execute(new EmailLoginSubscriber(), email, password);
        else
            mView.setError("Heslo nie je vyplnené");
    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    public void setView(@NonNull LoginPasswordView view) {
        this.mView = view;
    }

    public String getPassword() {
        return mView.getPassword();
    }

    @RxLogSubscriber
    private class EmailLoginSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
            mView.hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e);
            if(e instanceof UpdateException) {
                mView.onUpdateNeeded();
            } else if (e instanceof NetworkConnectionException && e.getMessage().equals("No connection"))
                mView.networkConnection();
            mView.showError(e.getMessage());
            mView.hideProgress();
        }

        @Override
        public void onNext(Integer result) {
            switch (result){
                case 401:
                    mView.setError("Zlé meno alebo heslo");
                    break;
                case 200:
                    mMeInfoUseCase.execute(new ProfileSubscriber());
                    break;
            }
        }

    }

    @RxLogSubscriber
    private class ProfileSubscriber extends DefaultSubscriber<Runner> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                mView.onUpdateNeeded();
            }
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Runner result) {
            Timber.i("Result error: %s",result.getFirstName());
            mView.navigateToDashboard(result);
        }


    }

}
