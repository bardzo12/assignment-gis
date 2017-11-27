package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui;

import android.content.Context;
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
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.adapters.OnItemClickedListener;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.adapters.VerifiedStagesAdapter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.VerifiedPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class VerifiedFragment extends TheRunFragment implements VerifiedView {

    @Inject
    VerifiedPresenter mPresenter;

    @BindView(R.id.recyclerViewTeam)
    RecyclerView mListView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipe;

    private OnItemClickedListener mListener;

    public static VerifiedFragment newInstance() {
        VerifiedFragment fragment = new VerifiedFragment();
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
        View layout = inflater.inflate(R.layout.fragment_verified, container, false);

        ButterKnife.bind(this,layout);

        //initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipe.setRefreshing(true);
                mPresenter.initializeList();
            }
        });
        initializeListener();
        mPresenter.initializeList();
    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setList(List<Stage> stagesList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mListView.setLayoutManager(layoutManager);
        VerifiedStagesAdapter newsFeedAdapter = new VerifiedStagesAdapter(getContext(), stagesList, mListener);
        mListView.setAdapter(newsFeedAdapter);
    }

    @Override
    public void setRefreshing(boolean result) {
        mSwipe.setRefreshing(result);
    }

    private void initializeListener() {
        mListener = new OnItemClickedListener() {
            @Override
            public void onItemClicked(int postId) {
                mListener.onItemClicked(postId);
            }
        };
    }
}
