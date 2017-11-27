package sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class RunnerAdapter extends RecyclerView.Adapter<RunnerAdapter.TeamViewHolder> {


    private List<Runner> runnerList;
    private OnItemClickedListener mListener;
    private Context mContext;

    public RunnerAdapter(Context context, List<Runner> runnerList, OnItemClickedListener mListener){
        this.runnerList = runnerList;
        this.mListener = mListener;
        this.mContext = context;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_runner, parent, false);
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, final int position) {
        final Runner runner = runnerList.get(position);

        if(position != 0 && position % 2 == 1)
            holder.mBackground.setBackgroundColor(UIUtils.getColorWrapper(mContext,R.color.backgroundColor2));
        holder.mName.setText(runner.getName());
        holder.mRank.setText(String.valueOf(position+1));
        holder.mTeam.setText(runner.getTeam().getName());

        holder.mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mListener.onItemClicked((int) view.getTag());
                mListener.onItemClicked(position, null);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (runnerList != null) {
            return runnerList.size();
        }else {
            return 0;
        }
    }

    public void setList(List<Runner> list) {
        Timber.d("Set list of items %d", list.size());
        this.runnerList = list;
        notifyDataSetChanged();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rankTextView)
        TextView mRank;

        @BindView(R.id.nameTextView)
        TextView mName;

        @BindView(R.id.teamTextView)
        TextView mTeam;

        @BindView(R.id.background)
        RelativeLayout mBackground;

        TeamViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
