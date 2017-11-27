package sk.pixwell.therun.presentation.Stage_detail.injection;

import dagger.Component;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetail;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
@PerActivity
@Component (dependencies = TheRunApplicationComponent.class, modules = {TheRunActivityModule.class, StageDetailModule.class})
public interface StageDetailComponent {
    void inject(StageDetail stageDetail);
}
