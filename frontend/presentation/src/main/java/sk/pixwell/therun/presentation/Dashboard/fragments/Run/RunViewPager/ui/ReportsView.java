package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui;

import java.util.List;

import sk.pixwell.therun.domain.Report;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface ReportsView {

    void setList(List<Report> reportList);
    void onUpdateNeeded();
    void setRefreshing(boolean b);
}
