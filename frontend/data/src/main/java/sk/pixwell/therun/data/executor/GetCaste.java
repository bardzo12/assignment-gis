package sk.pixwell.therun.data.executor;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Token;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;
import sk.pixwell.therun.domain.subscriber.TokenSubscriber;

/**
 * Created by Tomáš Baránek on 23.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class GetCaste extends UseCase {

    private final Repository repository;
    private Subscriber<List<CastlePolygon>> useCaseSubscriber;

    private Token token;
    private int distance;
    private Double lat;
    private Double lng;

    public GetCaste(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getCastlePolygon(token, distance, lat, lng);

    }

    public void execute(final Subscriber<List<CastlePolygon>> useCaseSubscriber, int distance, Double lat, Double lng) {
        this.distance = distance;
        this.lat = lat;
        this.lng = lng;
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
                                Observable.create((Observable.OnSubscribe<List<CastlePolygon>>) subscriber ->
                                        subscriber.onError(e)).subscribeOn(Schedulers.from(threadExecutor))
                                        .observeOn(postExecutionThread.getScheduler())
                                        .subscribe(useCaseSubscriber);
                            }

                            @Override
                            public void onNext(Token token) {
                                GetCaste.this.token = token;
                            }
                        }));
    }

    private void checkInStage() {
        super.execute(useCaseSubscriber);
    }
}
