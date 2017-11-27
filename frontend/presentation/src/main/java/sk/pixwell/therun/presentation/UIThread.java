package sk.pixwell.therun.presentation;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
@Singleton
public class UIThread implements PostExecutionThread {

  @Inject
  UIThread() {}

  @Override public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
