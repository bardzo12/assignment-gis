package sk.pixwell.therun.data.entity.stages.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.points.mapper.PointsEntityDataMapper;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesEntityDataMapper {

    public static List<Stage> transform(StagesEntity stagesEntity) {
        Timber.i("PR: Začal som DATA MAPPING");
        List<StagesEntity.Data> dataList = stagesEntity.getData();
        List<Stage> stages = new ArrayList<>();
        Stage stage;
        PointOfStage point;
        for (StagesEntity.Data data :dataList) {
            stage = new Stage();
            stage.setId(data.getId());
            stage.setName(data.getName_from() + " -> " + data.getName_to());
            stage.setDescription(data.getDescription());
            stage.setIncline(data.getIncline());
            stage.setPoints(PointsEntityDataMapper.transform(data.getPoints()));
            stage.setDecline(data.getDecline());
            stage.setDistance(data.getDistance());
            stage.setDistanceFromStart(data.getDistance_from_start());

            point = new PointOfStage();
            point.setLatitude(data.getFrom().getData().getLat());
            point.setLongitude(data.getFrom().getData().getLng());
            stage.setStart(point);

            point = new PointOfStage();
            point.setLatitude(data.getTo().getData().getLat());
            point.setLongitude(data.getTo().getData().getLng());
            stage.setEnd(point);
            stage.setMax_elevation(data.getMax_elevation());
            stage.setMin_elevation(data.getMin_elevation());
            stage.setY_AxisMaximum(data.getY_AxisMaximum());
            stage.setY_AxisMinimum(data.getY_AxisMinimum());

            stages.add(stage);
        }
        Timber.i("PR: Ukončil som DATA MAPPING");
        return stages;
    }

}
