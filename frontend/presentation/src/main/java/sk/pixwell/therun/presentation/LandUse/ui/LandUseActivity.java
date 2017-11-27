package sk.pixwell.therun.presentation.LandUse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Administrative.injection.AdministrativeComponent;
import sk.pixwell.therun.presentation.Administrative.injection.AdministrativeModule;
import sk.pixwell.therun.presentation.Administrative.injection.DaggerAdministrativeComponent;
import sk.pixwell.therun.presentation.Administrative.presenter.AdministrativePresenter;
import sk.pixwell.therun.presentation.Administrative.ui.AdministrativeView;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.LandUse.injection.DaggerLandUseComponent;
import sk.pixwell.therun.presentation.LandUse.injection.LandUseComponent;
import sk.pixwell.therun.presentation.LandUse.injection.LandUseModule;
import sk.pixwell.therun.presentation.LandUse.presenter.LandUsePresenter;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class LandUseActivity extends TheRunActivity implements HasComponent<LandUseComponent>, LandUseView {

    private static final String INTENT_EXTRA_PARAM_STAGE = "INTENT_PARAM_STAGE";
    private static final String INSTANCE_STATE_PARAM_STAGE = "STATE_PARAM_STAGE";

    @Inject
    LandUsePresenter mPresenter;

    @BindView(R.id.pieChart1)
    PieChart mChart;

    private Stage stage;
    private LandUseComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_land_use);

        ButterKnife.bind(this);

        initializeActivity(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);

        initializeLayout();
    }

    private void initializeLayout() {

    }

    private PieData generatePieData() {
        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        /*Typeface tf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        d.setValueTypeface(tf);*/

        return d;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    private void initializeInjector() {
        LandUseModule landUseModule = new LandUseModule();
        component = DaggerLandUseComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .landUseModule(landUseModule)
                .build();
        component.inject(this);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.stage = (Stage) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_STAGE);
        } else {
            this.stage = (Stage) savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_STAGE);
        }
    }

    public static Intent getCallingIntent(Context context, Stage stage) {
        Intent callingIntent = new Intent(context, LandUseActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_STAGE, stage);
        return callingIntent;
    }

    @Override
    public LandUseComponent getComponent() {
        return component;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(INSTANCE_STATE_PARAM_STAGE, this.stage);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setChart(List<Landuse> result) {
        mChart.getDescription().setEnabled(false);

        //Typeface tf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Light.ttf");

        //mChart.setCenterTextTypeface(tf);
        mChart.setCenterText(generateCenterText());
        mChart.setCenterTextSize(10f);
        //mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);


        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(Landuse item: result) {
            entries1.add(new PieEntry(item.length, item.landuse));
        }

        PieDataSet ds1 = new PieDataSet(entries1, stage.getDescription());
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        mChart.setData(d);
        mChart.invalidate();
        System.out.print(result.size());
    }
}
