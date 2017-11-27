package sk.pixwell.therun.data.executor;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.data.entity.StageListEntitity;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Token;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;
import sk.pixwell.therun.domain.subscriber.TokenSubscriber;

/**
 * Created by Tomáš Baránek on 20.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class UploadSegmentTime extends UseCase {

    Repository repository;
    Token token;

    public UploadSegmentTime(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.uploadSegmentTime(token);

    }

    @Override
    public void execute(final Subscriber useCaseSubscriber){
        this.subscription = this.repository.getToken()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(new TokenSubscriber(new TokenSubscriber.SubscriberListener() {
                    @Override
                    public void onCompleted() {
                        UploadSegmentTime.super.execute(useCaseSubscriber);
                    }

                    @Override
                    public void onError(Throwable e) {
                        useCaseSubscriber.onError(e);
                    }

                    @Override
                    public void onNext(Token token) {
                        UploadSegmentTime.this.token = token;
                    }
                }));
    }
}
