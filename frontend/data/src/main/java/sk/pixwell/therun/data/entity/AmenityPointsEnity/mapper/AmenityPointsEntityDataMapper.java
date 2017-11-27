package sk.pixwell.therun.data.entity.AmenityPointsEnity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.domain.AmenityPoint;
import sk.pixwell.therun.domain.PointOfStage;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class AmenityPointsEntityDataMapper {

    public static List<AmenityPoint> transform(AmenityPoints pointsEntity) {
        List<AmenityPoint> result = new ArrayList<>();
        AmenityPoint point;
        for (AmenityPoints.Features data :pointsEntity.features) {
            point = new AmenityPoint();
            point.amenityName = data.properties.amenity;
            point.name = data.properties.name;
            point.lat = data.geometry.coordinates.lat;
            point.lng = data.geometry.coordinates.lon;
            result.add(point);
        }
        return result;
    }

}
