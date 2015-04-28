package edu.washington.humzam.quizdroid;

/**
 * Created by humzamangrio on 4/27/15.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MathActivity extends ActionBarActivity {

    Button beginMath;
    Button beginPhysics;
    Button beginMarvel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchingIntent = getIntent();
        final String topic = launchingIntent.getStringExtra("topic");

        // show the appropriate topic overview page depending on what was clicked in MainActivity
        if (topic.equals("Math")) {
           setContentView(R.layout.activity_topic_math);
        } else if (topic.equals("Physics")) {
           setContentView(R.layout.activity_topic_physics);
        } else {
            setContentView(R.layout.activity_topic_marvel);
        }

        beginMath = (Button) findViewById(R.id.math_begin);
        beginPhysics = (Button) findViewById(R.id.physics_begin);
        beginMarvel = (Button) findViewById(R.id.marvel_begin);

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(beginMath);
        buttons.add(beginPhysics);
        buttons.add(beginMarvel);

        // sets onClickListeners for all the buttons
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startQuiz = new Intent(MathActivity.this, QuestionActivity.class);
                    startQuiz.putExtra("topic", topic);
                    startActivity(startQuiz);
                    finish();
                }
            });
        }

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