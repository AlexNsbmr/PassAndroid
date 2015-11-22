package org.ligi.passandroid;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;

import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import org.ligi.tracedroid.TraceDroid;
import org.ligi.tracedroid.logging.Log;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .trackerModule(new TrackerModule(this))
                .build();

        LeakCanary.install(this);
        JodaTimeAndroid.init(this);
        initTraceDroid();
    }

    private void initTraceDroid() {
        TraceDroid.init(this);
        Log.setTAG("PassAndroid");
    }

    public static String getPassesDir(final Context ctx) {
        return ctx.getFilesDir().getAbsolutePath() + "/passes";
    }

    public static String getShareDir() {
        return Environment.getExternalStorageDirectory() + "/tmp/passbook_share_tmp/";
    }

    public static AppComponent component() {
        return component;
    }

    @VisibleForTesting
    public static void setComponent(AppComponent newComponent) {
        component = newComponent;
    }
}
