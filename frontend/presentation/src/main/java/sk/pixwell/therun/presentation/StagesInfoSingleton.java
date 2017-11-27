package sk.pixwell.therun.presentation;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 23.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesInfoSingleton {

    private static StagesInfoSingleton mInstance = null;

    private List<StageInfo> mStages;

    private StagesInfoSingleton(){
        mStages = new ArrayList<>();
    }

    public static StagesInfoSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new StagesInfoSingleton();
        }
        return mInstance;
    }

    public List<StageInfo> getStages(){
        return this.mStages;
    }

    public void setmStages(List<StageInfo> value){
        mStages = value;
    }
}
