package sk.pixwell.therun.data.entity.CheckInEntity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.domain.CheckIn;
import sk.pixwell.therun.domain.PointOfStage;

/**
 * Created by Tomáš Baránek on 17.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInDataMapper {

    public static List<CheckIn> transform(CheckInEntity checkInEntity) {
        List<CheckInEntity.Data> dataList = checkInEntity.getData();
        List<CheckIn> checkInList = new ArrayList<>();
        for (CheckInEntity.Data data :dataList) {
            CheckIn checkIn = new CheckIn();
            checkIn.setCheckAt(data.getChecked_at());
            checkIn.setCheckInType(data.getType());
            checkIn.setPlanedAt(data.getPlanned_at());
            checkIn.setPlannedStartAt(data.getPlanned_start_at());
            checkIn.setStartedAt(data.getStarted_at());
            checkIn.setFinishedAt(data.getFinish_at());
            checkInList.add(checkIn);
        }
        return checkInList;
    }

}
