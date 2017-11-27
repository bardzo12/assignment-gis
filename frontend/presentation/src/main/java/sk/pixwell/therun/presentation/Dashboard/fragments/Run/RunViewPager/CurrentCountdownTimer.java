package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager;

import android.text.format.Time;

/**
 * Created by Tomáš Baránek on 20.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CurrentCountdownTimer {
    private long intervalMillis;

    public CurrentCountdownTimer(long time) {

        Time futureTime = new Time();

        // Set date to future time
        futureTime.set(time);
        futureTime.normalize(true);
        long futureMillis = futureTime.toMillis(true);

        Time timeNow = new Time();

        // Set date to current time
        timeNow.setToNow();
        timeNow.normalize(true);
        long nowMillis = timeNow.toMillis(true);

        // Subtract current milliseconds time from future milliseconds time to retrieve interval
        intervalMillis = futureMillis - nowMillis;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }
}
