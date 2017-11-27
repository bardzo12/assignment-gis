package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public interface QRCheckInView {

    void photoPermissionGranted();

    void photoPermissionDenied();

    void onUpdateNeeded();

    void showSnack(String message);
}
