package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamanland.fonticon.FontIconDrawable;
import com.shamanland.fonticon.FontIconView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.adapters.ReportAdapter;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 16.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class VerifiedStagesAdapter extends RecyclerView.Adapter<VerifiedStagesAdapter.VerifiedStagesViewHolder> {


    private List<Stage> stageList;
    private OnItemClickedListener mListener;
    private Context mContext;

    public VerifiedStagesAdapter(Context context, List<Stage> runnerList, OnItemClickedListener mListener){
        this.stageList = runnerList;
        this.mListener = mListener;
        this.mContext = context;
    }

    @Override
    public VerifiedStagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 0){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_verified_stage, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_verified_stage_light, parent, false);
        }
        return new VerifiedStagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VerifiedStagesViewHolder holder, final int position) {
        final Stage stage = stageList.get(position);

        holder.mName.setText(String.valueOf(position + 1) + " " + stage.getName());
        if(stage.getiBeaconCheckIn()) {
          holder.mCheck.setImageDrawable(UIUtils.getBackgroundWrapper(mContext, R.drawable.etapa_checkin_dark));
        }else {
            holder.mCheck.setImageDrawable(UIUtils.getBackgroundWrapper(mContext, R.drawable.stage_checkin));
        }

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

    class VerifiedStagesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView mName;

        @BindView(R.id.checkImageView)
        ImageView mCheck;


        VerifiedStagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
