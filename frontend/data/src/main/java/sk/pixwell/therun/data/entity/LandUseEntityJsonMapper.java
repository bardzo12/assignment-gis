package sk.pixwell.therun.data.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LandUseEntityJsonMapper {
    private final Gson gson;

    @Inject
    public LandUseEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link LandUseEntity}.
     *
     * @param stagesInfoJsonResponse A json representing a token.
     * @return {@link LandUseEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public LandUseEntity transform(String stagesInfoJsonResponse) throws JsonSyntaxException {
        try {
            Type stagesInfoEntityType = new TypeToken<LandUseEntity>() {}.getType();
            LandUseEntity stagesInfoEntity = this.gson.fromJson(stagesInfoJsonResponse, stagesInfoEntityType);

            return stagesInfoEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
