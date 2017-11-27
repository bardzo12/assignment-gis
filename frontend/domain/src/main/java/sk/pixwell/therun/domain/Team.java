package sk.pixwell.therun.domain;

import java.io.Serializable;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class Team implements Serializable {

    private int id;
    private String name = "";
    private Boolean favorite;
    private PointOfStage actualPossition;
    private int rank;
    private String fullTime;

    public Team(int id, String name, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }

    public Team() {
    }

    public PointOfStage getActualPossition() {
        return actualPossition;
    }

    public void setActualPossition(PointOfStage actualPossition) {
        this.actualPossition = actualPossition;
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

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setFullTime(String timeFull) {
        this.fullTime = timeFull;
    }

    public String getFullTime() {
        return fullTime;
    }
}
