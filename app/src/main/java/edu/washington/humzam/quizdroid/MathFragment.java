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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_topic_math,
                container, false);

        beginMath = (Button) view.findViewById(R.id.math_begin);
        beginMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i("TopicActivity", "begin math button clicked");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new QuestionFragment());
                ft.commit();
            }
        });



        return view;

    }


}
