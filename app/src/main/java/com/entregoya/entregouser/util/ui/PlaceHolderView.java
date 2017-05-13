package com.entregoya.entregouser.util.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entregoya.entregouser.R;


public class PlaceHolderView extends FrameLayout {

    private TextView tvError;
    private ImageView ivPlaceHolder;
    private View progressBar;
    private View dataContainer;
    private LinearLayout llErrorContainer;

    public PlaceHolderView(Context context) {
        super(context);
        init();
    }

    public PlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setDataContainer(View dataContainer) {
        this.dataContainer = dataContainer;
        init();
    }

    private void init() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_placeholder_view, this, false);

        llErrorContainer = (LinearLayout) view.findViewById(R.id.layout_placeholder_view_ll_error_container);
        tvError = (TextView) view.findViewById(R.id.layout_placeholder_view_tv_error);
        ivPlaceHolder = (ImageView) view.findViewById(R.id.layout_placeholder_view_iv_placeholder);
        progressBar = view.findViewById(R.id.layout_placeholder_view_progress_bar);
        progressBar.setVisibility(GONE);

        this.addView(view);
    }

    public void showProgress() {
        this.setVisibility(VISIBLE);
        dataContainer.setVisibility(GONE);
        llErrorContainer.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    public void hideProgress() {
        this.setVisibility(GONE);
        dataContainer.setVisibility(VISIBLE);
        llErrorContainer.setVisibility(GONE);
        progressBar.setVisibility(GONE);
    }

    public void showError(String error, int imageId) {
        this.setVisibility(VISIBLE);
        dataContainer.setVisibility(GONE);
        progressBar.setVisibility(GONE);

        llErrorContainer.setVisibility(VISIBLE);
        tvError.setText(error);
        ivPlaceHolder.setImageResource(imageId);
    }
}
