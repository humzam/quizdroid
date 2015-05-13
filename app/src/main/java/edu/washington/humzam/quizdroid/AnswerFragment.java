package edu.washington.humzam.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by humzamangrio on 5/4/15.
 */
public class AnswerFragment extends Fragment {

    View view;
    String correct_answer;
    int total_correct;
    int total_questions;
    String selection;
    int pos;
    int topic;

    TextView yourAnswer;
    TextView correctAnswer;
    TextView total;
    private Activity hostActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.answer_layout,
                container, false);

        boolean hasMoreQuestions = false;
        if (getArguments() != null) {
            correct_answer = getArguments().getString("correctAnswer");
            total_correct = getArguments().getInt("totalCorrect");
            total_questions = getArguments().getInt("totalQuestions");
            selection = getArguments().getString("selection");
            pos = getArguments().getInt("pos");
            topic = getArguments().getInt("topic");
            hasMoreQuestions = getArguments().getBoolean("hasMoreQuestions");
        }


        yourAnswer = (TextView) view.findViewById(R.id.your_answer_text);
        correctAnswer = (TextView) view.findViewById(R.id.correct_answer_text);
        total = (TextView) view.findViewById(R.id.total_correct_text);

        yourAnswer.setText(selection);
        correctAnswer.setText(correct_answer);
        total.setText("You have " + total_correct + " out of " + total_questions + " correct");


        Button next = (Button) view.findViewById(R.id.next_btn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle info = new Bundle();
                info.putInt("pos", pos + 1);
                info.putInt("totalCorrect", total_correct);
                info.putInt("totalQuestions", total_questions);
                info.putInt("topic", topic);
                if (hostActivity instanceof TopicActivity) {
                    ((TopicActivity) hostActivity).loadQuestionFragment(info);
                }
            }
        });

        Button finish = (Button) view.findViewById(R.id.finish_btn);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startOver = new Intent(getActivity(), MainActivity.class);
                startOver.putExtra("pos", 0);
                startActivity(startOver);
            }
        });

        if (hasMoreQuestions) {
            next.setVisibility(View.VISIBLE);
            finish.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.GONE);
            finish.setVisibility(View.VISIBLE);
        }


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }
}
