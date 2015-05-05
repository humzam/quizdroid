package edu.washington.humzam.quizdroid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.util.Log.i;

/**
 * Created by humzamangrio on 5/5/15.
 */
public class MarvelFragment extends Fragment {

    Button beginMarvel;
    String topic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_topic_marvel,
                container, false);

        topic = getArguments().getString("topic");

        beginMarvel = (Button) view.findViewById(R.id.marvel_begin);
        beginMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i("TopicActivity", "begin marvel button clicked");
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
