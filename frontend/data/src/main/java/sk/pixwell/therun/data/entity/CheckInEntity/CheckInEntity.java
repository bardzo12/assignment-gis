package sk.pixwell.therun.data.entity.CheckInEntity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 17.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInEntity {

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("team_id")
        public int team_id;
        @SerializedName("type")
        public String type;
        @SerializedName("planned_at")
        public String planned_at;
        @SerializedName("checked_at")
        public String checked_at;
        @SerializedName("planned_start_at")
        public String planned_start_at;
        @SerializedName("started_at")
        public String started_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("finished_at")
        public String finish_at;

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlanned_at() {
            return planned_at;
        }

        public void setPlanned_at(String planned_at) {
            this.planned_at = planned_at;
        }

        public String getChecked_at() {
            return checked_at;
        }

        public void setChecked_at(String checked_at) {
            this.checked_at = checked_at;
        }

        public String getPlanned_start_at() {
            return planned_start_at;
        }

        public void setPlanned_start_at(String planned_start_at) {
            this.planned_start_at = planned_start_at;
        }

        public String getStarted_at() {
            return started_at;
        }

        public void setStarted_at(String started_at) {
            this.started_at = started_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getFinish_at() {
            return finish_at;
        }

        public void setFinish_at(String finish_at) {
            this.finish_at = finish_at;
        }
    }
}
