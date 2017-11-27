package sk.pixwell.therun.data.entity.ReportsEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class ReportsEntityJsonMapper {
    private final Gson gson;

    @Inject
    public ReportsEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link ReportsEntity}.
     *
     * @param reportsJsonResponse A json representing a token.
     * @return {@link StagesEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public ReportsEntity transformReportsEntity(String reportsJsonResponse) throws JsonSyntaxException {
        try {
            Type reportsEntityType = new TypeToken<ReportsEntity>() {}.getType();
            ReportsEntity reportsEntity = this.gson.fromJson(reportsJsonResponse, reportsEntityType);
            return reportsEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }

    }
}
