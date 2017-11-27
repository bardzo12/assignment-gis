package sk.pixwell.therun.data.entity.AmenityPointsEnity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.points.PointsEntity;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class AmenityPointsEntityJsonMapper {
    private final Gson gson;

    @Inject
    public AmenityPointsEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link AmenityPoints}.
     *
     * @param pointsJsonResponse A json representing a token.
     * @return {@link AmenityPoints}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public AmenityPoints transformPointsEntity(String pointsJsonResponse) throws JsonSyntaxException {
        try {
            Type pointsEntityType = new TypeToken<AmenityPoints>() {}.getType();
            AmenityPoints pointsEntity = this.gson.fromJson(pointsJsonResponse, pointsEntityType);

            return pointsEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
