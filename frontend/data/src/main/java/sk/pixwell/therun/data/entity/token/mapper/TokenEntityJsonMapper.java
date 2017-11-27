package sk.pixwell.therun.data.entity.token.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.token.TokenEntity;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TokenEntityJsonMapper {
    private final Gson gson;

    @Inject
    public TokenEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link TokenEntity}.
     *
     * @param tokenJsonResponse A json representing a token.
     * @return {@link TokenEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public TokenEntity transformTokenEntity(String tokenJsonResponse) throws JsonSyntaxException {
        try {
            Type tokenEntityType = new TypeToken<TokenEntity>() {}.getType();
            TokenEntity tokenEntity = this.gson.fromJson(tokenJsonResponse, tokenEntityType);

            return tokenEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
