package com.example.graphpick;

import android.app.Application;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Portrait extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Set the default screen orientation for the entire app to portrait
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // Set the requested orientation to portrait and ignore auto-rotate setting
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
