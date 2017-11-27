package sk.pixwell.therun.domain;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class Token {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** sk.pixwell.therun.domain.Token Details *****\n");
        stringBuilder.append("token=" + this.getToken() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}

