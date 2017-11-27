package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.VerifiedPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui.VerifiedView;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.adapters.OnItemClickedListener;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.adapters.TeamAdapter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.presenters.FavoriteListPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class FavoriteListFragment extends TheRunFragment implements FavoriteListView {

    @Inject
    FavoriteListPresenter mPresenter;

    @BindView(R.id.recyclerViewTeam)
    RecyclerView mListView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipe;

    @BindView(R.id.errorMessageTextView)
    TextView mError;

    private OnItemClickedListener mListener;

    public static FavoriteListFragment newInstance() {
        FavoriteListFragment fragment = new FavoriteListFragment();
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
        View layout = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        initializeListener();
        mPresenter.initializeList();
        mSwipe.setRefreshing(true);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipe.setRefreshing(true);
                mPresenter.initializeList();
            }
        });
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
    public void setList(List<Team> teamsList) {
        if(teamsList.size() == 0){
            mListView.setVisibility(View.GONE);
            mError.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mError.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mListView.setLayoutManager(layoutManager);
        TeamAdapter newsFeedAdapter = new TeamAdapter(getContext(), teamsList, mListener, true);
        mListView.setAdapter(newsFeedAdapter);
    }

    @Override
    public void changeFavorite() {
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.changeFavoriteInFavoriteFragment();
    }

    @Override
    public void setRefreshing(boolean result) {
        mSwipe.setRefreshing(result);
    }

    private void initializeListener() {
        mListener = new OnItemClickedListener() {
            @Override
            public void onItemClicked(int postId, List<Team> result) {
                mPresenter.onItemClicked(postId, result);
            }
        };
    }
}
