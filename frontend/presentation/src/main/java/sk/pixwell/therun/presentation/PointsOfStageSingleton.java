package sk.pixwell.therun.presentation;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 19.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class PointsOfStageSingleton {

    private static PointsOfStageSingleton mInstance = null;

    private List<PointOfStage> mPoints;

    private PointsOfStageSingleton(){
        mPoints = new ArrayList<>();
    }

    public static PointsOfStageSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new PointsOfStageSingleton();
        }
        return mInstance;
    }

    public List<PointOfStage> getPoints(){
        return this.mPoints;
    }

    public void setPoints(List<PointOfStage> value){
        mPoints = value;
    }
}
