package edu.washington.humzam.quizdroid;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by humzamangrio on 5/24/15.
 */
public class DownloadQuestions extends IntentService {
    private DownloadManager dm;
    private long enqueue;
    public static final int MY_ALARM = 21;

    public DownloadQuestions() {
        super("DownloadQuestions");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.i("DownloadService", "entered onHandleIntent()");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPrefs.getString("url", "http://tednewardsandbox.site44.com/questions.json");

        Toast.makeText(getApplicationContext(), "Download", Toast.LENGTH_LONG).show();
        Log.i("url", "" + url);

        // Start the download
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        enqueue = dm.enqueue(request);

    }

    public static void startOrStopAlarm(Context context, boolean on) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);

        Intent alarmReceiverIntent = new Intent(context, CheckQuestions.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, MY_ALARM, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (on) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            String refreshInterval = sharedPrefs.getString("interval", "10000");
            int intervalInMillis = Integer.parseInt(refreshInterval) * 60000;
            Log.i("DownloadService", "setting alarm to " + refreshInterval);
            // Start the alarm manager to repeat
            manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), intervalInMillis, pendingIntent);
        }
        else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.i("DownloadService", "Stopping alarm");
        }
    }

}
