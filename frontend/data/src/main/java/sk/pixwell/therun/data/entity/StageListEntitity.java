package sk.pixwell.therun.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageListEntitity {


    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("incline")
    public int incline;
    @SerializedName("decline")
    public int decline;
    @SerializedName("distance")
    public int distance;
    @SerializedName("distance_from_start")
    public int distance_from_start;
    @SerializedName("from")
    public From from;
    @SerializedName("to")
    public To to;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIncline() {
        return incline;
    }

    public void setIncline(int incline) {
        this.incline = incline;
    }

    public int getDecline() {
        return decline;
    }

    public void setDecline(int decline) {
        this.decline = decline;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance_from_start() {
        return distance_from_start;
    }

    public void setDistance_from_start(int distance_from_start) {
        this.distance_from_start = distance_from_start;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public static class From {
        @SerializedName("track_segment_id")
        public int track_segment_id;
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;

        public int getTrack_segment_id() {
            return track_segment_id;
        }

        public void setTrack_segment_id(int track_segment_id) {
            this.track_segment_id = track_segment_id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class To {
        @SerializedName("track_segment_id")
        public int track_segment_id;
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;

        public int getTrack_segment_id() {
            return track_segment_id;
        }

        public void setTrack_segment_id(int track_segment_id) {
            this.track_segment_id = track_segment_id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
