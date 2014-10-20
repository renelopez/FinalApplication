package com.example.usuario.finalapplication.net;

import android.view.View;

/**
 * Created by Usuario on 18/10/2014.
 */
public class ProgressBarAnimation implements OnBackgroundTaskAnimation {
    private final View view ;

    public ProgressBarAnimation(View viewById) {
        this.view=viewById;
    }

    @Override
    public void startAnimation() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopAnimation() {
        view.setVisibility(View.INVISIBLE);
    }
}
