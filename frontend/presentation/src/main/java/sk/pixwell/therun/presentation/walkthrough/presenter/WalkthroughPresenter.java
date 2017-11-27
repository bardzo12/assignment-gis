package sk.pixwell.therun.presentation.walkthrough.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import rx.Subscriber;
import sk.pixwell.therun.data.executor.GetMeInfo;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.walkthrough.ui.WalkthroughView;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
public class WalkthroughPresenter implements Presenter<WalkthroughView> {

    private WalkthroughView view;
    private GetMeInfo getMeInfoUseCase;
    private Context context;
    private GetMeInfo getMeInfo;

    public WalkthroughPresenter(Context context) {
        this.context = context;
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

    public void setView(@NonNull WalkthroughView view) {
        this.view = view;
    }

}
