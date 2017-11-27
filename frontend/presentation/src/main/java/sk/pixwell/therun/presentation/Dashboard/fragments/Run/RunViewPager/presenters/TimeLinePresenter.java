package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.FavoriteListView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.TimeLineView;
import sk.pixwell.therun.presentation.Presenter;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TimeLinePresenter implements Presenter<TimeLineView> {

    private TimeLineView view;
    private Context context;

    public TimeLinePresenter(Context context) {
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
    public void setView(@NonNull TimeLineView view) {
        this.view = view;
    }


}
