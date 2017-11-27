package sk.pixwell.therun.data.executor;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.Token;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;
import sk.pixwell.therun.domain.subscriber.TokenSubscriber;

/**
 * Created by Tomáš Baránek on 25.11.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class GetOkresy extends UseCase {

    private final Repository repository;
    private Subscriber<List<String>> useCaseSubscriber;

    private Stage points;

    public GetOkresy(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getOkresy(points);

    }

    public void execute(final Subscriber<List<String>> useCaseSubscriber, Stage points) {
        this.points = points;
        this.useCaseSubscriber = useCaseSubscriber;
        getToken();
    }

    private void getToken() {
        this.subscription = this.repository.getToken()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(new TokenSubscriber(new TokenSubscriber.SubscriberListener() {
                    @Override
                    public void onCompleted() {
                        subscription.unsubscribe();
                        checkInStage();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Observable.create((Observable.OnSubscribe<List<String>>) subscriber ->
                                subscriber.onError(e)).subscribeOn(Schedulers.from(threadExecutor))
                                .observeOn(postExecutionThread.getScheduler())
                                .subscribe(useCaseSubscriber);
                    }

                    @Override
                    public void onNext(Token token) {
                    }
                }));
    }

    private void checkInStage() {
        super.execute(useCaseSubscriber);
    }
}
