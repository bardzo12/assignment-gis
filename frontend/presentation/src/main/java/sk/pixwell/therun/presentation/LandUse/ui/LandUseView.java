package sk.pixwell.therun.presentation.LandUse.ui;

import java.util.List;

import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public interface LandUseView {
    Stage getStage();

    void setChart(List<Landuse> result);
}
