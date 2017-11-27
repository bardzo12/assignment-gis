package sk.pixwell.therun.data.entity.points.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class PointsEntityJsonMapper {
    private final Gson gson;

    @Inject
    public PointsEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link PointsEntity}.
     *
     * @param pointsJsonResponse A json representing a token.
     * @return {@link PointsEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public PointsEntity transformPointsEntity(String pointsJsonResponse) throws JsonSyntaxException {
        try {
            Type pointsEntityType = new TypeToken<PointsEntity>() {}.getType();
            PointsEntity pointsEntity = this.gson.fromJson(pointsJsonResponse, pointsEntityType);

            return pointsEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
