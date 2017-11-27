package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.iBeaconCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.text_watchers.ManulQREnter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.StagesInfoSingleton;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class iBeaconCheckInFragment extends TheRunFragment implements iBeaconCheckInView {

    @Inject
    iBeaconCheckInPresenter mPresenter;

    @BindView(R.id.pulsator)
    PulsatorLayout mPulsator;

    @BindView(R.id.manual_edittext_layout)
    TextInputLayout mManualLayout;

    @BindView(R.id.manual_edittext)
    TextInputEditText mManual;

    @OnClick(R.id.checkInButton)
    void checkIn() {
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        if (dashboardActivity.checkInBeacon() && stageInfo != null) {
            showToastMessage("Etapa: " + String.valueOf(stageInfo.getId()));
            mPresenter.checkInStage(stageInfo);
        }else
            showToastMessage("Nie ste v blizskoti žiadnej etapy. Skúste sa priblížiť bližšie k tabuli etapy.");
    }

    @BindView(R.id.checkInButton)
    Button mCheckIn;

    @BindView(R.id.errorTextView)
    TextView mError;

    @BindView(R.id.bluetoothTextView)
    TextView mErrorBluetooth;

    private Boolean bleAvailable = false;
    private boolean hasBLE = false;
    GoogleApiClient client;

    private Boolean lastState;
    private StageInfo stageInfo;

    public static iBeaconCheckInFragment newInstance() {
        iBeaconCheckInFragment fragment = new iBeaconCheckInFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new GoogleApiClient.Builder(getContext())
                .addApi(Awareness.API)
                .build();
        client.connect();

        initializeInjector();

        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_beacon_check_in, container, false);

        ButterKnife.bind(this, layout);

        return layout;
    }

    public void initializeLayout() {

        PackageManager pm = getContext().getPackageManager();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2){
            hasBLE = false;
        }else {
            hasBLE = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        }
        if (hasBLE) {
            DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
            dashboardActivity.getBeaconPermission();
            bleAvailable = true;
            errorOf();
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                // Device does not support Bluetooth
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    setError();
                }
            }
        } else {
            setError();
            bleAvailable = false;
        }
        mManual.addTextChangedListener(new ManulQREnter(getContext(),mManualLayout, StagesInfoSingleton.getInstance().getStages()));

    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.registerReceiver();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.unregisterReceiver();
    }

    public void setBluetoothPermission(boolean result) {
        if (result) {
            errorOf();
        } else {
            setError();
        }
    }

    public void setError() {
        mCheckIn.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        if(hasBLE){
            mErrorBluetooth.setVisibility(View.VISIBLE);
        } else {
            mErrorBluetooth.setVisibility(View.GONE);
        }
    }

    public void errorOf() {
        mCheckIn.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mErrorBluetooth.setVisibility(View.GONE);
    }

    public void setViewBeacon(Boolean result, StageInfo stageInfo) {
        if(stageInfo != null)
            this.stageInfo = stageInfo;
        if(lastState == null) {
            lastState = result;
            setButton(result);
        }else if(lastState != result) {
            setButton(result);
            lastState = result;
        }

    }

    private void setButton(boolean result) {
        if (result) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCheckIn.setBackgroundResource(R.drawable.oval_button_active);
                    mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.backgroundColor1));
                    mPulsator.start();
                    mPulsator.setVisibility(View.VISIBLE);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCheckIn.setBackgroundResource(R.drawable.oval_button_no_active);
                    mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
                    mPulsator.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    @Override
    public void showToast(String s) {
        showToastMessage(s);
    }
}
