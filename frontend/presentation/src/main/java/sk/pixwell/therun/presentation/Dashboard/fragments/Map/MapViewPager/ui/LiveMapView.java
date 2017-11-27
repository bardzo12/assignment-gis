package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui;

import java.util.List;

import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface LiveMapView {
    void setTeams(List<Team> result);
    void showToast();
    void onUpdateNeeded();
    void setTrack(List<PointOfStage> result);
    void networkConnection();
    void setCastlePolygons(List<CastlePolygon> result);
}
