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
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {


    private List<Team> teamList;
    private OnItemClickedListener mListener;
    private Context mContext;
    private Boolean favorite;

    public TeamAdapter(Context context, List<Team> teamList, OnItemClickedListener mListener, Boolean favorite){
        this.teamList = teamList;
        this.mListener = mListener;
        this.mContext = context;
        this.favorite = favorite;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 0){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_team, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_team_light, parent, false);
        }
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TeamViewHolder holder, final int position) {
        final Team team = teamList.get(position);

        if(position != 0 && position % 2 == 1)
            holder.mBackground.setBackgroundColor(UIUtils.getColorWrapper(mContext,R.color.backgroundColor2));
        holder.mName.setText(team.getName() + " " + team.getFullTime());
        if(!favorite)
            holder.mName.setText(String.valueOf(position+1) + " " + team.getName());
        else
            holder.mName.setText(String.valueOf(team.getRank()) + " " + team.getName());
        if(team.getFavorite())
            holder.mStar.setBackgroundResource(R.drawable.star_selected);
        else
            holder.mStar.setBackgroundResource(R.drawable.star_no_selected);
        holder.mTime.setText(team.getFullTime());

        holder.mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(team.getFavorite()) {
                    holder.mStar.setBackgroundResource(R.drawable.star_no_selected);
                    teamList.get(position).setFavorite(false);
                } else {
                    holder.mStar.setBackgroundResource(R.drawable.star_selected);
                    teamList.get(position).setFavorite(true);
                }
                mListener.onItemClicked(position, teamList);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        if (teamList != null) {
            return teamList.size();
        }else {
            return 0;
        }
    }

    public void setList(List<Team> list) {
        Timber.d("Set list of items %d", list.size());
        this.teamList = list;
        notifyDataSetChanged();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.timeTextView)
        TextView mTime;

        @BindView(R.id.nameTextView)
        TextView mName;

        @BindView(R.id.starImageView)
        ImageView mStar;

        @BindView(R.id.background)
        RelativeLayout mBackground;

        TeamViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
