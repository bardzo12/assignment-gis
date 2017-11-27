package sk.pixwell.therun.data.entity.TeamsEntity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamsEntity {

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Last_position {
        @SerializedName("distance_from_start")
        public double distance_from_start;
        @SerializedName("elapsed_time_from_segment_start")
        public int elapsed_time_from_segment_start;
        @SerializedName("track_point_id")
        public int track_point_id;
        @SerializedName("segment_id")
        public int segment_id;
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;

        public double getDistance_from_start() {
            return distance_from_start;
        }

        public void setDistance_from_start(int distance_from_start) {
            this.distance_from_start = distance_from_start;
        }

        public int getElapsed_time_from_segment_start() {
            return elapsed_time_from_segment_start;
        }

        public void setElapsed_time_from_segment_start(int elapsed_time_from_segment_start) {
            this.elapsed_time_from_segment_start = elapsed_time_from_segment_start;
        }

        public int getTrack_point_id() {
            return track_point_id;
        }

        public void setTrack_point_id(int track_point_id) {
            this.track_point_id = track_point_id;
        }

        public int getSegment_id() {
            return segment_id;
        }

        public void setSegment_id(int segment_id) {
            this.segment_id = segment_id;
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

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("min_runners")
        public int min_runners;
        @SerializedName("runners")
        public int runners;
        @SerializedName("max_runners")
        public int max_runners;
        @SerializedName("type")
        public String type;
        @SerializedName("section")
        public String section;
        @SerializedName("team_segments")
        public int team_segments;
        @SerializedName("time_full")
        public String timeFull;
        @SerializedName("last_position")
        public Last_position last_position;

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

        public int getMin_runners() {
            return min_runners;
        }

        public void setMin_runners(int min_runners) {
            this.min_runners = min_runners;
        }

        public int getRunners() {
            return runners;
        }

        public void setRunners(int runners) {
            this.runners = runners;
        }

        public int getMax_runners() {
            return max_runners;
        }

        public void setMax_runners(int max_runners) {
            this.max_runners = max_runners;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public int getTeam_segments() {
            return team_segments;
        }

        public void setTeam_segments(int team_segments) {
            this.team_segments = team_segments;
        }

        public Last_position getLast_position() {
            return last_position;
        }

        public void setLast_position(Last_position last_position) {
            this.last_position = last_position;
        }

        public String getTimeFull() {
            return timeFull;
        }

        public void setTimeFull(String timeFull) {
            this.timeFull = timeFull;
        }
    }
}
