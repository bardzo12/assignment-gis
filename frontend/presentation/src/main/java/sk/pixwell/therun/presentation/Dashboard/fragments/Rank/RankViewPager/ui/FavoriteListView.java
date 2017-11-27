package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui;

import java.util.List;

import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface FavoriteListView {

    void setList(List<Team> teamsList);

    void onUpdateNeeded();

    void changeFavorite();

    void setRefreshing(boolean b);

    void networkConnection();
}
