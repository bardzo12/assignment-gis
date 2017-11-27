package sk.pixwell.therun.data.entity.StagesInfoEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesInfoEntityJsonMapper {
    private final Gson gson;

    @Inject
    public StagesInfoEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link StagesInfoEntity}.
     *
     * @param stagesInfoJsonResponse A json representing a token.
     * @return {@link StagesInfoEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public StagesInfoEntity transformStagesInfoEntity(String stagesInfoJsonResponse) throws JsonSyntaxException {
        try {
            Type stagesInfoEntityType = new TypeToken<StagesInfoEntity>() {}.getType();
            StagesInfoEntity stagesInfoEntity = this.gson.fromJson(stagesInfoJsonResponse, stagesInfoEntityType);

            return stagesInfoEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
