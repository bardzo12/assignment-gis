package sk.pixwell.therun.presentation.Aminity.ui;

import java.util.List;

import sk.pixwell.therun.domain.AmenityPoint;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface StageAminityView {
    void setTrack(List<PointOfStage> list);

    void onUpdateNeeded();

    Stage getStage();

    void setResult(List<AmenityPoint> result);
}
