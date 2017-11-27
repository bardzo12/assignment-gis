package sk.pixwell.therun.presentation;

import android.support.annotation.NonNull;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 * {@link C} is representing interface through which presenter communicates to activity
 */
public interface Presenter<C> {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();


    /**
     *
     */
    void setView(@NonNull C view);
}