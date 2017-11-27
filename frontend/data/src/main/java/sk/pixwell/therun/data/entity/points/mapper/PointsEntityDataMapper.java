package sk.pixwell.therun.data.entity.points.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class PointsEntityDataMapper {

    public static List<PointOfStage> transform(PointsEntity pointsEntity) {
        List<PointsEntity.Data> dataList = pointsEntity.getData();
        List<PointOfStage> pointOfStages = new ArrayList<>();
        PointOfStage pointOfStage;
        for (PointsEntity.Data data :dataList) {
            pointOfStage = new PointOfStage();
            pointOfStage.setLatitude(data.getLat());
            pointOfStage.setLongitude(data.getLng());
            pointOfStage.setElevation(data.getElevation());
            pointOfStages.add(pointOfStage);
        }
        return pointOfStages;
    }

}
