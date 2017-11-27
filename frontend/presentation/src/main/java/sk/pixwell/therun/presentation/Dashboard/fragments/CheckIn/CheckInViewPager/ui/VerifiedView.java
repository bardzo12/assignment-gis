package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui;

import java.util.List;

import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface VerifiedView {

    void setList(List<Stage> teamsList);
    void onUpdateNeeded();
    void setRefreshing(boolean b);
}
