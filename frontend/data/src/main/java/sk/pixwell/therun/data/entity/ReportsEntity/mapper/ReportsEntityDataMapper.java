package sk.pixwell.therun.data.entity.ReportsEntity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.points.mapper.PointsEntityDataMapper;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Stage;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class ReportsEntityDataMapper {

    public static List<Report> transform(ReportsEntity reportsEntity) {
        List<ReportsEntity.Data> dataList = reportsEntity.getData();
        List<Report> reports = new ArrayList<>();
        Report report;
        for (ReportsEntity.Data data :dataList) {
            report = new Report();
            report.setId(data.getId());
            report.setMessage(data.getBody());
            report.setDate(data.getCreated_at());
            report.setName(data.getTitle());
            reports.add(report);
        }
        return reports;
    }

}
