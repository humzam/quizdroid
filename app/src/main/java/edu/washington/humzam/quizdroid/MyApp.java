package edu.washington.humzam.quizdroid;

import android.app.Application;
import android.util.Log;

/**
 * Created by humzamangrio on 5/7/15.
 */
public class MyApp extends Application {
    private static MyApp instance = null;

    public MyApp() {
        Log.i("MyApp", "constructor fired");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one MyApp");
        }
    }

}
