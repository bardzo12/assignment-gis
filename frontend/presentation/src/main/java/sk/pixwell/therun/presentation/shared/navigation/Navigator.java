package sk.pixwell.therun.presentation.shared.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.presentation.Administrative.ui.AdministrativeActivity;
import sk.pixwell.therun.presentation.Aminity.ui.StageAminityActivity;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.Detail_dialog.DetailDialogActivity;
import sk.pixwell.therun.presentation.LandUse.ui.LandUseActivity;
import sk.pixwell.therun.presentation.Login_Email.ui.LoginEmailActivity;
import sk.pixwell.therun.presentation.Login_Password.ui.LoginPasswordActivity;
import sk.pixwell.therun.presentation.Stage_detail.ui.StageDetail;
import sk.pixwell.therun.presentation.splash.ui.SplashActivity;
import sk.pixwell.therun.presentation.walkthrough.ui.WalkthroughActivity;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    private static final int STAGE_DETAIL = 150;
    private static final int STAGE_AMINITY = 160;
    private static final int PASSWORD_ACTIVITY = 200;
    public static final int RUNNER_MODE = 1;
    public static final int FAN_MODE = 2;

    @Inject
    public Navigator() {
        //empty
    }


    public void navigateToSplash(Context context) {
        if (context != null) {
            Intent intentToLaunch = SplashActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToStageDetail(Context context, Stage stage, Activity activity) {
        if (activity != null) {
            Intent intentToLaunch = StageDetail.getCallingIntent(context, stage);
            activity.startActivityForResult(intentToLaunch, STAGE_DETAIL);
        }
    }

    public void navigateToWalkthrough(Context context) {
        if (context != null) {
            Intent intentToLaunch = WalkthroughActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
        /*if (context != null) {
            Intent intentToLaunch = DetailDialogActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }*/
    }

    public void navigateToRunnerMode(Context context, Runner result, Activity activity) {
        if (context != null) {
            ActivityCompat.finishAffinity(activity);
            Intent intentToLaunch = DashboardActivity.getCallingIntent(context, result, RUNNER_MODE);
            intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intentToLaunch);
        }
    }

    public void navigateToFansMode(Context context, Activity activity) {
        if (context != null) {
            activity.finish();
            Intent intentToLaunch = DashboardActivity.getCallingIntent(context, FAN_MODE);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToPassword(Context context, Activity activity, String result) {
        if (context != null) {
            Intent intentToLaunch = LoginPasswordActivity.getCallingIntent(context, result);
            activity.startActivityForResult(intentToLaunch, PASSWORD_ACTIVITY);
        }
    }

    public void navigateToLogin(Context context) {
        if (context != null) {
            Intent intentToLaunch = LoginEmailActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToStageDetailDialog(Context context, Stage stage) {
        if (context != null) {
            Intent intentToLaunch = DetailDialogActivity.getCallingIntent(context, stage);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWalkthroughAndClearStack(Context context) {
        if (context != null) {
            Intent intentToLaunch = WalkthroughActivity.getCallingIntent(context);
            intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToLoginAndClearStack(Context context, Activity activity) {
        if (context != null) {
            Intent intentToLaunch = LoginEmailActivity.getCallingIntent(context);
            //intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToStageAminity(Activity activity, Stage stage, Context context) {
        if (activity != null) {
            Intent intentToLaunch = StageAminityActivity.getCallingIntent(context, stage);
            activity.startActivityForResult(intentToLaunch, STAGE_AMINITY);
        }
    }

    public void navigateToAdministrativeDetail(Context context, Stage stage) {
        if (context != null) {
            Intent intentToLaunch = AdministrativeActivity.getCallingIntent(context, stage);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToUseLand(Context context, Stage stage) {
        if (context != null) {
            Intent intentToLaunch = LandUseActivity.getCallingIntent(context, stage);
            context.startActivity(intentToLaunch);
        }
    }
}
