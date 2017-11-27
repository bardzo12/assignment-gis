package sk.pixwell.therun.domain.subscriber;

import sk.pixwell.therun.domain.Token;

/**
 * Created by Tomáš Baránek on 11.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class TokenSubscriber extends DefaultSubscriber<Token> {

    private SubscriberListener mListener;

    public TokenSubscriber(SubscriberListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCompleted() {
        if (mListener != null) {
            mListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mListener != null) {
            mListener.onError(e);
        }
    }

    @Override
    public void onNext(Token token) {
        if (mListener != null) {
            mListener.onNext(token);
        }
    }

    public void setListener(SubscriberListener listener) {
        this.mListener = listener;
    }

    public interface SubscriberListener {
        void onCompleted();

        void onError(Throwable e);

        void onNext(Token token);
    }
}

