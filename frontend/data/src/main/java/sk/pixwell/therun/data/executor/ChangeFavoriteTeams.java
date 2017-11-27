package sk.pixwell.therun.data.executor;

import android.content.Intent;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import sk.pixwell.therun.domain.Repository;
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
public class ChangeFavoriteTeams extends UseCase {

    private final Repository repository;
    private List<Integer> listIDs;

    public ChangeFavoriteTeams(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.changeFavoriteTeams(listIDs);
    }

    public void execute(Subscriber subscriber, List<Integer> list){
        this.listIDs = list;
        super.execute(subscriber);
    }
}
