package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.StageListener;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.adapters.OnItemClickedListener;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.adapters.StageAdapter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters.StagesPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Detail_dialog.DetailDialogActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StagesFragment extends TheRunFragment implements StagesView {

    @Inject
    StagesPresenter mPresenter;

    @BindView(R.id.recyclerViewTeam)
    RecyclerView mListView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipe;

    private OnItemClickedListener mListener;
    private StageListener mStageListener;

    public static StagesFragment newInstance() {
        StagesFragment fragment = new StagesFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_stage_list, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        initializeListener();
        mSwipe.setEnabled(false);
        mPresenter.initializeList();
    }

    public void setRefreshing(Boolean result){
        mSwipe.setRefreshing(result);
    }

    @Override
    public void navigateToAminity(Stage stage) {
        mStageListener.onClickStageAminity(stage);
    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStageListener= (StageListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setList(List<Stage> teamsList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mListView.setLayoutManager(layoutManager);
        StageAdapter newsFeedAdapter = new StageAdapter(getContext(), teamsList, mListener);
        mListView.setAdapter(newsFeedAdapter);
    }

    @Override
    public void navigateToDetailStage(Stage stage) {
        mStageListener.navigateToStage(stage);
    }

    @Override
    public void navigateToDetailDialogStage(Stage stage) {
        mStageListener.navigateToStageDialog(stage);
    }

    private void initializeListener() {

        mListener = new OnItemClickedListener() {
            @Override
            public void onItemClicked(int postId) {
                mPresenter.navigateToDetailDialog(postId);
            }

            @Override
            public void onAminityClicked(int postId) {
                mPresenter.navigateToAminity(postId);
            }
        };
    }

}
