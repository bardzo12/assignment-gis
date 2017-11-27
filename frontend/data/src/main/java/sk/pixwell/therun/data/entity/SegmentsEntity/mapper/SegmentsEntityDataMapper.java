package sk.pixwell.therun.data.entity.SegmentsEntity.mapper;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Segment;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SegmentsEntityDataMapper {

    public static List<Segment> transform(SegmentsEntity segmentsEntity) {
        List<SegmentsEntity.Data> dataList = segmentsEntity.getData();
        List<Segment> segments = new ArrayList<>();
        Segment segment;
        for (SegmentsEntity.Data data :dataList) {
            segment = new Segment();
            segment.setId(data.getSegment_id());
            segment.setStarted_at(data.getStarted_at());
            segments.add(segment);
        }
        return segments;
    }

}
