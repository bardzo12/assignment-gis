package sk.pixwell.therun.presentation.shared.activity;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
import android.app.Activity;

import dagger.Component;
import sk.pixwell.therun.presentation.PerActivity;
import sk.pixwell.therun.presentation.shared.application.TheRunApplicationComponent;


@PerActivity
@Component(dependencies = TheRunApplicationComponent.class, modules = TheRunActivityModule.class)
public interface TheRunActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}