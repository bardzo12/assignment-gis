package sk.pixwell.therun.presentation.Dashboard.ui;

import org.altbeacon.beacon.Beacon;

import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetail;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface DashboardView {

    void navigateToDetail(Stage postId);
    void setViewBeacon(Boolean b, Beacon beacon);
    void navigateToStageDetailDialog(Stage stage);
    void navigateToWalkthrough();
    void navigateToEmailLogin();
    void enterToEnd(int i);
    void navigateToStageAminity(Stage stage);
}
