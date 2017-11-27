package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.SupportPresenter;
import sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.presenters.TimeLinePresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

/**
 * Created by Tomáš Baránek on 11.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class SupportFragment extends TheRunFragment implements SupportView {

    @Inject
    SupportPresenter mPresenter;

    @BindView(R.id.sendButton)
    Button mSend;

    @BindView(R.id.messageTextView)
    EditText mMessage;

    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_support, container, false);

        ButterKnife.bind(this,layout);

        initializeLayout();

        return layout;
    }

    public void initializeLayout() {
        mMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    mSend.setEnabled(true);
                else
                    mSend.setEnabled(false);
            }
        });
    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
