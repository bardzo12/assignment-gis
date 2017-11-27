package sk.pixwell.therun.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tomáš Baránek on 13.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class Report {

    private int id;
    private String name;
    private Date date;
    private String message;

    public Report(int id, String name, Date date, String message) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public Report() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        String text = dateFormat.format(date) + " o " + timeFormat.format(date);
        return text;
    }

    public void setDate(String created_at) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(created_at);
            this.date = date;
        } catch (Exception ex){
        }
    }
}
