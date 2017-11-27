package sk.pixwell.therun.presentation.Stage_detail.ui;

import java.util.List;

import sk.pixwell.therun.domain.PointOfStage;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface StageDetailView {
    void setTrack(List<PointOfStage> list);

    void onUpdateNeeded();

    void navigateToAdministrative();

    void navigateToUseLand();
}
