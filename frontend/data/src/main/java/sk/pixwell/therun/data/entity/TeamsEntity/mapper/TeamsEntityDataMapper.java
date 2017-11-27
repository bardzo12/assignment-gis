package sk.pixwell.therun.data.entity.TeamsEntity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Team;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamsEntityDataMapper {

    public static List<Team> transform(TeamsEntity teamsEntity) {
        List<TeamsEntity.Data> dataList = teamsEntity.getData();
        List<Team> teams = new ArrayList<>();
        int i = 1;
        for (TeamsEntity.Data data :dataList) {
            Team team = new Team();
            team.setId(data.getId());
            team.setFavorite(false);
            team.setName(data.getName());
            team.setRank(i);
            i++;
            PointOfStage pointOfStage = new PointOfStage();
            pointOfStage.setLatitude(data.getLast_position().getLat());
            pointOfStage.setLongitude(data.getLast_position().getLng());
            team.setActualPossition(pointOfStage);
            team.setFullTime(data.getTimeFull());
            teams.add(team);
        }
        return teams;
    }

}
