package sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.presenters.LiveMapPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.PointsOfStageSingleton;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.StagesSingleton;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 12.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class LiveMapFragment extends TheRunFragment implements LiveMapView, OnMapReadyCallback {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 589;
    @Inject
    LiveMapPresenter mPresenter;

    @BindView(R.id.map)
    MapView mMapView;

    GoogleMap map;

    PolylineOptions options = new PolylineOptions();

    List<Marker> markers = new ArrayList<>();

    //Bundle saveBundle;

    public static LiveMapFragment newInstance() {
        LiveMapFragment fragment = new LiveMapFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_live_map, container, false);

        ButterKnife.bind(this, layout);

        initializeLayout(savedInstanceState);

        return layout;
    }

    private void initializeLayout(Bundle savedInstanceState) {
        afterDrawer(savedInstanceState);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }



    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(PointsOfStageSingleton.getInstance().getPoints().size() > 0)
            setTrack(PointsOfStageSingleton.getInstance().getPoints());
        else
            mPresenter.getTrack();
        if(StagesSingleton.getInstance().getStages().size() > 0)
            setFlafs(StagesSingleton.getInstance().getStages());
        /*else 
            mPresenter.getStages();*/
        mPresenter.initializeLayout();

        initializeLayout();
    }

    private void setFlafs(List<Stage> stages) {
        for (Stage data : stages){
            BitmapDescriptor iconPin = BitmapDescriptorFactory.fromResource(R.drawable.finish_flag_48);

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(data.getEnd().getLatitude(), data.getEnd().getLongitude()))
                    .title("Koniec " + data.getId() + ". etapy"))
                    .setIcon(iconPin);
        }
    }

    @Override
    public void onResume() {
        //if(saveBundle!= null)
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


    public void setLocationPermission(boolean locationPermission) {
        if (locationPermission) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (map != null) {


                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {

                        mPresenter.getCastlePolygon(location.getLatitude(), location.getLongitude());

                        map.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                            public void onPolygonClick(Polygon polygon) {
                                showToastMessage(String.valueOf(polygon.getTag()));

                            }
                        });

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        if(mCircle == null || mMarker == null){
                            drawMarkerWithCircle(latLng);
                        }else{
                            updateMarkerWithCircle(latLng);
                        }
                    }
                });

            }
        }
    }

    private Circle mCircle;
    private Marker mMarker;

    private void updateMarkerWithCircle(LatLng position) {
        mCircle.setCenter(position);
        mMarker.setPosition(position);
    }

    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 100.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = map.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMarker = map.addMarker(markerOptions);
    }

    @Override
    public void initializeLayout() {
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.getLocationPermission();
    }

    @Override
    public void setTeams(List<Team> result) {
        //map.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(markers.size() > 0){
            for(Team data : result){
                for(Marker marker : markers){
                    if(marker.getTag().equals(data.getId())){
                        marker.setPosition(new LatLng(data.getActualPossition().getLatitude(),data.getActualPossition().getLongitude()));
                        break;
                    }
                }
            }
        }else {
            for (int i = 0; i < result.size(); i++) {
                LatLng position = new LatLng(result.get(i).getActualPossition().getLatitude(),
                        result.get(i).getActualPossition().getLongitude());
                builder.include(position);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(position)
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(result.get(i).getName().substring(0, 2).toUpperCase())))
                        .title(result.get(i).getName())
                        .anchor(0.5f, 0.5f);
                Marker mMarker2 = map
                        .addMarker(markerOptions);
                mMarker2.setTag(result.get(i).getId());
                markers.add(mMarker2);
            }
            //map.addPolyline(options);
        }

        /*final LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 150);
        map.animateCamera(cu);*/
    }

    private Bitmap getMarkerBitmapFromView(String name) {

        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_pin_team, null);
        TextView mName = (TextView) customMarkerView.findViewById(R.id.name_text);
        mName.setText(name);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void showToast() {
        showToastMessage("GET");
    }

    public void setTrack(List<PointOfStage> listLocsToDraw) {
        if ( map == null )
        {
            return;
        }

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        options = new PolylineOptions();

        options.color(UIUtils.getColorWrapper(getContext(), R.color.colorPrimary));
        options.width( 5 );
        options.visible( true );

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for ( PointOfStage locRecorded : listLocsToDraw )
        {
            LatLng point = new LatLng( locRecorded.getLatitude(),
                    locRecorded.getLongitude() );
            options.add(point );
            builder.include(point);
        }

        map.addPolyline( options );

        LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        map.animateCamera(cu);
    }

    @Override
    public void setCastlePolygons(List<CastlePolygon> result) {
        Iterable<LatLng> data;
        for(CastlePolygon caste: result) {
            List<LatLng> body = new ArrayList<>();
            PolygonOptions polygonOptions = new PolygonOptions();
            for(CastlePolygon.Coordinates coordinates: caste.polygon){
                body.add(new LatLng(coordinates.lat, coordinates.lng));
                polygonOptions.add(new LatLng(coordinates.lat, coordinates.lng));
            }
            data = body;
            Polygon polygon = map.addPolygon(new PolygonOptions()
                    .addAll(data)
                    .strokeColor(UIUtils.getColorWrapper(getContext(),R.color.colorPrimaryDark))
                    .fillColor(UIUtils.getColorWrapper(getContext(),R.color.colorPrimary)));
            polygon.setClickable(true);
            polygon.setTag(caste.name);
        }
    }

    public void afterDrawer(Bundle saveBundle) {
        if(checkPlayServices()){
            mMapView.onCreate(saveBundle);
            // Gets to GoogleMap from the MapView and does initialization stuff
            if (mMapView != null) {
                //mMapView.setCameraDistance();
                mMapView.getMapAsync(this);
            }
        } else {
            showToastMessage("Prosím aktualizujte si aplikáciu Google Maps");
        }
    }
}
