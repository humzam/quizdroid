package edu.washington.humzam.quizdroid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;

import static android.util.Log.i;

/**
 * Created by humzamangrio on 5/3/15.
 */
public class MathFragment extends Fragment implements Serializable{

    Button beginMath;
    String topic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_topic_math,
                container, false);

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
        }

        beginMath = (Button) view.findViewById(R.id.math_begin);
        beginMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i("TopicActivity", "begin math button clicked");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                QuestionFragment questionFragment = new QuestionFragment();
                Bundle info = new Bundle();
                info.putString("topic", topic);
                questionFragment.setArguments(info);
                ft.replace(R.id.container, questionFragment);
                ft.commit();
            }
        });



        return view;

    }


}
