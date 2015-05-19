package edu.washington.humzam.quizdroid;

import android.app.Application;
import android.nfc.Tag;
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
    private static TopicRepository quiz;
    public static final String TAG = "QuizApp";
    private List<Topic> questions;

    public QuizApp() {
        Log.i(TAG, "constructor fired");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one " + TAG);
        }
        questions = new ArrayList<Topic>();
        quiz = new InMemoryRepository();
    }

    public List<Topic> getTopics() {
        return questions;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate fired");

        // Fetch questions.json in assets/ folder
        try {
            InputStream inputStream = getAssets().open("questions.json");
            String json = readJSONFile(inputStream);

            JSONArray jsonData = new JSONArray(json);
            Log.i(TAG, "json length is " + jsonData.length());
            for (int topicNumber = 0; topicNumber < jsonData.length(); topicNumber++) {
                Topic newTopic = new Topic();
                JSONObject topic = new JSONObject(jsonData.get(topicNumber).toString());
                String title = topic.getString("title");
                Log.i(TAG, "title is " + title);
                newTopic.setTitle(title);
                String desc = topic.getString("desc");
                Log.i(TAG, "desc is " + desc);
                newTopic.setShort_desc(desc);
                newTopic.setLong_desc(desc);
                JSONArray questionsData = new JSONArray(topic.get("questions").toString());
                List<Question> questions = new ArrayList<Question>();
                for (int questionNumber = 0; questionNumber < questionsData.length(); questionNumber++) {
                    Question newQuestion = new Question();
                    JSONObject question = new JSONObject(questionsData.get(questionNumber).toString());
                    String text = question.getString("text");
                    Log.i(TAG, "question is " + text);
                    newQuestion.setQuestion(text);
                    int correct_answer = question.getInt("answer");
                    newQuestion.setAnswer(correct_answer);
                    JSONArray answers = question.getJSONArray("answers");
                    newQuestion.setOption1(answers.get(0).toString());
                    newQuestion.setOption2(answers.get(1).toString());
                    newQuestion.setOption3(answers.get(2).toString());
                    newQuestion.setOption4(answers.get(3).toString());
                    Log.i(TAG, "first answer is " + answers.get(0));
                    Log.i(TAG, "second answer is " + answers.get(1));
                    Log.i(TAG, "third answer is " + answers.get(2));
                    Log.i(TAG, "fourth answer is " + answers.get(3));
                    questions.add(newQuestion);
                }
                newTopic.setQuestions(questions);
                this.questions.add(newTopic);
            }
        } catch (IOException e) {
            Log.i(TAG, "json thing failed");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i(TAG, "json thing failed");
            e.printStackTrace();
        }
        quiz.setTopics(questions);
        Log.i(TAG, "questions " + questions);
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
