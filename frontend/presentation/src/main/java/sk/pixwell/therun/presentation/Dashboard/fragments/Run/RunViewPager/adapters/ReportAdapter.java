package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.RankViewPager.adapters.TeamAdapter;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {


    private List<Report> reportList;
    private OnItemClickedListener mListener;
    private Context mContext;

    public ReportAdapter(Context context, List<Report> reportList, OnItemClickedListener mListener){
        this.reportList = reportList;
        this.mListener = mListener;
        this.mContext = context;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 0){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_report, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_report_light, parent, false);
        }
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, final int position) {
        final Report report = reportList.get(position);

        holder.mName.setText(report.getName());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.mDate.setText(Html.fromHtml(report.getDateString(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.mDate.setText(Html.fromHtml(report.getDateString()));
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.mMessage.setText(Html.fromHtml(report.getMessage(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.mMessage.setText(Html.fromHtml(report.getMessage()));
        }

        holder.mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mListener.onItemClicked((int) view.getTag());
                mListener.onItemClicked(position);
            }
        });

        if(position != 0) {
            holder.mName.setTextColor(UIUtils.getColorWrapper(mContext, R.color.lightTextColor));
            holder.mDate.setTextColor(UIUtils.getColorWrapper(mContext, R.color.lightTextColor));
            holder.mMessage.setTextColor(UIUtils.getColorWrapper(mContext, R.color.lightTextColor));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        if (reportList != null) {
            return reportList.size();
        }else {
            return 0;
        }
    }

    public void setList(List<Report> list) {
        Timber.d("Set list of items %d", list.size());
        this.reportList = list;
        notifyDataSetChanged();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dateTextView)
        TextView mDate;

        @BindView(R.id.nameTextView)
        TextView mName;

        @BindView(R.id.messageTextView)
        TextView mMessage;

        @BindView(R.id.background)
        RelativeLayout mBackground;

        ReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
