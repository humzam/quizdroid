package edu.washington.humzam.quizdroid;

/**
 * Created by humzamangrio on 4/27/15.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MathActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchingIntent = getIntent();
        String topic = launchingIntent.getStringExtra("topic");

        if (topic.equals("Math")) {
           setContentView(R.layout.activity_topic_math);
        } else if (topic.equals("Physics")) {
           setContentView(R.layout.activity_topic_physics);
        } else {
            setContentView(R.layout.activity_topic_marvel);
        }


        // how to get the intent

//        TextView txt = (TextView) findViewById();
//        txt.setText(now);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}