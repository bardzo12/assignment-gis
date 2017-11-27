package sk.pixwell.therun.presentation.Dashboard.ui;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.presentation.Dashboard.adapters.DrawerItem;
import sk.pixwell.therun.presentation.Dashboard.adapters.DrawerItemAdapter;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.ui.CheckInFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.MapViewPager.StageListener;
import sk.pixwell.therun.presentation.Dashboard.fragments.Map.ui.MapFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Rank.ui.RankFragment;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.ui.RunFragment;
import sk.pixwell.therun.presentation.Dashboard.injection.DaggerDashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardModule;
import sk.pixwell.therun.presentation.Dashboard.presenter.DashboardPresenter;
import sk.pixwell.therun.presentation.EndInfoSingleton;
import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.StagesInfoSingleton;
import sk.pixwell.therun.presentation.StartInfoSingleton;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivity;
import sk.pixwell.therun.presentation.shared.activity.TheRunActivityModule;

import static sk.pixwell.therun.presentation.shared.navigation.Navigator.FAN_MODE;
import static sk.pixwell.therun.presentation.shared.navigation.Navigator.RUNNER_MODE;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class DashboardActivity extends TheRunActivity implements HasComponent<DashboardComponent>, DashboardView, StageListener, BeaconConsumer {

    @Inject
    DashboardPresenter mPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.left_drawer)
    ListView mDrawerList;

    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.nameTextView)
    TextView mName;

    @BindView(R.id.teamNameTextView)
    TextView mTeamName;

    @BindView(R.id.logoutButton)
    Button mLogoutButton;

    @BindView(R.id.logInButton)
    Button mLogIn;

    @OnClick(R.id.logInButton)
    void logIn(){
        mPresenter.logIn();
    }

    @OnClick(R.id.logoutButton)
    void logout(){
        mPresenter.logout();
    }

    private static final int REQUEST_CAMERA = 150;
    private static final int REQUEST_PHONE = 200;
    private static final int REQUEST_LOCATION = 250;
    private static final int REQUEST_LOCATION_LOCAL = 350;
    private static final int REQUEST_BLUETOOTH = 300;

    private static final String INTENT_EXTRA_PARAM_RUNNER = "INTENT_PARAM_RUNNER";
    private static final String INSTANCE_STATE_PARAM_RUNNER = "STATE_PARAM_RUNNER";

    private static final String INTENT_EXTRA_PARAM_MODE = "INTENT_PARAM_MODE";
    private static final String INSTANCE_STATE_PARAM_MODE = "STATE_PARAM_MODE";


    private CheckInFragment checkInFragment;
    private MapFragment mapFragment;
    private RunFragment runFragment;
    private RankFragment rankFragment;

    DashboardComponent component;

    private BeaconManager beaconManager;
    private Boolean isRegister = false;

    private List<DrawerItem> drawerItems = new ArrayList<>();
    private DrawerItemAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;

    private Runner runner;
    private int mode;
    private Boolean mapFragmentSelected = false;
    private List<Beacon> beaconsList = new ArrayList<>();
    private List<StageInfo> stagesInfoAddToNotifi = new ArrayList<>();
    private boolean endWest = false;
    private boolean endEast  = false;
    private boolean startWest = false;
    private boolean startEast  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeActivity(savedInstanceState);

        ButterKnife.bind(this);

        initializeInjector();

        mPresenter.setView(this);

        initializeLayout(savedInstanceState);
        Boolean hasBLE = false;
        PackageManager pm = getPackageManager();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2){
            hasBLE = false;
        }else {
            hasBLE = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        }
        if(!hasBLE){
            showAllert();
        }

    }

    private void showAllert() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Pozor")
                .setMessage("Vaše zariadenie neobsahuje Bluetooth Low Energy, preto funkcie spojené s technológiou iBeacon nebudú dostupné" +
                        " (overenie etapy pomocou iBeacon, Check-in na štarte, štart pretekov, koniec pretekov). Použite na tieto činnosti" +
                        " iné zariadenie.")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialogInterface){
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(UIUtils.getColorWrapper(getApplicationContext(), R.color.darkButton));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(int i = 0; i < grantResults.length; i++){
            switch (requestCode){
                case (REQUEST_CAMERA):
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        checkInFragment.photoPermission(true);
                    else
                        checkInFragment.photoPermission(false);
                    break;
                case (REQUEST_PHONE):
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        runFragment.callPermission(true);
                    else
                        runFragment.callPermission(false);
                    break;
                case (REQUEST_LOCATION):
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        mapFragment.callPermission(true);
                    else
                        mapFragment.callPermission(false);
                    break;
                case (REQUEST_LOCATION_LOCAL):
                    break;
                case (REQUEST_BLUETOOTH):
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        checkInFragment.bluetoothPermission(true);
                    else
                        checkInFragment.bluetoothPermission(false);
                    break;
            }
        }
    }

    private void initializeLayout(Bundle savedInstanceState) {
        if(this.mode == RUNNER_MODE) {
            initializeLayoutAsRunner(savedInstanceState);
            getLocationPermissionLocal();
        } else
            initializeLayoutAsFans(savedInstanceState);

    }

    private void initializeLayoutAsFans(Bundle savedInstanceState) {
        mLogIn.setVisibility(View.VISIBLE);
        mLogoutButton.setVisibility(View.GONE);

        mName.setVisibility(View.VISIBLE);
        mName.setText("The Run Slovakia 2018");
        mTeamName.setText("Fanúšik");
        drawerItems.add(new DrawerItem("Run 2018"));
        drawerItems.add(new DrawerItem("Mapa"));
        drawerItems.add(new DrawerItem("Poradie 2017"));

        drawer.setDrawerShadow(null,
                GravityCompat.START);

        adapter = new DrawerItemAdapter(this, R.layout.drawer_item,
                drawerItems);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                mToolbar, R.string.openDrawer,
                R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        mDrawerToggle.setHomeAsUpIndicator(drawable);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.setDrawerListener(mDrawerToggle);

        SelectItem(0);

        if (savedInstanceState == null) {
            SelectItem(0);
        }
    }

    private void initializeLayoutAsRunner(Bundle savedInstanceState) {
        mLogIn.setVisibility(View.GONE);
        mLogoutButton.setVisibility(View.VISIBLE);

        mName.setVisibility(View.VISIBLE);
        mTeamName.setVisibility(View.VISIBLE);
        mLogoutButton.setVisibility(View.VISIBLE);
        mName.setText(runner.getFirstName() + " " + runner.getLastName());
        mTeamName.setText(runner.getTeam().getName());
        drawerItems.add(new DrawerItem("Run 2018"));
        drawerItems.add(new DrawerItem("Mapa"));
        drawerItems.add(new DrawerItem("CHECK IN"));
        drawerItems.add(new DrawerItem("Poradie 2017"));

        drawer.setDrawerShadow(null,
                GravityCompat.START);


        beaconManager = BeaconManager.getInstanceForApplication(this);
        String[] beaconParsers = new String[] {
                "m:0-1=8c00,i:2-10,p:11-11",
                "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25",
                "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15",
                "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19",
                "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v",
                "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24",
                "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24",
                "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24",
                "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25",
                "m:2-3=0203,i:14-19l,d:10-13,p:9-9" };

        for(String beacon : beaconParsers){
            Log.i("BeaconService","layout: "+beacon);
            beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(beacon));
        }
        beaconManager.bind(this);


        adapter = new DrawerItemAdapter(this, R.layout.drawer_item,
                drawerItems);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                mToolbar, R.string.openDrawer,
                R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        mDrawerToggle.setHomeAsUpIndicator(drawable);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.setDrawerListener(mDrawerToggle);

        SelectItem(0);

        if (savedInstanceState == null) {
            SelectItem(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(beaconManager != null)
            beaconManager.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            if(drawer.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawer(Gravity.LEFT);
            }else{
                drawer.openDrawer(Gravity.LEFT);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeInjector() {
        component = DaggerDashboardComponent
                .builder()
                .theRunApplicationComponent(getApplicationComponent())
                .theRunActivityModule(new TheRunActivityModule(this))
                .dashboardModule(new DashboardModule())
                .build();
        component.inject(this);
    }


    public void SelectItem(int possition) {

        android.support.v4.app.Fragment fragment = null;
        adapter.updateItems(possition);
        adapter.notifyDataSetChanged();
        fragment =  new MapFragment();
        mapFragment = (MapFragment) fragment;

        mapFragmentSelected = false;
        if(mode == RUNNER_MODE) {
            switch (possition) {

                case 0:
                    fragment = new RunFragment();
                    runFragment = (RunFragment) fragment;
                    runFragment.setMode(RUNNER_MODE);
                    break;
                case 1:
                    mapFragmentSelected = true;
                    fragment = mapFragment;
                /*args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                    break;
                case 2:
                    fragment = new CheckInFragment();
                    checkInFragment = (CheckInFragment) fragment;

                /*args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                    break;
                case 3:
                    fragment = new RankFragment();
                    rankFragment = (RankFragment) fragment;
                /*args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
                        .get(possition).getImgResID());*/
                    break;
                default:
                    break;
            }
        }else {
            switch (possition) {

                case 0:
                    fragment = new RunFragment();
                    runFragment = (RunFragment) fragment;
                    runFragment.setMode(FAN_MODE);
                    break;
                case 1:
                    fragment = mapFragment;
                    break;
                case 2:
                    fragment = new RankFragment();
                    rankFragment = (RankFragment) fragment;
                    break;
                default:
                    break;
            }
        }
        final android.support.v4.app.Fragment finalFragment = fragment;
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {*/
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, finalFragment, "12");
                    fragmentTransaction.commitAllowingStateLoss();
               /* }catch (Exception ignored){

                }
            }
        }).start();**/



        mDrawerList.setItemChecked(possition, true);
        setTitle(drawerItems.get(possition).getItemName());
        drawer.closeDrawer(mRelativeLayout);

    }

    @Override
    public DashboardComponent getComponent() {
        return component;
    }


    public void getPhotoPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            checkInFragment.photoPermission(true);
        }
    }

    public void getCallPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE);
        } else {
            checkInFragment.photoPermission(true);
        }
    }

    @Override
    public void navigateToDetail(Stage stage) {
        navigator.navigateToStageDetail(this,stage, this);
    }

    @Override
    public void setViewBeacon(Boolean result, Beacon beacon) {
        if(checkInFragment != null)
            checkInFragment.setViewBeacon(result, beacon);
    }

    @Override
    public void navigateToStageDetailDialog(Stage stage) {
        navigator.navigateToStageDetailDialog(this, stage);
    }

    @Override
    public void navigateToWalkthrough() {
        finish();
        this.mPresenter.clearToken();
        this.navigator.navigateToWalkthroughAndClearStack(this);
    }

    @Override
    public void navigateToEmailLogin() {
        this.navigator.navigateToLoginAndClearStack(this, this);
    }

    @Override
    public void enterToEnd(int i) {
        if(runFragment != null)
            runFragment.enterToEnd(i);
    }

    @Override
    public void navigateToStageAminity(Stage stage) {
        this.navigator.navigateToStageAminity(this, stage, this);
    }

    @Override
    public void navigateToStage(Stage stage) {
        mPresenter.navigateToStageDetail(stage);
    }

    @Override
    public void navigateToStageDialog(Stage stage) {
        mPresenter.navigateToStageDetailDialog(stage);
    }

    @Override
    public void onClickStageAminity(Stage stage) {
        mPresenter.onClickStageAminity(stage);
    }

    public void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            mapFragment.callPermission(true);
        }
    }

    public void getLocationPermissionLocal() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_LOCAL);
        }
    }

    public void getBeaconPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN},
                    REQUEST_BLUETOOTH);
        } else {
            checkInFragment.bluetoothPermission(true);
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                beaconsList = new ArrayList(beacons);
                controlStage(beaconsList);
                if (beacons.size() > 0) {
                    for(Beacon data : beacons)
                        mPresenter.setBeacon(data);
                } else {
                    mPresenter.noBeacon();
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("TheRunRegion", Identifier.parse("DEADBEEF-CA1F-BABE-FEED-FEEDC0DEFACE"), null, null));
        } catch (RemoteException e) {    }
    }

    private void controlStage(List<Beacon> beaconsList) {
        endEast = false;
        endWest = false;
        startEast = false;
        startWest = false;
        for(int i =0; i < beaconsList.size(); i++){
            for(StageInfo data : StagesInfoSingleton.getInstance().getStages()) {
                if (beaconsList.get(i).getId2().toString().equalsIgnoreCase(data.getMajor()) &&
                        beaconsList.get(i).getId3().toString().equalsIgnoreCase(data.getMinor()))
                    showNotification(data);
                break;
            }
            for(StageInfo data : EndInfoSingleton.getInstance().getStages()) {
                if (beaconsList.get(i).getId2().toString().equalsIgnoreCase(data.getMajor()) &&
                        beaconsList.get(i).getId3().toString().equalsIgnoreCase(data.getMinor())) {
                    //showNotificationEnd(data);
                    if(data.getQrString().equals("end_east"))
                        endEast = true;
                    else if(data.getQrString().equals("end_west"))
                        endWest = true;
                }
                break;
            }
            for(StageInfo data : StartInfoSingleton.getInstance().getStages()) {
                if (beaconsList.get(i).getId2().toString().equalsIgnoreCase(data.getMajor()) &&
                        beaconsList.get(i).getId3().toString().equalsIgnoreCase(data.getMinor())) {
                    //showNotificationEnd(data);
                    if(data.getQrString().equals("start_east"))
                        startEast = true;
                    else if(data.getQrString().equals("start_west"))
                        startWest = true;
                }
                break;
            }
        }
        if(runFragment != null) {
            runFragment.setEndORun(endEast, endWest);
            runFragment.setStartORun(startEast, startWest);
        }
    }

    private void showNotificationEnd(StageInfo data) {
        if(controlIsAdded(data)) {
            Intent intent = new Intent(this, DashboardActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
            Notification noti = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                noti = new Notification.Builder(this)
                        .setContentTitle("The Run 2017")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pIntent)
                        .setStyle(new Notification.BigTextStyle().bigText("Vitajte v cieli"))
                        .build();
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(data.getId(), noti);
        }
    }

    private boolean controlIsAdded(StageInfo data) {
        for(StageInfo stage : stagesInfoAddToNotifi){
            if(stage.getQrString().equals(data.getQrString())){
                return false;
            }
        }
        stagesInfoAddToNotifi.add(data);
        return true;
    }

    private void showNotification(StageInfo data) {
        if(controlIsAdded(data)) {
            if (data.getId() > 1) {
                Intent intent = new Intent(this, DashboardActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
                Notification noti = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    noti = new Notification.Builder(this)
                            .setContentTitle("The Run 2017")
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pIntent)
                            .setStyle(new Notification.BigTextStyle().bigText("Ste na konci etapy " + String.valueOf(data.getId()) + ". Prosím urobte za Váš tím check-in"))
                            .build();
                }
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(data.getId(), noti);
            } else {
                Intent intent = new Intent(this, DashboardActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
                Notification noti = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    noti = new Notification.Builder(this)
                            .setContentTitle("The Run 2017")
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pIntent)
                            .setStyle(new Notification.BigTextStyle().bigText("Vitajte na štarte"))
                            .build();
                }
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(data.getId(), noti);
            }
        }

    }

    private void enterToEndWestEnd() {
        mPresenter.enterToEndWestEnd();
    }

    private void enterToEndEastEnd() {
        mPresenter.enterToEndEastEnd();
        //showNotification();
    }

    private void showNotification() {
        Intent intent = new Intent(this, DashboardActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification noti = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            noti = new Notification.Builder(this)
                    .setContentTitle("The Run 2017")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pIntent)
                    .setContentText("Vitaj v cieli").build();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;


        notificationManager.notify(0, noti);
    }

    public Boolean checkInBeacon() {
        return mPresenter.checkInBeacon();
    }

    private final BroadcastReceiver mbluetoothStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        checkInFragment.bluetoothOf();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        showToastMessage("Bluetooth bol vypnutý. Nie je možné použiť iBeacon technológiu");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        checkInFragment.bluetoothOn();
                        break;
                }
            }
        }
    };

    public void unregisterReceiver() {
        if(isRegister) {
            isRegister = false;
            unregisterReceiver(mbluetoothStateReceiver);
        }
    }

    public void registerReceiver() {
        isRegister = true;
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mbluetoothStateReceiver, filter);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.mode = getIntent().getIntExtra(INTENT_EXTRA_PARAM_MODE, -1);
            if(mode == RUNNER_MODE)
                this.runner = (Runner) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_RUNNER);
        } else {
            this.mode = savedInstanceState.getInt(INSTANCE_STATE_PARAM_MODE);
            if(mode == RUNNER_MODE)
                this.runner = (Runner) savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_RUNNER);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(INSTANCE_STATE_PARAM_RUNNER, this.mode);
            if(this.mode == RUNNER_MODE)
                outState.putSerializable(INSTANCE_STATE_PARAM_RUNNER, this.runner);
        }
        super.onSaveInstanceState(outState);
    }

    public static Intent getCallingIntent(Context context, Runner runner, int mode) {
        Intent callingIntent = new Intent(context, DashboardActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_RUNNER, runner);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MODE, mode);
        return callingIntent;
    }

    public static Intent getCallingIntent(Context context, int mode) {
        Intent callingIntent = new Intent(context, DashboardActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MODE, mode);
        return callingIntent;
    }

    public void changeFavoriteInTeamsFragment() {
        rankFragment.changeFavoriteInTeamsFragment();
    }

    public void changeFavoriteInFavoriteFragment() {
        rankFragment.changeFavoriteInFavoriteFragment();
    }

    public void setErrorOffBluetooth() {
        checkInFragment.bluetoothOf();
    }

    public void navigateToControlFragment() {
        SelectItem(1);
    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
