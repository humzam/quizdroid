package edu.washington.humzam.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by humzamangrio on 4/28/15.
 */
public class QuestionActivity extends Activity{

    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    RadioButton radioButton;
    Button submit;
    TextView yourAnswer;
    TextView correctAnswer;
    ArrayList<Question> questions;
    int pos = 0;
    String selection;
    String topic;
    int total_questions;
    int total_correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchingIntent = getIntent();
        topic = launchingIntent.getStringExtra("topic");
        Bundle b = launchingIntent.getExtras();
        pos = b.getInt("pos");
        total_questions = b.getInt("total questions");
        total_correct = b.getInt("total correct");


        if (topic.equals("Math")) {
            questions = getMathQuestions();
        } else if (topic.equals("Physics")) {
            questions = getPhysicsQuestions();
        } else {
            questions = getMarvelQuestions();
        }
        setContentView(R.layout.question_layout);

        nextQuestion(pos);
        yourAnswer = (TextView) findViewById(R.id.your_answer_text);
        correctAnswer = (TextView) findViewById(R.id.correct_answer_text);

        submit = (Button) findViewById(R.id.submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAnswer = new Intent(QuestionActivity.this, AnswerActivity.class);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int radioGroupID = radioGroup.getCheckedRadioButtonId();
                if (radioGroupID != -1) {
                    String[] options = getOptionsArray();
                    radioButton = (RadioButton) findViewById(radioGroupID);
                    selection = radioButton.getText().toString();
                    showAnswer.putExtra("pos", pos);
                    showAnswer.putExtra("size", questions.size());
                    showAnswer.putExtra("answer", selection);
                    showAnswer.putExtra("correct", options[questions.get(pos).getAnswer()]);
                    showAnswer.putExtra("questions", questions);
                    showAnswer.putExtra("topic", topic);
                    if (selection.equals(options[questions.get(pos).getAnswer()])) {
                        total_correct++;
                    }
                    total_questions++;
                    showAnswer.putExtra("total questions", total_questions);
                    showAnswer.putExtra("total correct", total_correct);
                    boolean hasMoreQuestions = true;
                    if (pos + 1 >= questions.size()) {
                        hasMoreQuestions = false;
                    }
                    showAnswer.putExtra("end", hasMoreQuestions);
                    startActivity(showAnswer);
                    finish();
                }
            }
        });
    }

    // method that returns an string array of all the options for this questions
    public String[] getOptionsArray() {
        String[] options = {questions.get(pos).getOption1(), questions.get(pos).getOption2(),
                questions.get(pos).getOption3(), questions.get(pos).getOption1()};
        return options;
    }

    public void nextQuestion(int pos) {
        TextView question = (TextView) findViewById(R.id.question_title_text_view);
        question.setText("" + questions.get(pos).getQuestion());
        option1 = (RadioButton) findViewById(R.id.radioButton1);
        option1.setText("" + questions.get(pos).getOption1());
        option2 = (RadioButton) findViewById(R.id.radioButton2);
        option2.setText("" + questions.get(pos).getOption2());
        option3 = (RadioButton) findViewById(R.id.radioButton3);
        option3.setText("" + questions.get(pos).getOption3());
        option4 = (RadioButton) findViewById(R.id.radioButton4);
        option4.setText("" + questions.get(pos).getOption4());

    }

    public ArrayList<Question> getMathQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is 1 + 1?", "3", "2", "0", "1", 1));
        questions.add(new Question("What is the 2 raised to the sixth power?", "64", "4", "16", "32", 0));
        questions.add(new Question("What is the derivative of 5x^2?", "3x", "10x^2", "5x^2", "10x", 3));
        return questions;
    }

    public ArrayList<Question> getPhysicsQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is the acceleration of an object near the surface of the earth?", "9.8 m/s", "3.14 m/s^2", "4.9 m/s^2", "9.8 m/s^2", 3));
        questions.add(new Question("An object at rest will stay at rest until acted upon by an external force. This is known as Newton's:", "Second Law", "First Law", "Theory of Inertia", "Third Law", 1));
        questions.add(new Question("What is the equation for momentum?", "p = m*v", "p = d*v", "p = m * g * v", "p = I * r", 0));
        return questions;
    }

    public ArrayList<Question> getMarvelQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Which of the following is NOT a Marvel super hero?", "Spiderman", "Iron Man", "Batman", "Wolverine", 2));
        questions.add(new Question("What is the Hulk's real name?", "Dr. Banter", "Dr. Brown", "Dr. Bruce Bowen", "Dr. Bruce Banner", 3));
        questions.add(new Question("Which hero is played by actor Chris Evans?", "Captain America", "Flash", "Wolverine", "Spiderman", 0));
        return questions;
    }
}
