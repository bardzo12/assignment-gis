package sk.pixwell.therun.data.entity.AmenityPointsEnity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 20.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class AmenityPoints {

    @SerializedName("type")
    public String type;
    @SerializedName("features")
    public List<Features> features;

    public static class Coordinates {
        @SerializedName("lon")
        public double lon;
        @SerializedName("lat")
        public double lat;
    }

    public static class Geometry {
        @SerializedName("type")
        public String type;
        @SerializedName("coordinates")
        public Coordinates coordinates;
    }

    public static class Properties {
        @SerializedName("name")
        public String name;
        @SerializedName("amenity")
        public String amenity;
    }

    public static class Features {
        @SerializedName("type")
        public String type;
        @SerializedName("geometry")
        public Geometry geometry;
        @SerializedName("properties")
        public Properties properties;
    }
}
