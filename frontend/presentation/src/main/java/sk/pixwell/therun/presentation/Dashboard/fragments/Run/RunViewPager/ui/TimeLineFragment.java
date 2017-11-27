package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.CurrentCountdownTimer;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.TimeLinePresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TimeLineFragment extends TheRunFragment implements TimeLineView {

    @Inject
    TimeLinePresenter mPresenter;

    @BindView(R.id.textView7)
    TextView mTime;

    public static TimeLineFragment newInstance() {
        TimeLineFragment fragment = new TimeLineFragment();
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
        View layout = inflater.inflate(R.layout.fragment_time_line_before_run, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {

        /*/Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();

        CurrentCountdownTimer timer = new CurrentCountdownTimer(date.getTime());
        new CountDownTimer(timer.getIntervalMillis(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int days = (int) ((millisUntilFinished / 1000) / 86400);
                int hours = (int) (((millisUntilFinished / 1000)
                        - (days * 86400)) / 3600);
                int minutes = (int) (((millisUntilFinished / 1000)
                        - (days * 86400) - (hours * 3600)) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String countdown;
                if (days > 0)
                    switch (days) {
                        case 1:
                            countdown = String.valueOf(days) + " Deň";
                            break;
                        case 2: case 3:case 4:
                            countdown = String.valueOf(days) + " Dni";
                            break;
                        default:
                            countdown = String.valueOf(days) + " Dní";
                    }
                else if (days < 0 && minutes < 10)
                    countdown = String.format("%02dh:%02dm:%02ds",
                            hours, minutes, seconds);
                else
                    countdown = String.valueOf(hours) + "h:" + String.valueOf(minutes) + "m:" + String.valueOf(seconds) + "s";
                mTime.setText(countdown);
            }

            @Override
            public void onFinish() {
                mTime.setText("Beh je odštartovaný");
            }
        }.start();*/

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
}
