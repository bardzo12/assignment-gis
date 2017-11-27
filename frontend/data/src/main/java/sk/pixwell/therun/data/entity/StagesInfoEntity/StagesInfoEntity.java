package sk.pixwell.therun.data.entity.StagesInfoEntity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tomáš Baránek on 23.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesInfoEntity {

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Beacon {
        @SerializedName("uuid")
        public String uuid;
        @SerializedName("major")
        public String major;
        @SerializedName("minor")
        public String minor;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getMinor() {
            return minor;
        }

        public void setMinor(String minor) {
            this.minor = minor;
        }
    }

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("code")
        public String code;
        @SerializedName("beacon")
        public Beacon beacon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Beacon getBeacon() {
            return beacon;
        }

        public void setBeacon(Beacon beacon) {
            this.beacon = beacon;
        }
    }
}
