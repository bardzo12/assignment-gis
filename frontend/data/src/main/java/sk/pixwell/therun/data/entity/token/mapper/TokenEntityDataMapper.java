package sk.pixwell.therun.data.entity.token.mapper;

import javax.inject.Inject;

import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.domain.Token;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TokenEntityDataMapper {
    @Inject
    public TokenEntityDataMapper(){}
    /**
     * Transform a {@link TokenEntity} into an {@link Token}.
     *
     * @param tokenEntity Object to be transformed.
     * @return {@link Token} if valid {@link TokenEntity} otherwise null.
     */

    public static Token transform(TokenEntity tokenEntity) {
        Token token = null;
        if (tokenEntity != null) {
            token = new Token();
            token.setToken(tokenEntity.getToken());
        }

        return token;
    }

    public static TokenEntity transform(Token token) {
        TokenEntity tokenEntity = null;
        if (token != null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setToken(token.getToken());
        }

        return tokenEntity;
    }

}
