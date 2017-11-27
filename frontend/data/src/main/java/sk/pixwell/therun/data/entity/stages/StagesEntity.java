package sk.pixwell.therun.data.entity.stages;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sk.pixwell.therun.data.entity.points.PointsEntity;

/**
 * Created by Tomáš Baránek on 15.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesEntity {

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class From {
        @SerializedName("data")
        public Point data;

        public Point getData() {
            return data;
        }

        public void setData(Point data) {
            this.data = data;
        }
    }

    public static class Point {
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

    public static class To {
        @SerializedName("data")
        public Point data;

        public Point getData() {
            return data;
        }

        public void setData(Point data) {
            this.data = data;
        }
    }

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("name_from")
        public String name_from;
        @SerializedName("name_to")
        public String name_to;
        @SerializedName("description")
        public String description;
        @SerializedName("incline")
        public int incline;
        @SerializedName("decline")
        public int decline;
        @SerializedName("distance")
        public int distance;
        @SerializedName("distance_from_start")
        public Double distance_from_start;
        @SerializedName("from")
        public From from;
        @SerializedName("to")
        public To to;
        @SerializedName("points")
        public PointsEntity points;
        @SerializedName("min_elevation")
        public float min_elevation;
        @SerializedName("max_elevation")
        public float max_elevation;
        @SerializedName("Y_AxisMinimum")
        public float Y_AxisMinimum;
        @SerializedName("Y_AxisMaximum")
        public float Y_AxisMaximum;

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

        public String getName_from() {
            return name_from;
        }

        public void setName_from(String name_from) {
            this.name_from = name_from;
        }

        public String getName_to() {
            return name_to;
        }

        public void setName_to(String name_to) {
            this.name_to = name_to;
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

        public Double getDistance_from_start() {
            return distance_from_start;
        }

        public void setDistance_from_start(Double distance_from_start) {
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

        public PointsEntity getPoints() {
            return points;
        }

        public void setPoints(PointsEntity points) {
            this.points = points;
        }

        public float getMin_elevation() {
            return min_elevation;
        }

        public void setMin_elevation(float min_elevation) {
            this.min_elevation = min_elevation;
        }

        public float getMax_elevation() {
            return max_elevation;
        }

        public void setMax_elevation(float max_elevation) {
            this.max_elevation = max_elevation;
        }

        public float getY_AxisMinimum() {
            return Y_AxisMinimum;
        }

        public void setY_AxisMinimum(float y_AxisMinimum) {
            Y_AxisMinimum = y_AxisMinimum;
        }

        public float getY_AxisMaximum() {
            return Y_AxisMaximum;
        }

        public void setY_AxisMaximum(float y_AxisMaximum) {
            Y_AxisMaximum = y_AxisMaximum;
        }
    }
}