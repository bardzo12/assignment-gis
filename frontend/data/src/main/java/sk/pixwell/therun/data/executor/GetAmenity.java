package sk.pixwell.therun.data.executor;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;
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
public class GetAmenity extends UseCase {

    private final Repository repository;
    private Subscriber<AmenityPoints> useCaseSubscriber;

    private Token token;
    private Boolean clinic;
    private Boolean dentist;
    private Boolean doctors;
    private Boolean hospital;
    private Boolean pharmacy;
    private int distance;
    private Stage points;

    public GetAmenity(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getAmenity(token, clinic, dentist, doctors, hospital, pharmacy, distance, points);

    }

    public void execute(final Subscriber useCaseSubscriber, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage points) {
        this.clinic = clinic;
        this.dentist = dentist;
        this.doctors = doctors;
        this.hospital = hospital;
        this.pharmacy = pharmacy;
        this.distance = distance;
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
                                Observable.create((Observable.OnSubscribe<AmenityPoints>) subscriber ->
                                        subscriber.onError(e)).subscribeOn(Schedulers.from(threadExecutor))
                                        .observeOn(postExecutionThread.getScheduler())
                                        .subscribe(useCaseSubscriber);
                            }

                            @Override
                            public void onNext(Token token) {
                                GetAmenity.this.token = token;
                            }
                        }));
    }

    private void checkInStage() {
        super.execute(useCaseSubscriber);
    }
}
