package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.FavoriteListView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.SupportView;
import sk.pixwell.therun.presentation.Presenter;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SupportPresenter implements Presenter<SupportView> {

    private SupportView view;
    private Context context;

    public SupportPresenter(Context context) {
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

    }

    @Override
    public void setView(@NonNull SupportView view) {
        this.view = view;
    }


}
