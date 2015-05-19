package edu.washington.humzam.quizdroid;

/**
 * Created by humzamangrio on 4/27/15.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.i;


public class TopicActivity extends Activity {

    Button next;
    Button finish;


    Button submit;
    TextView yourAnswer;
    TextView correctAnswer;

    int pos; // topic number

    private static final String TAG = "TopicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchingIntent = getIntent();
        Bundle b = launchingIntent.getExtras();
        pos = b.getInt("pos");

        setContentView(R.layout.topic_layout);


        addOverviewFragment();

        submit = (Button) findViewById(R.id.submit_btn);
        yourAnswer = (TextView) findViewById(R.id.your_answer_text);
        correctAnswer = (TextView) findViewById(R.id.correct_answer_text);
        next = (Button) findViewById(R.id.next_btn);
        finish = (Button) findViewById(R.id.finish_btn);
    }

    private void addOverviewFragment() {
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle info = new Bundle();
        info.putInt("pos", pos);
        overviewFragment.setArguments(info);
        getFragmentManager().beginTransaction().add(R.id.container, overviewFragment).commit();

    }

    public void loadAnswerFrag(Bundle info) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setArguments(info);
        ft.replace(R.id.container, answerFragment);
        ft.commit();
    }

    public void loadQuestionFragment(Bundle info) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(info);
        ft.replace(R.id.container, questionFragment);
        ft.commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}