package sk.pixwell.therun.data.entity.CheckInEntity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;

/**
 * Created by Tomáš Baránek on 17.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInEntityJsonMapper {
    private final Gson gson;

    @Inject
    public CheckInEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link CheckInEntity}.
     *
     * @param pointsJsonResponse A json representing a token.
     * @return {@link CheckInEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public CheckInEntity transformCheckInEntity(String pointsJsonResponse) throws JsonSyntaxException {
        try {
            Type checkInEntityType = new TypeToken<CheckInEntity>() {}.getType();
            CheckInEntity checkInEntity = this.gson.fromJson(pointsJsonResponse, checkInEntityType);

            return checkInEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
