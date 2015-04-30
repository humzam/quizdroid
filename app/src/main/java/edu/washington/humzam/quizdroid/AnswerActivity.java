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

/**
 * Created by humzamangrio on 4/28/15.
 */
public class AnswerActivity extends Activity {

    RadioGroup radioGroup;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    RadioButton radioButton;
    Button next;
    Button finish;
    Button submit;

    ArrayList<Question> questions;
    int pos;
    String topic;
    int total_questions;
    int total_correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = this.getIntent().getExtras();
        pos = b.getInt("pos");
        int size = b.getInt("size");
        String theirAnswer = b.getString("answer");
        String correct = b.getString("correct");
        topic = b.getString("topic");
        boolean hasMoreQuestions = b.getBoolean("end");
        total_questions = b.getInt("total questions");
        total_correct = b.getInt("total correct");

        if (topic.equals("Math")) {
            questions = getMathQuestions();
        } else if (topic.equals("Physics")) {
            questions = getPhysicsQuestions();
        } else {
            questions = getMarvelQuestions();
        }

        setContentView(R.layout.answer_layout);

        TextView yourAnswer = (TextView) findViewById(R.id.your_answer_text);
        TextView correctAnswer = (TextView) findViewById(R.id.correct_answer_text);
//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        next = (Button) findViewById(R.id.next_btn);
        finish = (Button) findViewById(R.id.finish_btn);
//        int selected_id = radioGroup.getCheckedRadioButtonId();
//        RadioButton answer = (RadioButton) findViewById(selected_id);

        option1 = (RadioButton) findViewById(R.id.radioButton1);
        option2 = (RadioButton) findViewById(R.id.radioButton2);
        option3 = (RadioButton) findViewById(R.id.radioButton3);
        option4 = (RadioButton) findViewById(R.id.radioButton4);

//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        int radioGroupID = radioGroup.getCheckedRadioButtonId();
//        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroupID);

//        String user_answer = "";
//        if (option1.isChecked()) {
//            user_answer = "" + option1.getText();
//        } else if (option2.isChecked()) {
//            user_answer = "" + option2.getText();
//        } else if (option3.isChecked()) {
//            user_answer = "" + option3.getText();
//        } else {
//            user_answer = "" + option4.getText();
//        }

        yourAnswer.setText(theirAnswer);
        correctAnswer.setText(correct);
//        if (yourAnswer.getText() == correctAnswer.getText()) {
//            total_correct++;
//        }
//        total_questions++;
        TextView total = (TextView) findViewById(R.id.total_correct_text);
        total.setText("You have " + total_correct + " out of " + total_questions + " correct");
        if (hasMoreQuestions) {
            next.setVisibility(View.VISIBLE);
            finish.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.GONE);
            finish.setVisibility(View.VISIBLE);
        }

        next = (Button) findViewById(R.id.next_btn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setContentView(R.layout.question_layout);
//                pos++;
//                nextQuestion(pos);
//                if (pos >= questions.size()) {
//                    next.setVisibility(View.GONE);
//                    finish.setVisibility(View.VISIBLE);
//                } else {
//                    next.setVisibility(View.VISIBLE);
//                    finish.setVisibility(View.GONE);
//                }

                Intent showAnswer = new Intent(AnswerActivity.this, QuestionActivity.class);
//                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//                int radioGroupID = radioGroup.getCheckedRadioButtonId();
//                radioButton = (RadioButton) findViewById(radioGroupID);
//                selection = radioButton.getText().toString();
                showAnswer.putExtra("pos", pos + 1);
                showAnswer.putExtra("size", questions.size());
//                showAnswer.putExtra("answer", selection);
                showAnswer.putExtra("correct", questions.get(pos).getAnswer());
                showAnswer.putExtra("questions", questions);
                showAnswer.putExtra("topic", topic);
                showAnswer.putExtra("total correct", total_correct);
                showAnswer.putExtra("total questions", total_questions);
                startActivity(showAnswer);
                finish();
            }
        });

//        submit = (Button) findViewById(R.id.submit_btn);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent showAnswer = new Intent(AnswerActivity.this, QuestionActivity.class);
//                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//                int radioGroupID = radioGroup.getCheckedRadioButtonId();
//                radioButton = (RadioButton) findViewById(radioGroupID);
//                String selection = radioButton.getText().toString();
//                showAnswer.putExtra("pos", pos);
//                showAnswer.putExtra("size", questions.size());
//                showAnswer.putExtra("answer", selection);
//                showAnswer.putExtra("correct", questions.get(pos).getAnswer());
//                showAnswer.putExtra("questions", questions);
//                showAnswer.putExtra("topic", topic);
//                startActivity(showAnswer);
//                finish();
//            }
//        });

        finish = (Button) findViewById(R.id.finish_btn);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startOver = new Intent(AnswerActivity.this, MainActivity.class);
                startOver.putExtra("pos", 0);
                startActivity(startOver);
                finish();
            }
        });

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
        questions.add(new Question("What is 1 + 1?", "3", "2", "0", "1", "2"));
        questions.add(new Question("What is the 2 raised to the sixth power?", "64", "4", "16", "32", "2"));
        questions.add(new Question("What is the derivative of 5x^2?", "3x", "10x^2", "5x^2", "10x", "10x"));
        return questions;
    }

    public ArrayList<Question> getPhysicsQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is the acceleration of an object near the surface of the earth?", "9.8 m/s", "3.14 m/s^2", "4.9 m/s^2", "9.8 ft/sec^2", "9.8 m/s^2"));
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
