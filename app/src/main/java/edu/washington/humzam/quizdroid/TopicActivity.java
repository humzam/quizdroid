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

    String topic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchingIntent = getIntent();
        Bundle b = launchingIntent.getExtras();
        topic = b.getString("topic");

        setContentView(R.layout.topic_layout);

        QuizApp quizApp = (QuizApp) getApplication();
        List<Topic> topics = quizApp.getTopics();

        // show the appropriate topic overview page depending on what was clicked in MainActivity
        if (topic.equals("Math")) {
            addMathFragment();
        } else if (topic.equals("Physics")) {
            addPhysicsFragment();
        } else {
            addMarvelFragment();
        }

        submit = (Button) findViewById(R.id.submit_btn);
        yourAnswer = (TextView) findViewById(R.id.your_answer_text);
        correctAnswer = (TextView) findViewById(R.id.correct_answer_text);
        next = (Button) findViewById(R.id.next_btn);
        finish = (Button) findViewById(R.id.finish_btn);
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


    private void addMathFragment() {
        // creating instance of the HelloWorldFragment.
        MathFragment mathFragment = new MathFragment();
        Bundle info = new Bundle();
        info.putString("topic", topic);
        mathFragment.setArguments(info);
        // adding fragment to relative layout by using layout id
        getFragmentManager().beginTransaction().add(R.id.container, mathFragment).commit();
    }

    private void addPhysicsFragment() {
        // creating instance of the HelloWorldFragment.
        PhysicsFragment physicsFragment = new PhysicsFragment();
        Bundle info = new Bundle();
        info.putString("topic", topic);
        physicsFragment.setArguments(info);
        // adding fragment to relative layout by using layout id
        getFragmentManager().beginTransaction().add(R.id.container, physicsFragment).commit();
    }

    private void addMarvelFragment() {
        // creating instance of the HelloWorldFragment.
        MarvelFragment marvelFragment = new MarvelFragment();
        Bundle info = new Bundle();
        info.putString("topic", topic);
        marvelFragment.setArguments(info);
        // adding fragment to relative layout by using layout id
        getFragmentManager().beginTransaction().add(R.id.container, marvelFragment).commit();
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