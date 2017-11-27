package sk.pixwell.therun.data.executor;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.Token;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;
import sk.pixwell.therun.domain.subscriber.TokenSubscriber;
import timber.log.Timber;

/**
 * Created by Tomáš Baránek on 16.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CheckInStage extends UseCase {

    private final Repository repository;
    private Subscriber<Boolean> useCaseSubscriber;
    private StageInfo stage;
    private Token token;

    public CheckInStage(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void execute(final Subscriber useCaseSubscriber, StageInfo stage) {
        this.stage = stage;
        this.useCaseSubscriber = useCaseSubscriber;

        getToken();
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.checkInStage(token, stage);
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
                        CheckInStage.this.token = token;
                    }
                }));
    }

    private void checkInStage() {
        super.execute(useCaseSubscriber);
    }
}