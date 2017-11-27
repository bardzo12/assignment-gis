package sk.pixwell.therun.presentation.shared.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import sk.pixwell.therun.presentation.HasComponent;
import sk.pixwell.therun.presentation.R;
/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class TheRunSupportFragment extends SupportMapFragment {
    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */

    private final String googlePlay = "https://play.google.com/store/apps/details?id=sk.pixwell.ekobanka";

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }


    public abstract void initializeLayout();

    public void onUpdateNeeded() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.TheRunDialogTheme)
                .setTitle("Dostupná nová verzia aplikácie")
                .setMessage("Prosím aktualizujte aplikáciu pre správne fungovanie.")
                .setPositiveButton("Aktualizuj",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(googlePlay);
                            }
                        }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
