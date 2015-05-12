package edu.washington.humzam.quizdroid;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by humzamangrio on 5/7/15.
 */

public class QuizApp extends Application {
    private static QuizApp instance = null;
    public static TopicRepository quiz = new InMemoryRepository();
    public static final String TAG = "QuizApp";
    public List<Topic> questions;

    public QuizApp() {
        Log.i(TAG, "constructor fired");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one " + TAG);
        }
        questions = new ArrayList<Topic>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate fired");

        String json = null;

        // Fetch questions.json in assets/ folder
        try {
            InputStream inputStream = getAssets().open("questions.json");
            json = readJSONFile(inputStream);

            JSONArray jsonData = new JSONArray(json);
            for (int topicNumber = 0; topicNumber < jsonData.length(); topicNumber++) {
                Topic newTopic = new Topic();
                JSONObject topic = new JSONObject(jsonData.get(topicNumber).toString());
                String title = topic.getString("title");
                newTopic.setTitle(title);
                String desc = topic.getString("desc");
                newTopic.setShort_desc(desc);
                newTopic.setLong_desc(desc);
                JSONArray questionsData = new JSONArray(topic.get("questions").toString());
                List<Question> questions = new ArrayList<Question>();
                for (int questionNumber = 0; questionNumber < questionsData.length(); questionNumber++) {
                    Question newQuestion = new Question();
                    JSONObject question = new JSONObject(questionsData.get(0).toString());
                    String text = question.getString("text");
                    newQuestion.setQuestion(text);
                    int correct_answer = question.getInt("answer");
                    newQuestion.setAnswer(correct_answer);
                    JSONArray answers = question.getJSONArray("answers");
                    newQuestion.setOption1(answers.get(0).toString());
                    newQuestion.setOption1(answers.get(1).toString());
                    newQuestion.setOption1(answers.get(2).toString());
                    newQuestion.setOption1(answers.get(3).toString());
                    questions.add(newQuestion);
                }
                newTopic.setQuestions(questions);
                this.questions.add(newTopic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

}
