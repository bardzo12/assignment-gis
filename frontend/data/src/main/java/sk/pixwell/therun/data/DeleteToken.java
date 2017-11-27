package sk.pixwell.therun.data;

import rx.Observable;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.executor.PostExecutionThread;
import sk.pixwell.therun.domain.executor.ThreadExecutor;
import sk.pixwell.therun.domain.interactor.UseCase;

/**
 * Created by Tomáš Baránek on 22.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class DeleteToken extends UseCase {

	private final Repository repository;

	public DeleteToken(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
		super(threadExecutor, postExecutionThread);
		this.repository = repository;
	}
	
	@Override
	protected Observable buildUseCaseObservable() {
		return this.repository.clearToken();
	}
}
