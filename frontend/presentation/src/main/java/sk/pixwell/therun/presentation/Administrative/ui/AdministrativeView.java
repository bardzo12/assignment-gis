package sk.pixwell.therun.presentation.Administrative.ui;

import java.util.List;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public interface AdministrativeView {
    void navigateToAdministrativeDetail();
    void setKraje(List<String> result);
    void setOkresy(List<String> result);
}
