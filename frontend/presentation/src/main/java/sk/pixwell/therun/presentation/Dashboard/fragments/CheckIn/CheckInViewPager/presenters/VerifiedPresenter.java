package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.ArrayList;
import java.util.List;

import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.data.executor.GetTeamSegments;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Segment;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.subscriber.DefaultSubscriber;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.VerifiedView;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.iBeaconCheckInView;
import sk.pixwell.therun.presentation.Presenter;
import sk.pixwell.therun.presentation.StagesSingleton;
import timber.log.Timber;


/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class VerifiedPresenter implements Presenter<VerifiedView> {

    private VerifiedView view;
    private Context context;
    private List<Stage> stageList;
    private GetTeamSegments getTeamSegments;

    public VerifiedPresenter(Context context, GetTeamSegments getTeamSegments) {
        this.context = context;
        this.getTeamSegments = getTeamSegments;
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
    public void setView(@NonNull VerifiedView view) {
        this.view = view;
    }


    public void initializeList(){
        stageList = new ArrayList<>();
        getTeamSegments.execute(new GetTeamSegmentsSubscriber());
    }

    @RxLogSubscriber
    private class GetTeamSegmentsSubscriber extends DefaultSubscriber<List<Segment>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if(e instanceof UpdateException) {
                view.onUpdateNeeded();
            }
            view.setRefreshing(false);
            Timber.i("Result error: %s", e.getMessage());
        }

        @SuppressLint("TimberArgTypes")
        @Override
        public void onNext(List<Segment> result) {
            List<Stage> stages = StagesSingleton.getInstance().getStages();

            List<Stage> stageList = new ArrayList<>();

            for(Segment segment : result)
                for(int i = 0; i < stages.size(); i++){
                    if(stages.get(i).getId() == segment.getId())
                        if(segment.getStarted_at() != null) {
                            stages.get(i).setiBeaconCheckIn(true);
                            stageList.add(stages.get(i));
                        }else{
                            stages.get(i).setiBeaconCheckIn(false);
                            stageList.add(stages.get(i));
                        }
                }

            view.setList(stageList);
            view.setRefreshing(false);
        }
    }

    public void onItemClicked(int postId) {
        Timber.d("Item click: %d",  postId);
    }
}
