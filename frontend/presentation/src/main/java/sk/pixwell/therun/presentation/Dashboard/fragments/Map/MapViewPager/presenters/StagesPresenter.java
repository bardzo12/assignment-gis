package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import sk.pixwell.therun.data.executor.GetStages;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui.StagesView;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesSingleton;


/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesPresenter implements Presenter<StagesView> {

    private StagesView view;
    private Context context;
    List<Stage> stageList;
    GetStages getStagesUseCase;

    public StagesPresenter(Context context, GetStages getStagesUseCase) {
        this.getStagesUseCase = getStagesUseCase;
        this.context = context;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(@NonNull StagesView view) {
        this.view = view;
    }

    public void initializeList(){
        stageList = StagesSingleton.getInstance().getStages();
        view.setList(stageList);
    }

    public void navigateToDetailDialog(int postId) {
        view.navigateToDetailDialogStage(stageList.get(postId));
    }

    public void navigateToAminity(int postId) {
        view.navigateToAminity(stageList.get(postId));
    }
}
