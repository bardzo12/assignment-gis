package sk.pixwell.therun.presentation;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 30.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class EndInfoSingleton {

    private static EndInfoSingleton mInstance = null;

    private List<StageInfo> mStages;

    private EndInfoSingleton(){
        mStages = new ArrayList<>();
        mStages.add(new StageInfo(999,"DEADBEEF-CA1F-BABE-FEED-FEEDC0DEFACE", "1", "201","end_east"));
        mStages.add(new StageInfo(888,"DEADBEEF-CA1F-BABE-FEED-FEEDC0DEFACE", "1", "28", "end_west"));
    }

    public static EndInfoSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new EndInfoSingleton();
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
