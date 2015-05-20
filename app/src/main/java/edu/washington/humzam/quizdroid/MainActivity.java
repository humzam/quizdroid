package edu.washington.humzam.quizdroid;

import android.content.Intent;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.preferences) {
            Intent setPreferences = new Intent(MainActivity.this, Preferences.class);
            startActivity(setPreferences);
        }

        return super.onOptionsItemSelected(item);
    }
}
