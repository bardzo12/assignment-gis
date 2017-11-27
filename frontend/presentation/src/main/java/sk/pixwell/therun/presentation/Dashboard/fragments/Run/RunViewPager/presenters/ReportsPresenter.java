package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetReports;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.FavoriteListView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.TeamListView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui.ReportsView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class ReportsPresenter implements Presenter<ReportsView> {

    private ReportsView view;
    private Context context;
    private List<Report> reportList;
    private GetReports getReportsUseCase;

    public ReportsPresenter(Context context, GetReports getReportsUseCase) {
        this.context = context;
        this.getReportsUseCase = getReportsUseCase;
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
    public void setView(@NonNull ReportsView view) {
        this.view = view;
    }

    public void initializeList(){
        reportList = new ArrayList<>();
        view.setRefreshing(true);
        getReportsUseCase.execute(new GetReportsSubscriber());
    }



    @RxLogSubscriber
    private class GetReportsSubscriber extends DefaultSubscriber<List<Report>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            view.setRefreshing(false);
            Timber.i("Result error: %s", e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<Report> result) {
            view.setList(result);
            view.setRefreshing(false);
        }
    }

    public void onItemClicked(int postId) {
        Timber.d("Item click: %d",  postId);
    }

}
