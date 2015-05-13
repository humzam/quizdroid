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
import android.widget.TextView;

import java.util.List;

import static android.util.Log.i;

/**
 * Created by humzamangrio on 5/5/15.
 */
public class OverviewFragment extends Fragment {

    Button begin;
    String topic;
    int position;

    private static final String TAG = "OverviewFragment";

    private List<Topic> topicsList;

   private Activity hostActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.overview_layout,
                container, false);

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            position = getArguments().getInt("pos");
            Log.i(TAG, "position in overviewfrag = " + position);
        }
        getTopicsArray();

//        if (hostActivity instanceof TopicActivity) {
//            ((TopicActivity) hostActivity).loadOverviewFragment(info);
//        }

//        String[] topicsArray = getTopicsArray();
        TextView topicTitle = (TextView) view.findViewById(R.id.topic_title);
        TextView topicDesc = (TextView) view.findViewById(R.id.topic_desc);
        TextView totalQuestions = (TextView) view.findViewById(R.id.total_question_text);

        topicTitle.setText(topicsList.get(position).getTitle());
        topicDesc.setText(topicsList.get(position).getShort_desc());
        totalQuestions.setText("Total Questions: " + topicsList.get(position).getQuestions().size());

        begin = (Button) view.findViewById(R.id.begin);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i(TAG, "begin button clicked for " + topic);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                QuestionFragment questionFragment = new QuestionFragment();
                Bundle info = new Bundle();
//                info.putString("topic", topic);
                info.putInt("topic", position);   // specifies which topic we are currently working with
                questionFragment.setArguments(info);
                ft.replace(R.id.container, questionFragment);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    public String[] getTopicsArray() {
        QuizApp quizApp = (QuizApp) getActivity().getApplication();
        topicsList = quizApp.getTopics();
        String []topics = new String[topicsList.size()];
        int pos = 0;
        for (Topic t : topicsList) {
            topics[pos] = t.getTitle();
            pos++;
        }
        return topics;
    }

}
