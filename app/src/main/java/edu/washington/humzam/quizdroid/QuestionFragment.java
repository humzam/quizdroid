package edu.washington.humzam.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.util.Log.i;

/**
 * Created by humzamangrio on 5/4/15.
 */

public class QuestionFragment extends Fragment {

    ArrayList<Question> questions;
    View view;
    int pos;
    int total_correct;
    int total_questions;
    String topic;
    private Activity hostActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.question_layout,
                container, false);

        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            total_correct = getArguments().getInt("totalCorrect");
            total_questions = getArguments().getInt("totalQuestions");
            topic = getArguments().getString("topic");
        } else {
            pos = 0;
            total_questions = 0;
            total_correct = 0;
            topic = "";
        }

        Button submit = (Button) view.findViewById(R.id.submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i("QuestionFragment", "submit button clicked");
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
                int radioGroupID = radioGroup.getCheckedRadioButtonId();
                if (radioGroupID != -1) {
                    Button radioButton = (RadioButton) view.findViewById(radioGroupID);
                    String selection = radioButton.getText().toString();
                    if (selection.equals(questions.get(pos).getAnswer())) {
                        total_correct++;
                    }
                    total_questions++;
                    boolean hasMoreQuestions = true;
                    if (pos + 1 >= questions.size()) {
                        hasMoreQuestions = false;
                    }
                    Bundle info = new Bundle();
                    info.putBoolean("hasMoreQuestions", hasMoreQuestions);
                    info.putInt("totalCorrect", total_correct);
                    info.putInt("totalQuestions", total_questions);
                    info.putString("selection", selection);
                    info.putString("correctAnswer", questions.get(pos).getAnswer());
                    info.putInt("pos", pos);
                    info.putInt("size", questions.size());
                    info.putString("topic", topic);
                    if (hostActivity instanceof TopicActivity) {
                        ((TopicActivity) hostActivity).loadAnswerFrag(info);
                    }
                }
            }
        });

        if (topic.equals("Math")) {
            questions = getMathQuestions();
        } else if (topic.equals("Physics")) {
            questions = getPhysicsQuestions();
        } else {
            questions = getMarvelQuestions();
        }

        Log.i("QuestionFrag", "View id =" + getResources().getResourceEntryName(view.getId()));


        if (questions == null ) {
            Log.i("QuestFrag", "questions array is null");
        }
        Log.i("QuestFrag", "questions length = " + questions.size());
        Log.i("QuestFrag", "Question is at pos : " + pos + " quest " + questions.get(pos).getQuestion());


        nextQuestion(pos);
        return view;

    }

    public void nextQuestion(int pos) {
        Log.i("QuestionFrag", "view is " + view);
        TextView question = (TextView) view.findViewById(R.id.question_title_text_view);
        question.setText("" + questions.get(pos).getQuestion());
        RadioButton option1 = (RadioButton) view.findViewById(R.id.radioButton1);
        option1.setText("" + questions.get(pos).getOption1());
        RadioButton option2 = (RadioButton) view.findViewById(R.id.radioButton2);
        option2.setText("" + questions.get(pos).getOption2());
        RadioButton option3 = (RadioButton) view.findViewById(R.id.radioButton3);
        option3.setText("" + questions.get(pos).getOption3());
        RadioButton option4 = (RadioButton) view.findViewById(R.id.radioButton4);
        option4.setText("" + questions.get(pos).getOption4());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    public ArrayList<Question> getMathQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question("What is 1 + 1?", "3", "2", "0", "1", "2"));
        questions.add(new Question("What is the 2 raised to the sixth power?", "64", "4", "16", "32", "64"));
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
