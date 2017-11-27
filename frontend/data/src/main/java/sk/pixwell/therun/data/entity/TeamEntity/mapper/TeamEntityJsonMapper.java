package sk.pixwell.therun.data.entity.TeamEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.TeamEntity.TeamEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamEntityJsonMapper {
    private final Gson gson;

    @Inject
    public TeamEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link TeamsEntity}.
     *
     * @param teamJsonResponse A json representing a token.
     * @return {@link TeamsEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public TeamEntity transformTeamEntity(String teamJsonResponse) throws JsonSyntaxException {
        try {
            Type teamEntityType = new TypeToken<TeamEntity>() {}.getType();
            TeamEntity teamEntity = this.gson.fromJson(teamJsonResponse, teamEntityType);

            return teamEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
