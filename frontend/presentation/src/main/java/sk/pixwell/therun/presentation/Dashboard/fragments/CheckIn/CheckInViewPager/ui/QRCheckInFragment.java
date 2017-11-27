package sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.presentation.Dashboard.fragments.CheckIn.CheckInViewPager.presenters.QRCheckInPresenter;
import sk.pixwell.therun.presentation.Dashboard.injection.DashboardComponent;
import sk.pixwell.therun.presentation.Dashboard.ui.DashboardActivity;
import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.StagesInfoSingleton;
import sk.pixwell.therun.presentation.shared.UIUtils;
import sk.pixwell.therun.presentation.shared.fragment.TheRunFragment;

public class QRCheckInFragment extends TheRunFragment implements ZBarScannerView.ResultHandler, QRCheckInView {
    private ZBarScannerView mScannerView;

    @Inject
    QRCheckInPresenter mPresenter;

    @BindView(R.id.content_frame)
    FrameLayout mScanView;

    @BindView(R.id.errorTextView)
    TextView mError;

    @BindView(R.id.parentLayout)
    RelativeLayout mParent;

    @BindView(R.id.firtLineTextView)
    TextView mFirstLine;

    @BindView(R.id.secondLineTextView)
    TextView mSecondLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //mScannerView = new ZBarScannerView(getActivity());

        View layout = inflater.inflate(R.layout.fragment_qr_check_in, container, false);

//ZBarScannerView(Context context, CameraFace cameraFacing, int width, int height, int padding)
        mScannerView = new ZBarScannerView(getActivity()) {

            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };


        ButterKnife.bind(this,layout);

        initializeLayout();
        mFirstLine.bringToFront();
        mSecondLine.bringToFront();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mParent.setVisibility(View.VISIBLE);
                mFirstLine.bringToFront();
                mSecondLine.bringToFront();
            }
        }, 3000);

        return layout;
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        /*Toast.makeText(getActivity(), "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();*/

        try{
            List<StageInfo> stagesInfo = StagesInfoSingleton.getInstance().getStages();
            Boolean result = false;
            StageInfo stage = new StageInfo();
            for(StageInfo data : stagesInfo){
                if(data.getQrString().equals(rawResult.getContents())){
                    result = true;
                    stage = data;
                    break;
                }
            }
            if(result)
                showToastMessage("Etapa " + String.valueOf(stage.getId()));
            mPresenter.checkInStage(stage);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(QRCheckInFragment.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void initializeLayout() {

        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.getPhotoPermission();

    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void photoPermissionGranted() {
        mError.setVisibility(View.GONE);
        mScanView.addView(mScannerView);
    }

    @Override
    public void photoPermissionDenied() {
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSnack(String message) {
        showToastMessage(message);
    }

    public void setPhotoPermission(boolean photoPermission) {
        mPresenter.showPhotoPermissionResult(photoPermission);
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "ZXing";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();

            framingRect.set((int) convertDpToPixel(38, getContext()),(int) convertDpToPixel(150, getContext()),
                    canvas.getWidth() - (int) convertDpToPixel(38, getContext()), calculateWidth(canvas));
            Rect rect = new Rect();
            rect.set(0,0, canvas.getWidth(),framingRect.top);
            Paint black = new Paint();
            black.setColor(UIUtils.getColorWrapper(getContext(), R.color.backgroundColor));
            canvas.drawRect(rect,black);
            rect.set(0,0, framingRect.left,canvas.getHeight());
            canvas.drawRect(rect,black);
            rect.set(framingRect.right,framingRect.top, canvas.getWidth(),canvas.getHeight());
            canvas.drawRect(rect,black);
            rect.set(framingRect.left,framingRect.bottom, canvas.getWidth(),canvas.getHeight());
            canvas.drawRect(rect,black);
        }

        private int calculateWidth(Canvas canvas){
            int size = canvas.getWidth() - (int) convertDpToPixel(38, getContext()) - (int) convertDpToPixel(38, getContext());
            return  size + (int) convertDpToPixel(120, getContext());
        }

        public static float convertDpToPixel(float dp, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return px;
        }
    }

}


    /*private ZXingScannerView mScannerView;

    @Inject
    QRCheckInPresenter mPresenter;

    @BindView(R.id.content_frame)
    FrameLayout mScanView;

    @BindView(R.id.errorTextView)
    TextView mError;

    @BindView(R.id.parentLayout)
    RelativeLayout mParent;

    public static QRCheckInFragment newInstance() {
        QRCheckInFragment fragment = new QRCheckInFragment();
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
        View layout = inflater.inflate(R.layout.fragment_qr_check_in, container, false);

        mScannerView = new ZXingScannerView(getActivity());

        ButterKnife.bind(this,layout);

        initializeLayout();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mParent.setVisibility(View.VISIBLE);
            }
        }, 3000);

        return mScannerView;
    }

    public void initializeLayout() {

        DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.getPhotoPermission();

    }

    private void initializeInjector() {
        getComponent(DashboardComponent.class).inject(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if(mScannerView != null)
            mScannerView.stopCamera();
        super.onDetach();
    }

    @Override
    public void handleResult(Result result) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(QRCheckInFragment.this);
            }
        }, 2000);
    }

    public void setPhotoPermission(boolean photoPermission) {
        mPresenter.showPhotoPermissionResult(photoPermission);
    }

    @Override
    public void photoPermissionGranted() {
        mError.setVisibility(View.GONE);
        if(mScannerView == null) {
            mScannerView = new ZXingScannerView(getContext()) {
                @Override
                protected IViewFinder createViewFinderView(Context context) {
                    return new CustomViewFinderView(context);
                }
            };
            mScannerView.setResultHandler(this);
            mScannerView.startCamera(UIUtils.getCameraId());
            mScannerView.setAutoFocus(true);
            List<BarcodeFormat> formats =  new ArrayList<>();
            formats.add(BarcodeFormat.AZTEC);
            formats.add(BarcodeFormat.CODABAR);
            formats.add(BarcodeFormat.QR_CODE);
            mScannerView.setFormats(formats);
            mScanView.addView(mScannerView);
        }
    }

    @Override
    public void photoPermissionDenied() {
        mError.setVisibility(View.VISIBLE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "ZXing";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();

            framingRect.set((int) convertDpToPixel(38, getContext()),(int) convertDpToPixel(150, getContext()),
                    canvas.getWidth() - (int) convertDpToPixel(38, getContext()), calculateWidth(canvas));
            Rect rect = new Rect();
            rect.set(0,0, canvas.getWidth(),framingRect.top);
            Paint black = new Paint();
            black.setColor(UIUtils.getColorWrapper(getContext(), R.color.backgroundColor));
            canvas.drawRect(rect,black);
            rect.set(0,0, framingRect.left,canvas.getHeight());
            canvas.drawRect(rect,black);
            rect.set(framingRect.right,framingRect.top, canvas.getWidth(),canvas.getHeight());
            canvas.drawRect(rect,black);
            rect.set(framingRect.left,framingRect.bottom, canvas.getWidth(),canvas.getHeight());
            canvas.drawRect(rect,black);
        }

        private int calculateWidth(Canvas canvas){
            int size = canvas.getWidth() - (int) convertDpToPixel(38, getContext()) - (int) convertDpToPixel(38, getContext());
            return  size + (int) convertDpToPixel(120, getContext());
        }

        public static float convertDpToPixel(float dp, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return px;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onStop() {
        if(mScannerView != null)
            mScannerView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if(mScannerView != null)
            mScannerView.stopCamera();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mScannerView != null)
            mScannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(UIUtils.getCameraId());
        mScannerView.setAutoFocus(true);
        List<BarcodeFormat> formats =  new ArrayList<>();
        formats.add(BarcodeFormat.AZTEC);
        formats.add(BarcodeFormat.CODABAR);
        formats.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(formats);
    }




}*/
