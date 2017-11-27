package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui;

import java.util.List;

import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface StagesView {

    void setList(List<Stage> teamsList);

    void navigateToDetailStage(Stage stage);

    void navigateToDetailDialogStage(Stage stage);

    void onUpdateNeeded();

    void setRefreshing(Boolean b);

    void navigateToAminity(Stage stage);
}
