package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sk.pixwell.therun.data.exception.NetworkConnectionException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.ChangeFavoriteTeams;
import sk.pixwell.therun.data.executor.GetFavoriteTeams;
import sk.pixwell.therun.data.executor.GetTeams;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.VerifiedView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui.TeamListView;
import sk.pixwell.therun.presentation.Presenter;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamListPresenter implements Presenter<TeamListView> {

    private TeamListView view;
    private Context context;
    private List<Team> teamsList;
    private GetTeams getTeams;
    private GetFavoriteTeams getFavoriteTeams;
    private ChangeFavoriteTeams changeFavoriteTeams;

    public TeamListPresenter(Context context, GetTeams getTeams, ChangeFavoriteTeams changeFavoriteTeams, GetFavoriteTeams getFavoriteTeams) {
        this.context = context;
        this.getTeams = getTeams;
        this.changeFavoriteTeams = changeFavoriteTeams;
        this.getFavoriteTeams = getFavoriteTeams;
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

    public void initializeList(){
        teamsList = new ArrayList<>();
        getTeams.execute(new TeamsSubscriber());
    }

    @Override
    public void setView(@NonNull TeamListView view) {
        this.view = view;
    }


    public void onItemClicked(int postId, List<Team> result) {
        List<Integer> list = new ArrayList<>();
        for (Team team: result)
            if(team.getFavorite())
                list.add(team.getId());
        changeFavoriteTeams.execute(new ChangeFavoriteSubscriber(), list);
        view.setRefreshing(true);
        view.changeFavorite();
        Timber.d("Item click: %d",  postId);
    }

    @RxLogSubscriber
    private class ChangeFavoriteSubscriber extends DefaultSubscriber<Boolean> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            Timber.i("Result error");
            view.setRefreshing(false);
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(Boolean result) {
            view.changeFavorite();
            view.setRefreshing(false);
            Timber.i("Result okey");
        }

    }

    @RxLogSubscriber
    private class TeamsSubscriber extends DefaultSubscriber<List<Team>> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            } else if (e instanceof NetworkConnectionException && e.getMessage().equals("No internet connection"))
                view.networkConnection();
            view.setRefreshing(false);
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<Team> result) {
            Timber.i("Result okey");
            teamsList = result;
            getFavoriteTeams.execute(new GetFavoriteTeamsSubscriber());
        }

    }

    @RxLogSubscriber
    private class GetFavoriteTeamsSubscriber extends DefaultSubscriber<List<Integer>> {
        @Override
        public void onCompleted() {
            Timber.i("Complete");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            view.setRefreshing(false);
            Timber.i("Result error: %s",e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<Integer> result) {
            if(result != null) {
                for (Integer id : result)
                    for (int i = 0; i < teamsList.size(); i++)
                        if (teamsList.get(i).getId() == id) {
                            teamsList.get(i).setFavorite(true);
                        }
                view.setRefreshing(false);
                view.setList(teamsList);
            }else {
                view.setRefreshing(false);
                view.setList(teamsList);
            }
        }


    }
}
