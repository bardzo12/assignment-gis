package sk.pixwell.therun.presentation.Detail_dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetail;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.walkthrough.ui.WalkthroughActivity;

/**
 * Created by Tomáš Baránek on 19.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class DetailDialogActivity extends TheRunActivity {

    private Stage stage;

    private static final String INTENT_EXTRA_PARAM_STAGE = "INTENT_PARAM_STAGE";
    private static final String INSTANCE_STATE_PARAM_STAGE = "STATE_PARAM_STAGE";

    @BindView(R.id.distanceTextView)
    TextView mDistance;

    @BindView(R.id.elevationTextView)
    TextView mElevation;

    @BindView(R.id.stageNumberTextView)
    TextView mStageNumber;

    @BindView(R.id.nameTextView)
    TextView mName;

    @BindView(R.id.chart1)
    LineChart chart;

    @OnClick(R.id.background)
    void clickParent(){
        finish();
    }

    @OnClick(R.id.detailButton)
    void detailActivity(){
        navigator.navigateToStageDetail(this, stage, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dialog);

        ButterKnife.bind(this);
        initializeActivity(savedInstanceState);
        initializeLayout();
        this.setFinishOnTouchOutside(true);

        // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }

    private void initializeLayout() {
        if(stage != null) {
            mDistance.setText(String.valueOf(stage.getDistance()) + " M");
            mName.setText(stage.getName());
            mStageNumber.setText(String.valueOf(stage.getId()) + ". etapa");
            mElevation.setText(String.valueOf(stage.getIncline() - stage.getDecline()) + " M");
            setElevation(stage.getPoints(), stage.getDistance(), stage.getY_AxisMinimum(), stage.getY_AxisMaximum());
        }
    }

    private void setElevation(final List<PointOfStage> points, final int distance, final float minY, final float maxY) {
        ArrayList<Entry> values = new ArrayList<>();
        float i =  0;
        for(PointOfStage data : points) {
            i = i +1;
            values.add(new Entry(i, Float.valueOf(String.valueOf(data.getElevation()))));
        }

        LineDataSet dataSet = new LineDataSet(values, "Label");

        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircleHole(true);
        dataSet.setColor(UIUtils.getColorWrapper(this, R.color.colorPrimary));

        dataSet.setDrawCircles(false);

        LineData data = new LineData(dataSet);


        chart.setData(data);

        // style chart
        chart.setBackgroundColor(UIUtils.getColorWrapper(this,R.color.backgroundColor));// use your bg color
        chart.setDescription(null);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);

        chart.setAutoScaleMinMaxEnabled(true);

        XAxis xAxis = chart.getXAxis();

       IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(((distance / points.size()) * value) / 1000);
            }

        };
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(formatter);
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxis = chart.getAxisLeft();
        YAxis yAxis2 = chart.getAxisRight();
        yAxis2.setEnabled(false);

        yAxis.setGranularity(1);
        yAxis.setLabelCount(6, true);
        yAxis.setEnabled(true);
        yAxis.setAxisMinimum(minY);
        yAxis.setAxisMaximum(maxY);

        // hide legend
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        chart.invalidate();
        chart.setTouchEnabled(false);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.stage = (Stage) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_STAGE);
        } else {
            this.stage = (Stage) savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_STAGE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(INSTANCE_STATE_PARAM_STAGE, this.stage);
        }
        super.onSaveInstanceState(outState);
    }

    public static Intent getCallingIntent(Context context, Stage stage) {
        Intent callingIntent = new Intent(context, DetailDialogActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_STAGE, stage);
        return callingIntent;
    }

}
