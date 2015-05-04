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

import static android.util.Log.i;


public class TopicActivity extends Activity {

    Button beginMath;
    Button beginPhysics;
    Button beginMarvel;

    Button next;
    Button finish;

    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;

    Button submit;
    TextView yourAnswer;
    TextView correctAnswer;
    ArrayList<Question> questions;

    String topic;

    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addDynamicFragment();
        Intent launchingIntent = getIntent();
        Bundle b = launchingIntent.getExtras();
        topic = b.getString("topic");

        setContentView(R.layout.topic_layout);

        // show the appropriate topic overview page depending on what was clicked in MainActivity
        if (topic.equals("Math")) {
//           setContentView(R.layout.activity_topic_math);
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.container, new MathFragment());
//            ft.commit();
           questions = getMathQuestions();
        } else if (topic.equals("Physics")) {
//           setContentView(R.layout.activity_topic_physics);
           questions = getPhysicsQuestions();
        } else {
//           setContentView(R.layout.activity_topic_marvel);
           questions = getMarvelQuestions();
        }


//        beginMath = (Button) findViewById(R.id.math_begin);
//        if (beginMath != null) {
//            beginMath.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    i("TopicActivity", "begin math button clicked");
////                    Intent startMath = new Intent(TopicActivity.this, QuestionActivity.class);
////                    startMath.putExtra("topic", topic);
////                    startActivity(startMath);
////                    finish();
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.container, new QuestionFragment());
//                    ft.commit();
//                }
//            });
//        }


        beginPhysics = (Button) findViewById(R.id.physics_begin);
        if (beginPhysics != null) {
            beginPhysics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i("TopicActivity", "begin physics button clicked");
                    Intent startPhysics = new Intent(TopicActivity.this, QuestionActivity.class);
                    startPhysics.putExtra("topic", topic);
                    startActivity(startPhysics);
                    finish();
                }
            });
        }

        beginMarvel = (Button) findViewById(R.id.marvel_begin);
        if (beginMarvel != null) {
            beginMarvel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i("TopicActivity", "begin marvel button clicked");
                    Intent startMarvel = new Intent(TopicActivity.this, QuestionActivity.class);
                    startMarvel.putExtra("topic", topic);
                    startActivity(startMarvel);
                    finish();
                }
            });
        }

        submit = (Button) findViewById(R.id.submit_btn);
        yourAnswer = (TextView) findViewById(R.id.your_answer_text);
        correctAnswer = (TextView) findViewById(R.id.correct_answer_text);
        next = (Button) findViewById(R.id.next_btn);
        finish = (Button) findViewById(R.id.finish_btn);
    }


    private void addDynamicFragment() {
        // TODO Auto-generated method stub
        // creating instance of the HelloWorldFragment.
        MathFragment mathFragment = new MathFragment();
        // adding fragment to relative layout by using layout id
        getFragmentManager().beginTransaction().add(R.id.container, mathFragment).commit();
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

    public ArrayList<Question> getMathQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is 1 + 1?", "3", "2", "0", "1", "2"));
        questions.add(new Question("What is the 2 raised to the sixth power?", "64", "4", "16", "32", "2"));
        questions.add(new Question("What is the derivative of 5x^2?", "3x", "10x^2", "5x^2", "10x", "10x"));
        return questions;
    }

    public ArrayList<Question> getPhysicsQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is the acceleration of an object near the surface of the earth?", "9.8 m/s", "3.14 m/s^2", "4.9 m/s^2", "9.8 m/s^2", "9.8 m/s^2"));
        questions.add(new Question("An object at rest will stay at rest until acted upon by an external force. This is known as Newton's:", "Second Law", "First Law", "Theory of Inertia", "Third Law", "First Law"));
        questions.add(new Question("What is the equation for momentum?", "p = m*v", "p = d*v", "p = m * g * v", "p = I * r", "p = m*v"));
        return questions;
    }

    public ArrayList<Question> getMarvelQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Which of the following is NOT a Marvel super hero?", "Spiderman", "Iron Man", "Batman", "Wolverine", "Batman"));
        questions.add(new Question("What is the Hulk's real name?", "Dr. Banter", "Dr. Brown", "Dr. Bruce Bowen", "Dr. Bruce Banner", "Dr. Bruce Banner"));
        questions.add(new Question("Which hero is played by actor Chris Evans?", "Captain America", "Flash", "Wolverine", "Spiderman", "Captain America"));
        return questions;
    }
}