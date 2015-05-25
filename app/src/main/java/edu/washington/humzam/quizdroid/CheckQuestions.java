package edu.washington.humzam.quizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by humzamangrio on 5/19/15.
 */
public class CheckQuestions extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        Log.i("CheckQuestions", "making toast");
//        Toast.makeText(context, "URL to check is " + url, Toast.LENGTH_SHORT).show();
        Intent downloadServiceIntent = new Intent(context, DownloadQuestions.class);
        context.startService(downloadServiceIntent);
    }
}
