package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {


    private List<Stage> stageList;
    private OnItemClickedListener mListener;
    private Context mContext;

    public StageAdapter(Context context, List<Stage> runnerList, OnItemClickedListener mListener){
        this.stageList = runnerList;
        this.mListener = mListener;
        this.mContext = context;
    }

    @Override
    public StageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 0){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_stage, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_stage_dark, parent, false);
        }
        return new StageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StageViewHolder holder, final int position) {
        final Stage stage = stageList.get(position);

        holder.mName.setText(stage.getName());
        holder.mRank.setText(String.valueOf(stage.getId()) + ".");

        holder.mAminity.setOnClickListener(view -> {
            mListener.onAminityClicked(holder.getAdapterPosition());
        });

        holder.mBackground.setOnClickListener(view -> {

            //mListener.onItemClicked((int) view.getTag());
            mListener.onItemClicked(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        if (stageList != null) {
            return stageList.size();
        }else {
            return 0;
        }
    }

    public void setList(List<Stage> list) {
        Timber.d("Set list of items %d", list.size());
        this.stageList = list;
        notifyDataSetChanged();
    }

    class StageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView mName;

        @BindView(R.id.background)
        LinearLayout mBackground;

        @BindView(R.id.rankTextView)
        TextView mRank;

        @BindView(R.id.gps_aminity_image_button)
        ImageButton mAminity;

        StageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
