package sk.pixwell.therun.data.entity.token;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TokenEntity {
    @SerializedName("token")
    private String token;

    private Integer statusCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** sk.pixwell.therun.domain.Token Details *****\n");
        stringBuilder.append("sk.pixwell.therun.domain.Token =" + this.getToken() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
