package edu.washington.humzam.quizdroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public String[] topics;
    private ListView list;
    public static final String TAG = "MainActivity";
    private static final int SETTINGS = 1;
    private String url;
    private int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate fired");
        setContentView(R.layout.activity_main);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult called");
        if(requestCode == SETTINGS) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            url = sharedPreferences.getString("url", "http://tednewardsandbox.site44.com/questions.json");
            interval = Integer.parseInt(sharedPreferences.getString("interval", "1")) * 15000;

            QuizApp.getInstance().changeUrl(url, interval);
        }
    }
}
