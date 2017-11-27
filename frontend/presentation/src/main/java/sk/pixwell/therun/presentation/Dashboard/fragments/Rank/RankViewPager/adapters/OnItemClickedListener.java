package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.adapters;

import java.util.List;

import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface OnItemClickedListener {
    void onItemClicked(int postId, List<Team> result);
}