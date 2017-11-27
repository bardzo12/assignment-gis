package sk.pixwell.therun.presentation.Dashboard.fragments.Run.RunViewPager.text_watchers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.List;

import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 30.5.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class ManulQREnter implements TextWatcher {


    private TextInputLayout textInputEditText;
    private Context context;
    private List<StageInfo> stages;


    public ManulQREnter(Context context, TextInputLayout textInputEditText, List<StageInfo> stages) {
        this.textInputEditText = textInputEditText;
        this.context = context;
        this.stages = stages;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Boolean qrControl = false;
        if(s.toString().length() > 4 ) {
            for (StageInfo data : stages) {
                if (data.getQrString().equals(s.toString())) {
                    qrControl = true;
                    break;
                }
            }
        }
        if (qrControl) {
            textInputEditText.setErrorEnabled(false);
        } else {
            textInputEditText.setErrorEnabled(true);
            textInputEditText.setError("Nesprávne zadaný kód etapy");
        }
    }
}
