package sk.pixwell.therun.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tomáš Baránek on 17.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckIn {

    private String checkInType;
    private Date planedAt;
    private Date checkAt;
    private Date plannedStartAt;
    private Date startedAt;
    private Date finishedAt;

    public CheckIn() {
    }

    public String getCheckInType() {
        return checkInType;
    }

    public void setCheckInType(String checkInType) {
        this.checkInType = checkInType;
    }

    public Date getPlanedAt() {
        return planedAt;
    }

    public void setPlanedAt(Date planedAt) {
        this.planedAt = planedAt;
    }

    public Date getCheckAt() {
        return checkAt;
    }

    public void setCheckAt(Date checkAt) {
        this.checkAt = checkAt;
    }

    public Date getPlannedStartAt() {
        return plannedStartAt;
    }

    public void setPlannedStartAt(Date plannedStartAt) {
        this.plannedStartAt = plannedStartAt;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setCheckAt(String checked_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(checked_at);
            this.checkAt = date;
        } catch (Exception ex){
        }
    }

    public void setPlanedAt(String planned_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(planned_at);
            this.planedAt = date;
        } catch (Exception ex){
        }
    }

    public void setPlannedStartAt(String planned_start_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(planned_start_at);
            this.plannedStartAt = date;
        } catch (Exception ex){
        }
    }

    public void setStartedAt(String started_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(started_at);
            this.startedAt = date;
        } catch (Exception ex){
        }
    }

    public void setFinishedAt(String checked_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(checked_at);
            this.finishedAt = date;
        } catch (Exception ex){
        }
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                "checkInType='" + checkInType + '\'' +
                ", planedAt=" + planedAt +
                ", checkAt=" + checkAt +
                ", plannedStartAt=" + plannedStartAt +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}
