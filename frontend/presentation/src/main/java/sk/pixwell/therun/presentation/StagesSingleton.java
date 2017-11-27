package sk.pixwell.therun.presentation;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.domain.Stage;

/**
 * Created by Tomáš Baránek on 19.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesSingleton {

    private static StagesSingleton mInstance = null;

    private List<Stage> mStages;

    private StagesSingleton(){
        mStages = new ArrayList<>();
    }

    public static StagesSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new StagesSingleton();
        }
        return mInstance;
    }

    public List<Stage> getStages(){
        return this.mStages;
    }

    public void setmStages(List<Stage> value){
        mStages = value;
    }
}
