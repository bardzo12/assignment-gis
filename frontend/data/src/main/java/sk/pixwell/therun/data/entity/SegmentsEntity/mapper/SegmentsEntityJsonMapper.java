package sk.pixwell.therun.data.entity.SegmentsEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.domain.Segment;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SegmentsEntityJsonMapper {
    private final Gson gson;

    @Inject
    public SegmentsEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link ReportsEntity}.
     *
     * @param segmentsJsonResponse A json representing a token.
     * @return {@link SegmentsEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public SegmentsEntity transformSegmentsEntity(String segmentsJsonResponse) throws JsonSyntaxException {
        try {
            Type segmentsEntityType = new TypeToken<SegmentsEntity>() {}.getType();
            SegmentsEntity segmentsEntity = this.gson.fromJson(segmentsJsonResponse, segmentsEntityType);
            return segmentsEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }

    }
}
