package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shamanland.fonticon.FontIconDrawable;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.CurrentCountdownTimer;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.TimeLineCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 14.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TimeLineCheckInFragment extends TheRunFragment implements TimeLineCheckInView {

    @Inject
    TimeLineCheckInPresenter mPresenter;
    private String phoneNumber;

    @BindView(R.id.timeTextView)
    TextView mTime;

    @BindView(R.id.startButton)
    TextView mStart;

    @OnClick(R.id.startButton)
    void start(){
        if((startEast || startWest) && start)
            mPresenter.start(true,getTypeCheckIn());
        else
            showToastMessage("Nie ste v priestore štartu, alebo máte vypnutý bluetooth");
    }

    @BindView(R.id.checkInLayout)
    RelativeLayout mCheckInLayout;

    @BindView(R.id.infoLayout)
    ScrollView mInfoLayout;

    @BindView(R.id.checkInButton)
    TextView mCheckIn;

    @OnClick(R.id.checkInButton)
    void checkIn(){
        if(startEast || startWest)
            mPresenter.checkIn(getTypeCheckIn());
        else
            showToastMessage("Nie ste v priestore štartu, alebo máte vypnutý bluetooth");
    }

    @BindView(R.id.textView19)
    TextView mFirstLine;

    @BindView(R.id.textView22)
    TextView mSecondLine;

    @BindView(R.id.textView7)
    TextView mTimeBefore;

    @BindView(R.id.endRun)
    RelativeLayout mEndLayout;

    @OnClick(R.id.endButton)
    void finish(){
        if(endWest || endEast) {
            mTimeBefore.setText("");
            mTime.setText("");
            mPresenter.end(true, getTypeCheckIn());
        }else
            showToastMessage("Nie ste v cieli, alebo máte vypnutý bluetooth");
    }

    @BindView(R.id.endButton)
    Button mEnd;

    CurrentCountdownTimer currentCountdownTimer;
    int colorWhite;
    int colorDark;
    Boolean start = false;
    String type;
    private CountDownTimer startTimer;
    private ProgressDialog progressDialog;
    private CurrentCountdownTimer startTime =  new CurrentCountdownTimer(0);
    private boolean endWest = false;
    private boolean endEast = false;
    private boolean startEast = false;
    private boolean startWest = false;

    public static TimeLineCheckInFragment newInstance() {
        TimeLineCheckInFragment fragment = new TimeLineCheckInFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_time_line_check_in, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        colorWhite = UIUtils.getColorWrapper(getContext(), R.color.backgroundColor1);
        colorDark = UIUtils.getColorWrapper(getContext(), R.color.textColorButton2);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();

        CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
        setTimer(timer, true);
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
    public void callDialog(final String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.LightDialogTheme);
        alertDialogBuilder.setMessage("Zdravotníci: " + s);
                alertDialogBuilder.setPositiveButton("Zavolať",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                phoneNumber = s;
                                controlCallPermission();
                            }
                        });

        alertDialogBuilder.setNegativeButton("Zrušiť",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToastMessage("Nevolám");
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void showSnack(String s) {
        showToastMessage(s);
    }

    @Override
    public void showProgress(String text) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(text);
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void checkInOkey() {
        setNoActiviteStartButton(startTime);
    }

    @Override
    public void finishOkey() {
        showInfo(startTime);
    }

    @Override
    public void startOkey(String type) {
        showRunnerRun(startTime, type);
    }

    private void controlCallPermission() {
        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.getCallPermission();
    }

    public void callPermission(Boolean result) {
        if(result) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }else {
            showToastMessage("Nepovolili ste volanie.");
        }
    }

    public void setNoActiviteStartButton(CurrentCountdownTimer time) {
        setTimer(time, false);
        mEndLayout.setVisibility(View.GONE);
        mStart.setVisibility(View.VISIBLE);
        mCheckIn.setVisibility(View.INVISIBLE);
        mStart.setBackgroundResource(R.drawable.oval_button_no_active);
        mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
        mInfoLayout.setVisibility(View.INVISIBLE);
        mCheckInLayout.setVisibility(View.VISIBLE);
        mFirstLine.setVisibility(View.VISIBLE);
        mSecondLine.setVisibility(View.VISIBLE);
        mFirstLine.setText("POČKAJTE POKIAĽ UPLYNIE");
        mSecondLine.setText("ČAS DO ZAČIATKU PRETEKU");
    }

    private void setTimer(CurrentCountdownTimer time, final Boolean firstFragment) {
        startTime = time;
        if(currentCountdownTimer == null) {
            currentCountdownTimer = time;
            startTimer = new CountDownTimer(currentCountdownTimer.getIntervalMillis(), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    start = false;
                    int days = (int) ((millisUntilFinished / 1000) / 86400);
                    int hours = (int) (((millisUntilFinished / 1000)
                            - (days * 86400)) / 3600);
                    int minutes = (int) (((millisUntilFinished / 1000)
                            - (days * 86400) - (hours * 3600)) / 60);
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);

                    String countdown;
                    if (days > 0)
                        countdown = String.valueOf(days) + " Dní";
                    else if (days < 0 && minutes < 10)
                        countdown = String.format("%02dh:%02dm:%02ds",
                                hours, minutes, seconds);
                    else
                        countdown = String.valueOf(hours) + "h:" + String.valueOf(minutes) + "m:" + String.valueOf(seconds) + "s";
                    if(firstFragment) {
                        mTimeBefore.setText(countdown);
                    }else {
                        mTime.setText("0h:0m:0s");
                    }
                }

                @Override
                public void onFinish() {
                    start = true;
                    if(firstFragment) {
                        mTimeBefore.setText("Beh bol odštartovaný");
                    }else
                        mTime.setText("Beh je odštartovaný");
                }
            }.start();
        } else if(currentCountdownTimer.getIntervalMillis() != time.getIntervalMillis()){
            startTimer.cancel();
            currentCountdownTimer = time;
            startTimer = new CountDownTimer(currentCountdownTimer.getIntervalMillis(), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    start = false;
                    int days = (int) ((millisUntilFinished / 1000) / 86400);
                    int hours = (int) (((millisUntilFinished / 1000)
                            - (days * 86400)) / 3600);
                    int minutes = (int) (((millisUntilFinished / 1000)
                            - (days * 86400) - (hours * 3600)) / 60);
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);

                    String countdown;
                    if (days > 0)
                        countdown = String.valueOf(days) + " Dní";
                    else if (days < 0 && minutes < 10)
                        countdown = String.format("%02dh:%02dm:%02ds",
                                hours, minutes, seconds);
                    else
                        countdown = String.valueOf(hours) + "h:" + String.valueOf(minutes) + "m:" + String.valueOf(seconds) + "s";
                    if(firstFragment) {
                        mTimeBefore.setText(countdown);
                    }else {
                        mTime.setText(countdown);
                    }
                }

                @Override
                public void onFinish() {
                    start = true;
                    if(firstFragment) {
                        mTimeBefore.setText("Beh bol odštartovaný");
                    }else
                        mTime.setText("0h:0m:0s");
                }
            }.start();
        }


    }

    public void showRunnerRun(CurrentCountdownTimer time, String type) {
        this.type = type;
        setTimer(time, false);
        mStart.setVisibility(View.INVISIBLE);
        mCheckIn.setVisibility(View.INVISIBLE);
        mStart.setBackgroundResource(R.drawable.oval_button_no_active);
        mStart.setTextColor(colorDark);
        mInfoLayout.setVisibility(View.INVISIBLE);
        mCheckInLayout.setVisibility(View.VISIBLE);
        mFirstLine.setVisibility(View.GONE);
        mSecondLine.setVisibility(View.GONE);
        mEndLayout.setVisibility(View.VISIBLE);
        mInfoLayout.setVisibility(View.GONE);
        mCheckInLayout.setVisibility(View.GONE);
        if(endEast || endWest){
            mEnd.setBackgroundResource(R.drawable.oval_button_active);
            mEnd.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
        }else {
            mEnd.setBackgroundResource(R.drawable.oval_button_no_active);
            mEnd.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
        }

    }

    public void showActiviteStartButton(CurrentCountdownTimer time, String type) {
        this.type = type;
        setTimer(time, false);
        mEndLayout.setVisibility(View.GONE);
        mStart.setVisibility(View.VISIBLE);
        mCheckIn.setVisibility(View.INVISIBLE);
        mStart.setBackgroundResource(R.drawable.oval_button_active);
        mStart.setTextColor(colorWhite);
        mInfoLayout.setVisibility(View.INVISIBLE);
        mCheckInLayout.setVisibility(View.VISIBLE);
        mFirstLine.setVisibility(View.VISIBLE);
        mSecondLine.setVisibility(View.VISIBLE);
        mFirstLine.setText("KLIKNUTÍM NA TLAČIDLO");
        mSecondLine.setText("ZAČNITE VÁŠ PRETEK");
        if(startWest || startEast){
            mStart.setBackgroundResource(R.drawable.oval_button_active);
            mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
            mCheckIn.setBackgroundResource(R.drawable.capsule_button);
            mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
        }else {
            mStart.setBackgroundResource(R.drawable.oval_button_no_active);
            mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
            mCheckIn.setBackgroundResource(R.drawable.capsule_button_no_active);
            mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
        }
    }

    public void showActivateCheckIn(CurrentCountdownTimer time, String type) {
        this.type = type;
        setTimer(time, false);
        mEndLayout.setVisibility(View.GONE);
        mStart.setVisibility(View.INVISIBLE);
        mCheckIn.setVisibility(View.VISIBLE);
        mInfoLayout.setVisibility(View.INVISIBLE);
        mCheckInLayout.setVisibility(View.VISIBLE);
        mFirstLine.setVisibility(View.VISIBLE);
        mSecondLine.setVisibility(View.VISIBLE);
        mFirstLine.setText("KLIKNUTÍM NA TLAČIDLO POTVRĎTE");
        mSecondLine.setText("SVOJU ÚČASŤ NA PRETEKU");
        if(startWest || startEast){
            mStart.setBackgroundResource(R.drawable.oval_button_active);
            mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
            mCheckIn.setBackgroundResource(R.drawable.capsule_button);
            mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
        }else {
            mStart.setBackgroundResource(R.drawable.oval_button_no_active);
            mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
            mCheckIn.setBackgroundResource(R.drawable.capsule_button_no_active);
            mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
        }

    }

    public void showInfo(CurrentCountdownTimer time) {
        mEndLayout.setVisibility(View.GONE);
        mInfoLayout.setVisibility(View.VISIBLE);
        mCheckInLayout.setVisibility(View.INVISIBLE);
        setTimer(time, true);
    }

    public String getTypeCheckIn() {
        return type;
    }

    public void setEndRun(final int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*if(i == 1){
                    mControlStage.setVisibility(View.VISIBLE);
                    mControlStage.setBackgroundColor(Color.BLACK);
                }else if(i == 2){
                    mControlStage.setVisibility(View.VISIBLE);
                    mControlStage.setBackgroundColor(Color.RED);
                }*/
            }
        });
    }

    public void setEndRun(final boolean endEastResult, final boolean endWestResult) {
        if(getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    endEast = endEastResult;
                    endWest = endWestResult;
                    if(endEast || endWest){
                        mEnd.setBackgroundResource(R.drawable.oval_button_active);
                        mEnd.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
                    }else {
                        mEnd.setBackgroundResource(R.drawable.oval_button_no_active);
                        mEnd.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
                    }
                }
            });
    }

    public void setStartORun(final boolean startEastResult, final boolean startWestResult) {
        if(getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                startEast = startEastResult;
                startWest = startWestResult;
                if(startEast || startWest){
                    /*mStart.setBackgroundResource(R.drawable.oval_button_active);
                    mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));*/
                    mCheckIn.setBackgroundResource(R.drawable.capsule_button);
                    mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColor));
                }else {
                    /*mStart.setBackgroundResource(R.drawable.oval_button_no_active);
                    mStart.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));*/
                    mCheckIn.setBackgroundResource(R.drawable.capsule_button_no_active);
                    mCheckIn.setTextColor(UIUtils.getColorWrapper(getContext(), R.color.textColorButton2));
                }
            }
        });
    }
}
