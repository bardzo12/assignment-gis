package sk.pixwell.therun.data.entity.TeamsEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamsEntityJsonMapper {
    private final Gson gson;

    @Inject
    public TeamsEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link TeamsEntity}.
     *
     * @param teamsJsonResponse A json representing a token.
     * @return {@link TeamsEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public TeamsEntity transformTeamsEntity(String teamsJsonResponse) throws JsonSyntaxException {
        try {
            Type teamsEntityType = new TypeToken<TeamsEntity>() {}.getType();
            TeamsEntity teamsEntity = this.gson.fromJson(teamsJsonResponse, teamsEntityType);

            return teamsEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
