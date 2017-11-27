package sk.pixwell.therun.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class LandUseEntity {

    @SerializedName("result")
    public List<Result> result;

    public static class Result {
        @SerializedName("landuse")
        public String landuse;
        @SerializedName("length")
        public String length;
    }
}
