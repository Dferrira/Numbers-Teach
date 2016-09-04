package helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.dferreira.numbers_teach.languages_dashboard.R;


/**
 * Set of methods to help to handle the view
 */
public class UIHelper {


    /**
     * Toggle the visibility of a view
     *
     * @param view view that is going to get the visibility toggle
     */
    public static void toggleVisibilityView(View view) {

        switch (view.getVisibility()) {
            case View.VISIBLE:
                view.setVisibility(View.INVISIBLE);
                break;
            case View.GONE:
            case View.INVISIBLE:
                view.setVisibility(View.VISIBLE);
                break;
            default:
                view.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Start a new activity if possible with material design transition
     *
     * @param activityOrigin The Activity whose window contains the shared elements.
     * @param sharedView     the view that is going to be shared between activities
     * @param intent         intent that is going to be used to launch the new activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startNewActivity(Activity activityOrigin, View sharedView, Intent intent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activityOrigin.startActivity(intent);
        } else {
            String transitionName = activityOrigin.getResources().getString(R.string.toolbar_transition);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activityOrigin, sharedView, transitionName);
            activityOrigin.startActivity(intent, transitionActivityOptions.toBundle());
        }
    }
}

