package sk.pixwell.therun.data.entity.SegmentsEntity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SegmentsEntity {


    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("segment_id")
        public int segment_id;
        @SerializedName("team_id")
        public int team_id;
        @SerializedName("run_minutes")
        public String run_minutes;
        @SerializedName("speed")
        public String speed;
        @SerializedName("started_at")
        public String started_at;
        @SerializedName("updated_at")
        public String updated_at;

        public int getSegment_id() {
            return segment_id;
        }

        public void setSegment_id(int segment_id) {
            this.segment_id = segment_id;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public String getRun_minutes() {
            return run_minutes;
        }

        public void setRun_minutes(String run_minutes) {
            this.run_minutes = run_minutes;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getStarted_at() {
            return started_at;
        }

        public void setStarted_at(String started_at) {
            this.started_at = started_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
