package edu.washington.humzam.quizdroid;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public String[] topics;
    private ListView list;
    public static final String TAG = "MainActivity";
    private static final int SETTINGS = 1;
    private String url;
    private int interval;
    private DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate fired");
        setContentView(R.layout.activity_main);



        if (!isConnectedToNetwork()) {
            Toast.makeText(getApplicationContext(), "You didn't connect to the internet",
                    Toast.LENGTH_SHORT).show();

            if(isAirplaneModeOn(this)) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Do you want to turn airplane mode off?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else if (!isMobileAvailable(this)) {
                Toast.makeText(getApplicationContext(), "You didn't have mobile signal!",
                        Toast.LENGTH_SHORT).show();
            }

        }

        IntentFilter filter = new IntentFilter("edu.washington.humzam.quizdroid");
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        registerReceiver(receiver, filter);

        QuizApp quizApp = (QuizApp) getApplication();
        List<Topic> topicsList = quizApp.getTopics();
        topics = new String[topicsList.size()];
        Log.i("MainActivity", "Topics are " + topicsList.toString());
        int pos = 0;
        for (Topic t : topicsList) {
            if (t == null) {
                Log.i(TAG, "this topic is null");
            } else {
                Log.i(TAG, "not null");
            }
            Log.i(TAG, "" + t + " at position " + pos);
            topics[pos] = t.getTitle();
            pos++;
        }
        Log.i(TAG, "before shared preferences");
//        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//        String url = prefs.getString("url", "null");
//        int interval = prefs.getInt("interval", -1);
//        Log.i(TAG, "BEFORE url is " + url);
//        Log.i(TAG, "BEFORE interval is " + interval);
//        if (url.equals("null") && interval == -1) {
//            url = "https://getjsonfile.com/questions.json";
//            interval = 1;
//            Log.i(TAG, "changing null values to real values");
//        } else {
//            Log.i(TAG, "not changing null values");
//        }
//        Log.i(TAG, "AFTER url is " + url);
//        Log.i(TAG, "AFTER interval is " + interval);
//        quizApp.changeUrl(url, interval);
//        Log.i(TAG, "just changed URL");

        list = (ListView) findViewById(R.id.list_topics);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this, R.layout.list_item, topics);
        list.setAdapter(items);

        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go bring some other activity around the item selected
                Intent next = new Intent(MainActivity.this, TopicActivity.class);
                next.putExtra("topic", topics[position]);
                next.putExtra("pos", position);
                Log.i(TAG, "current topic selected is at position = " + position);
                startActivity(next);
                finish();
            }
        });
    }

    // This is your receiver that you registered in the onCreate that will receive any messages that match a download-complete like broadcast
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("MyApp BroadcastReceiver", "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if(c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample","Status Check: "+status);
                        switch(status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    // Get file from Download Manager (which is a system service as explained in the onCreate)
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // YOUR CODE HERE [convert file to String here]
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                                    StringBuilder out = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        out.append(line);
                                    }
                                    String data = "";
                                    data = out.toString();
                                    QuizApp quizApp = (QuizApp) getApplication();
                                    quizApp.writeToFile(data);


                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                DownloadQuestions.startOrStopAlarm(context, false);

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                                builder2.setMessage("Do you want to retry or quitApp?");
                                builder2.setCancelable(true);
                                builder2.setPositiveButton("Retry",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                DownloadQuestions.startOrStopAlarm(MainActivity.this,true);
                                                dialog.cancel();
                                            }
                                        });
                                builder2.setNegativeButton("Quit",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert22 = builder2.create();
                                alert22.show();
                                break;
                        }
                    }
                }
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.preferences) {
            Intent setPreferences = new Intent(MainActivity.this, Preferences.class);
            startActivityForResult(setPreferences, SETTINGS);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult called");
        if(requestCode == SETTINGS) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            url = sharedPreferences.getString("url", "http://tednewardsandbox.site44.com/questions.json");
            interval = Integer.parseInt(sharedPreferences.getString("interval", "1")) * 60000;

            QuizApp.getInstance().changeUrl(url, interval);
        }
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

    }

    public static boolean isMobileAvailable(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((!(tel.getNetworkOperator() != null && tel.getNetworkOperator().equals(""))));
    }
}
