package sk.pixwell.therun.data.entity;

import java.util.Date;

import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 20.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageCheckIn {

    private StageInfo stageInfo;

    public StageCheckIn(StageInfo stageInfo) {
        this.stageInfo = stageInfo;
    }

    public StageInfo getId() {
        return stageInfo;
    }

    public void setId(StageInfo stageInfo) {
        this.stageInfo = stageInfo;
    }

}
