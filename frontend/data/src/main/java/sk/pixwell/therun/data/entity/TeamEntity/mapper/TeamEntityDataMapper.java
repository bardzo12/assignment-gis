package sk.pixwell.therun.data.entity.TeamEntity.mapper;

import sk.pixwell.therun.data.entity.TeamEntity.TeamEntity;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamEntityDataMapper {

    public static Team transform(TeamEntity teamEntity) {
        Team team = new Team();
        team.setId(teamEntity.getData().getId());
        team.setFavorite(false);
        team.setName(teamEntity.getData().getName());
        PointOfStage pointOfStage = new PointOfStage();
        pointOfStage.setLatitude(teamEntity.getData().getLast_position().getLat());
        pointOfStage.setLongitude(teamEntity.getData().getLast_position().getLng());
        team.setActualPossition(pointOfStage);
        return team;
    }

}
