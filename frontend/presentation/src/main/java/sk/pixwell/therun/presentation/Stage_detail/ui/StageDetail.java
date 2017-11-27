package sk.pixwell.therun.presentation.Stage_detail.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.Stage_detail.injection.DaggerStageDetailComponent;
import sk.pixwell.therun.presentation.Stage_detail.injection.StageDetailComponent;
import sk.pixwell.therun.presentation.Stage_detail.injection.StageDetailModule;
import sk.pixwell.therun.presentation.Stage_detail.presenter.StageDetailPresenter;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;

public class StageDetail extends TheRunActivity implements HasComponent<StageDetailComponent>, StageDetailView,OnMapReadyCallback {

    @Inject
    StageDetailPresenter mPresenter;
    
    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.distanceTextView)
    TextView mDistatnce;

    @BindView(R.id.bestTimeTextView)
    TextView mBestTime;

    @BindView(R.id.inclineTextView)
    TextView mIncline;

    @BindView(R.id.declineTextView)
    TextView mDecline;

    @BindView(R.id.startLatitudeTextView)
    TextView mStartLat;

    @BindView(R.id.startLongitudeTextView)
    TextView mStartLng;

    @BindView(R.id.endLatitudeTextView)
    TextView mEndLat;

    @BindView(R.id.endLongitudeTextView)
    TextView mEndLng;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @OnClick(R.id.back_button)
    void back(){
        super.onBackPressed();
    }

    @OnClick(R.id.administrativeButton)
    void administrative(){
        mPresenter.onAdministrativeClick();
    }

    @OnClick(R.id.distance_layout)
    void useLand(){
        mPresenter.onUseLandClick();
    }

    GoogleMap map;

    StageDetailComponent component;

    private int id;
    private Stage stage;

    private static final String INTENT_EXTRA_PARAM_STAGE = "INTENT_PARAM_STAGE";
    private static final String INSTANCE_STATE_PARAM_STAGE = "STATE_PARAM_STAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);

        initializeActivity(savedInstanceState);
        ButterKnife.bind(this);

        initializeInjector();

        mPresenter.setView(this);

        initializeLayout(savedInstanceState);
    }

    private void initializeLayout(Bundle savedInstanceState) {
        MapsInitializer.initialize(StageDetail.this);

        mTitle.setText(stage.getName());
        mDecline.setText(String.valueOf(stage.getDecline()) + " M");
        mIncline.setText(String.valueOf(stage.getIncline()) + " M");
        mDistatnce.setText(String.valueOf(stage.getDistance()) + " M");
        mStartLat.setText(String.valueOf(stage.getStart().getLatitude()));
        mStartLng.setText(String.valueOf(stage.getStart().getLongitude()));
        mEndLat.setText(String.valueOf(stage.getEnd().getLatitude()));
        mEndLng.setText(String.valueOf(stage.getEnd().getLongitude()));
        int distance = stage.getDistanceFromStart().intValue();
        mBestTime.setText(String.valueOf(distance) + " M");

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(StageDetail.this) ) {
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
    }

    private void initializeInjector() {
        StageDetailModule stageDetailModule = new StageDetailModule();
        component = DaggerStageDetailComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .stageDetailModule(stageDetailModule)
                .build();
        component.inject(this);
    }

    @Override
    public StageDetailComponent getComponent() {
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
        Intent callingIntent = new Intent(context, StageDetail.class);
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
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        map.animateCamera(cu);

    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void setTrack(List<PointOfStage> list) {
        drawPrimaryLinePath(list);
    }

    @Override
    public void navigateToAdministrative() {
        this.navigator.navigateToAdministrativeDetail(this, stage);
    }

    @Override
    public void navigateToUseLand() {
        this.navigator.navigateToUseLand(this, stage);
    }
}
