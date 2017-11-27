package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager;

import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface StageListener {
    void navigateToStage(Stage stage);

    void navigateToStageDialog(Stage stage);

    void onClickStageAminity(Stage stage);
}
