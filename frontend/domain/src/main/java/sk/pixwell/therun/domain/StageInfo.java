package sk.pixwell.therun.domain;

import java.util.Date;

/**
 * Created by Tomáš Baránek on 23.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageInfo {
    private String uuid;
    private String major;
    private String minor;
    private String qrString;
    private int id;
    private Date time;

    public StageInfo() {
    }

    public StageInfo(int id, String uuid, String major, String minor, String qrCode) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.id = id;
        this.qrString = qrCode;
    }

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

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
