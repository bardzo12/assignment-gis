package sk.pixwell.therun.data.entity.stages.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.stages.StagesEntity;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesEntityJsonMapper {
    private final Gson gson;

    @Inject
    public StagesEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link StagesEntity}.
     *
     * @param tokenJsonResponse A json representing a token.
     * @return {@link StagesEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public StagesEntity transformStagesEntity(String tokenJsonResponse) throws JsonSyntaxException {
        Timber.i("PR: Začal som JSON MAPPING");
        try {
            Type tokenEntityType = new TypeToken<StagesEntity>() {}.getType();
            StagesEntity tokenEntity = this.gson.fromJson(tokenJsonResponse, tokenEntityType);
            Timber.i("PR: Skončil som JSON MAPPING");
            return tokenEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }

    }
}
