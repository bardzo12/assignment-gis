package sk.pixwell.therun.data.entity.StagesInfoEntity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 23.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesInfoEntityDataMapper {

    public static List<StageInfo> transform(StagesInfoEntity stagesInfoEntity) {
        List<StagesInfoEntity.Data> stagesInfo = stagesInfoEntity.getData();
        List<StageInfo> stageInfoList = new ArrayList<>();
        for (StagesInfoEntity.Data data :stagesInfo) {
            StageInfo stageInfo = new StageInfo();
            stageInfo.setId(data.getId());
            stageInfo.setMajor(data.getBeacon().getMajor());
            stageInfo.setMinor(data.getBeacon().getMinor());
            stageInfo.setQrString(data.getCode());
            stageInfo.setUuid(data.getBeacon().getUuid());
            stageInfoList.add(stageInfo);
        }
        return stageInfoList;
    }

}
