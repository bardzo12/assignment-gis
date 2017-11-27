package sk.pixwell.therun.data.executor;

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
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class GetReports extends UseCase {

    private final Repository repository;

    private Token token;

    public GetReports(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getReports(token);

    }

    @Override
    public void execute(final Subscriber useCaseSubscriber){
        this.subscription = this.repository.getToken()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(new TokenSubscriber(new TokenSubscriber.SubscriberListener() {
                    @Override
                    public void onCompleted() {
                        GetReports.super.execute(useCaseSubscriber);
                    }

                    @Override
                    public void onError(Throwable e) {
                        useCaseSubscriber.onError(e);
                    }

                    @Override
                    public void onNext(Token token) {
                        GetReports.this.token = token;
                    }
                }));
    }
}
