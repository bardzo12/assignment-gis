package sk.pixwell.therun.data.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class NameListEntityJsonMapper {
    private final Gson gson;

    @Inject
    public NameListEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link NameListEntity}.
     *
     * @param stagesInfoJsonResponse A json representing a token.
     * @return {@link NameListEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public NameListEntity transform(String stagesInfoJsonResponse) throws JsonSyntaxException {
        try {
            Type stagesInfoEntityType = new TypeToken<NameListEntity>() {}.getType();
            NameListEntity stagesInfoEntity = this.gson.fromJson(stagesInfoJsonResponse, stagesInfoEntityType);

            return stagesInfoEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
