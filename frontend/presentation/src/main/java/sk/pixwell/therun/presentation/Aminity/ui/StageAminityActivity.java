package sk.pixwell.therun.presentation.Aminity.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.AmenityPoint;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Aminity.injection.DaggerStageAminityComponent;
import sk.pixwell.therun.presentation.Aminity.injection.StageAminityComponent;
import sk.pixwell.therun.presentation.Aminity.injection.StageAminityModule;
import sk.pixwell.therun.presentation.Aminity.presenter.StageAminityPresenter;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.Stage_detail.injection.DaggerStageDetailComponent;
import sk.pixwell.therun.presentation.Stage_detail.injection.StageDetailComponent;
import sk.pixwell.therun.presentation.Stage_detail.injection.StageDetailModule;
import sk.pixwell.therun.presentation.Stage_detail.presenter.StageDetailPresenter;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetailView;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;

public class StageAminityActivity extends TheRunActivity implements HasComponent<StageAminityComponent>, StageAminityView,OnMapReadyCallback {

    @Inject
    StageAminityPresenter mPresenter;
    
    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.dentistCheckBox)
    CheckBox mDentist;

    @BindView(R.id.clinicCheckBox)
    CheckBox mClinic;

    @BindView(R.id.doctorCheckBox)
    CheckBox mDoctor;

    @BindView(R.id.hospitalCheckBox)
    CheckBox mHospital;

    @BindView(R.id.pharmacyCheckBox)
    CheckBox mPharmacy;

    @BindView(R.id.findBbutton)
    Button mFind;

    @BindView(R.id.distanceSeekBar)
    SeekBar mDistance;

    @BindView(R.id.distanceTextView)
    TextView mDistanceTV;

    @OnClick(R.id.findBbutton)
    void find(){
        mPresenter.find(mClinic.isChecked(), mDentist.isChecked(), mDoctor.isChecked(), mHospital.isChecked(), mPharmacy.isChecked(), mDistance.getProgress() * 1000);
    }

    @OnClick(R.id.back_button)
    void back(){
        super.onBackPressed();
    }

    GoogleMap map;

    StageAminityComponent component;

    private int id;
    private Stage stage;

    private static final String INTENT_EXTRA_PARAM_STAGE = "INTENT_PARAM_STAGE";
    private static final String INSTANCE_STATE_PARAM_STAGE = "STATE_PARAM_STAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_aminity);

        ButterKnife.bind(this);

        initializeActivity(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);

        initializeLayout(savedInstanceState);
    }

    private void initializeLayout(Bundle savedInstanceState) {
        mDistance.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(UIUtils.getColorWrapper(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY));

        MapsInitializer.initialize(StageAminityActivity.this);

        mTitle.setText(stage.getName());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(StageAminityActivity.this) ) {
            case ConnectionResult.SUCCESS:
                mMapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mMapView != null) {
                    mMapView.getMapAsync(this);
                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(this, "Google Play maps nie je dostupné", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(this, "Aktualizujte si Google Maps", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, GooglePlayServicesUtil.isGooglePlayServicesAvailable(this), Toast.LENGTH_SHORT).show();

        }

        mDistanceTV.setText(String.valueOf(mDistance.getProgress()));
        mDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                mDistanceTV.setText(String.valueOf(progress));
            }
        });
    }

    private void initializeInjector() {
        StageAminityModule stageDetailModule = new StageAminityModule();
        component = DaggerStageAminityComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .stageAminityModule(stageDetailModule)
                .build();
        component.inject(this);
    }

    @Override
    public StageAminityComponent getComponent() {
        return component;
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
        Intent callingIntent = new Intent(context, StageAminityActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_STAGE, stage);
        return callingIntent;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(PointsOfStageSingleton.getInstance().getPoints().size() > 0)
            setTrack(PointsOfStageSingleton.getInstance().getPoints());
        else
            mPresenter.getPoints();
    }

    private void drawPrimaryLinePath( List<PointOfStage> listLocsToDraw ) {
        if ( map == null )
        {
            return;
        }

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color(UIUtils.getColorWrapper(this, R.color.colorPrimary));
        options.width( 5 );
        options.visible( true );

        for ( PointOfStage locRecorded : listLocsToDraw )
        {
            options.add( new LatLng( locRecorded.getLatitude(),
                    locRecorded.getLongitude() ) );
        }

        map.addPolyline( options );


        /*LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(stage.getStart().getLatitude(), stage.getStart().getLongitude()));
        builder.include(new LatLng(stage.getEnd().getLatitude(), stage.getEnd().getLongitude()));
        builder.include(new LatLng(stage.getEnd().getLatitude() - 0.0001, stage.getEnd().getLongitude() - 0.0001));

        final LatLngBounds bounds = builder.build();
        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        map.animateCamera(cu);*/

        List<LatLng> list = new ArrayList<>();
        for(PointOfStage point : stage.getPoints())
            list.add( new LatLng( point.getLatitude(),
                    point.getLongitude() ) );

        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(0);
        polyOptions.addAll(list);
        //googleMap.clear();
        map.addPolyline(polyOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }
        final LatLngBounds bounds = builder.build();

        BitmapDescriptor iconStartPin = BitmapDescriptorFactory.fromResource(R.drawable.pin_start);

        BitmapDescriptor iconEndPin = BitmapDescriptorFactory.fromResource(R.drawable.pin_finish);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(stage.getStart().getLatitude(), stage.getStart().getLongitude()))
                .title("Štart"))
                .setIcon(iconStartPin);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(stage.getEnd().getLatitude(), stage.getEnd().getLongitude()))
                .title("Koniec"))
                .setIcon(iconEndPin);
        //BOUND_PADDING is an int to specify padding of bound.. try 100.

        int width = 1080;
        int height = 1920 / 3;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

    }

    @Override
    public void onResume() {
        if(mMapView != null)
            mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mMapView != null)
            mMapView.onLowMemory();
    }

    @Override
    public void setTrack(List<PointOfStage> list) {
        drawPrimaryLinePath(list);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setResult(List<AmenityPoint> result) {
        map.clear();
        if(PointsOfStageSingleton.getInstance().getPoints().size() > 0)
            setTrack(PointsOfStageSingleton.getInstance().getPoints());
        else
            mPresenter.getPoints();
        for (AmenityPoint point:result) {
            switch (point.amenityName){
                case "doctors":
                    BitmapDescriptor iconStartPin = BitmapDescriptorFactory.fromResource(R.drawable.ic_doctor);
                    if(point.name == null)
                        point.name = "";
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(point.lat, point.lng))
                            .title(point.name))
                            .setIcon(iconStartPin);
                    break;
                case "dentist":
                    BitmapDescriptor iconStartPin2 = BitmapDescriptorFactory.fromResource(R.drawable.ic_dentist);
                    if(point.name == null)
                        point.name = "";
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(point.lat, point.lng))
                            .title(point.name))
                            .setIcon(iconStartPin2);
                    break;
                case "clinic":
                    BitmapDescriptor iconStartPin3 = BitmapDescriptorFactory.fromResource(R.drawable.ic_clinic);
                    if(point.name == null)
                        point.name = "";
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(point.lat, point.lng))
                            .title(point.name))
                            .setIcon(iconStartPin3);
                    break;
                case "hospital":
                    BitmapDescriptor iconStartPin4 = BitmapDescriptorFactory.fromResource(R.drawable.ic_local_hospital);
                    if(point.name == null)
                        point.name = "";
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(point.lat, point.lng))
                            .title(point.name))
                            .setIcon(iconStartPin4);
                    break;
                default:
                    BitmapDescriptor iconStartPin5 = BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy);
                    if(point.name == null)
                        point.name = "";
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(point.lat, point.lng))
                            .title(point.name))
                            .setIcon(iconStartPin5);
                    break;
            }
        }
    }
}
