package sk.pixwell.therun.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class Stage implements Serializable {

    private int id;
    private String name;
    private String description;
    private int incline;
    private int decline;
    private int distance;
    private Double distanceFromStart;
    private PointOfStage start;
    private PointOfStage end;
    private Boolean qrCheckIn = false;
    private Boolean iBeaconCheckIn = false;
    private List<PointOfStage> points;
    private float min_elevation;
    private float max_elevation;
    private float Y_AxisMinimum;
    private float Y_AxisMaximum;

    public Stage() {

    }

    public Stage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Stage(int id, String name, String description, int incline, int decline, int distance, Double distanceFromStart, PointOfStage start, PointOfStage end) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.incline = incline;
        this.decline = decline;
        this.distance = distance;
        this.distanceFromStart = distanceFromStart;
        this.start = start;
        this.end = end;
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

    public Double getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(Double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public List<PointOfStage> getPoints() {
        return points;
    }

    public void setPoints(List<PointOfStage> points) {
        this.points = points;
    }

    public PointOfStage getStart() {
        return start;
    }

    public void setStart(PointOfStage start) {
        this.start = start;
    }

    public PointOfStage getEnd() {
        return end;
    }

    public void setEnd(PointOfStage end) {
        this.end = end;
    }

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

    public Boolean getQrCheckIn() {
        return qrCheckIn;
    }

    public void setQrCheckIn(Boolean qrCheckIn) {
        this.qrCheckIn = qrCheckIn;
    }

    public Boolean getiBeaconCheckIn() {
        return iBeaconCheckIn;
    }

    public void setiBeaconCheckIn(Boolean iBeaconCheckIn) {
        this.iBeaconCheckIn = iBeaconCheckIn;
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
