package sk.pixwell.therun.data.executor;

import rx.Observable;
import rx.Subscriber;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;

/**
 * Created by Tomáš Baránek on 10.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class CredentialsLogin extends UseCase {

    Repository repository;
    private String email;
    private String password;

    public CredentialsLogin(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        return repository.authWithEmail(email,password);
    }

    public void execute(Subscriber subscriber, String email, String password){
        this.email = email;
        this.password = password;
        super.execute(subscriber);
    }
}
