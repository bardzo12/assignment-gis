package sk.pixwell.therun.data.executor;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Token;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;
import sk.pixwell.therun.domain.subscriber.TokenSubscriber;

/**
 * Created by Tomáš Baránek on 31.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class EndRun extends UseCase {

    private final Repository repository;
    private Subscriber<Boolean> useCaseSubscriber;
    private Date time;
    private String type;
    private Token token;

    public EndRun(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void execute(final Subscriber useCaseSubscriber, Date time, String type) {
        this.time = time;
        this.type = type;
        this.useCaseSubscriber = useCaseSubscriber;


        getToken();
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.endRun(token, time, type);

    }

    private void getToken() {
        this.subscription = this.repository.getToken()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(new TokenSubscriber(new TokenSubscriber.SubscriberListener() {
                    @Override
                    public void onCompleted() {
                        subscription.unsubscribe();
                        startRun();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Observable.create(new Observable.OnSubscribe<Boolean>() {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber) {
                                subscriber.onError(e);
                            }
                        }).subscribeOn(Schedulers.from(threadExecutor))
                                .observeOn(postExecutionThread.getScheduler())
                                .subscribe(useCaseSubscriber);
                    }

                    @Override
                    public void onNext(Token token) {
                        EndRun.this.token = token;
                    }
                }));
    }

    private void startRun() {
        super.execute(useCaseSubscriber);
    }
}