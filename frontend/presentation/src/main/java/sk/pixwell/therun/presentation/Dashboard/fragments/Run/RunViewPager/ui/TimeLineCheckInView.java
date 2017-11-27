package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui;

/**
 * Created by Tomáš Baránek on 14.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface TimeLineCheckInView {

    void callDialog(String s);

    void showSnack(String s);

    void onUpdateNeeded();

    void showProgress(String text);

    void dismissDialog();

    void checkInOkey();

    void finishOkey();

    void startOkey(String type);
}
