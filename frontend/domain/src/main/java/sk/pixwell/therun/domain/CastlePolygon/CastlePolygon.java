package sk.pixwell.therun.domain.CastlePolygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomáš Baránek on 27.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class CastlePolygon {
    public String name;
    public List<Coordinates> polygon = new ArrayList<>();

    public static class Coordinates {
        public double lat;
        public double lng;
    }
}
