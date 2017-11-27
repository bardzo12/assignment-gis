package sk.pixwell.therun.presentation.Administrative.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Administrative.injection.AdministrativeComponent;
import sk.pixwell.therun.presentation.Administrative.injection.AdministrativeModule;
import sk.pixwell.therun.presentation.Administrative.injection.DaggerAdministrativeComponent;
import sk.pixwell.therun.presentation.Administrative.presenter.AdministrativePresenter;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class AdministrativeActivity extends TheRunActivity implements HasComponent<AdministrativeComponent>, AdministrativeView {

    private static final String INTENT_EXTRA_PARAM_STAGE = "INTENT_PARAM_STAGE";
    private static final String INSTANCE_STATE_PARAM_STAGE = "STATE_PARAM_STAGE";

    @Inject
    AdministrativePresenter mPresenter;

    @BindView(R.id.lv_kraj)
    ListView lv_kraje;

    @BindView(R.id.lv_okres)
    ListView lv_okresy;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @OnClick(R.id.back_button)
    void back(){
        super.onBackPressed();
    }

    private Stage stage;
    private AdministrativeComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_administrative);

        ButterKnife.bind(this);

        initializeActivity(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);

        initializeLayout();
    }

    private void initializeLayout() {
        mPresenter.getKraje(stage);
        mPresenter.getOkresy(stage);
        mTitle.setText(stage.getId() + " Etapa");
    }

    private void initializeInjector() {
        AdministrativeModule administrativeModule = new AdministrativeModule();
        component = DaggerAdministrativeComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .administrativeModule(administrativeModule)
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
        Intent callingIntent = new Intent(context, AdministrativeActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_STAGE, stage);
        return callingIntent;
    }

    @Override
    public AdministrativeComponent getComponent() {
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
    public void navigateToAdministrativeDetail() {
        this.navigator.navigateToAdministrativeDetail(this, stage);
    }

    @Override
    public void setKraje(List<String> result) {
        String[] stockArr = new String[result.size()];
        stockArr = result.toArray(stockArr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stockArr);
        lv_kraje.setAdapter(adapter);
    }

    @Override
    public void setOkresy(List<String> result) {
        String[] stockArr = new String[result.size()];
        stockArr = result.toArray(stockArr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stockArr);
        lv_okresy.setAdapter(adapter);
    }
}
