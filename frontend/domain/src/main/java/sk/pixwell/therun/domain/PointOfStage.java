package sk.pixwell.therun.domain;

import java.io.Serializable;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class PointOfStage implements Serializable {

    private int track_segment_start;
    private Double latitude;
    private Double longitude;
    private Double elevation;

    public int getTrack_segment_start() {
        return track_segment_start;
    }

    public void setTrack_segment_start(int track_segment_start) {
        this.track_segment_start = track_segment_start;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }
}
