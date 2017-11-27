package sk.pixwell.therun.data.entity.points;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 15.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class PointsEntity {

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
        @SerializedName("segment_id")
        public int segment_id;
        @SerializedName("elevation")
        public double elevation;
        @SerializedName("distance_from_start")
        public Double distance_from_start;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSegment_id() {
            return segment_id;
        }

        public void setSegment_id(int segment_id) {
            this.segment_id = segment_id;
        }

        public double getElevation() {
            return elevation;
        }

        public void setElevation(double elevation) {
            this.elevation = elevation;
        }

        public Double getDistance_from_start() {
            return distance_from_start;
        }

        public void setDistance_from_start(Double distance_from_start) {
            this.distance_from_start = distance_from_start;
        }
    }
}
