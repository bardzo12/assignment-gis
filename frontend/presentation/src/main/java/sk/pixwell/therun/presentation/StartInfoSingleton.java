package sk.pixwell.therun.presentation;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 30.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StartInfoSingleton {

    private static StartInfoSingleton mInstance = null;

    private List<StageInfo> mStages;

    private StartInfoSingleton(){
        mStages = new ArrayList<>();
        mStages.add(new StageInfo(1001,"DEADBEEF-CA1F-BABE-FEED-FEEDC0DEFACE", "1", "21","start_east"));
        mStages.add(new StageInfo(2002,"DEADBEEF-CA1F-BABE-FEED-FEEDC0DEFACE", "1", "109", "start_west"));
    }

    public static StartInfoSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new StartInfoSingleton();
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
