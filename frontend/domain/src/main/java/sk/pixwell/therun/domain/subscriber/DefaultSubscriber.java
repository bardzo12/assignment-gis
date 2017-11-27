package sk.pixwell.therun.domain.subscriber;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {



    @Override public void onCompleted() {
        // no-op by default.
    }

    @Override public void onError(Throwable e) {
        System.out.println("Default error");
        // no-op by default.
    }

    @Override public void onNext(T t) {
        // no-op by default.
    }

}
