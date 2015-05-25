package edu.washington.humzam.quizdroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private int interval;
    private String url;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private boolean alarmAlreadySet;

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

        // this string is where you can specify what file you are looking for inside your data/ directory
        File myFile = new File(getFilesDir().getAbsolutePath(), "/data.json");
        String json = "";

        // Let's get the JSON in the files directory! (aka data/data.json which is a hidden folder that you can't access or see unless its from the app itself)
        // check if data.json file exists in files directory
        if (myFile.exists()) {
            Log.i(TAG, "data.json DOES exist");

            try {
                FileInputStream fis = openFileInput("data.json");      // sweet we found it. openFileInput() takes a string path from your data directory. no need to put 'data/' in your path parameter
                json = readJSONFile(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            // Can't find data.json file in files directory. Fetch data.json in assets
            Log.i(TAG, "data.json DOESN'T exist. Fetch from assets");

            try {
                InputStream inputStream = getAssets().open("data.json");
                json = readJSONFile(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Fetch questions.json in assets/ folder
        try {
//            InputStream inputStream = getAssets().open("questions.json");
//            String json = readJSONFile(inputStream);
            JSONArray jsonData = new JSONArray(json);
//            Log.i(TAG, "json length is " + jsonData.length());
            for (int topicNumber = 0; topicNumber < jsonData.length(); topicNumber++) {
                Topic newTopic = new Topic();
                JSONObject topic = new JSONObject(jsonData.get(topicNumber).toString());
                String title = topic.getString("title");
//                Log.i(TAG, "title is " + title);
                newTopic.setTitle(title);
                String desc = topic.getString("desc");
//                Log.i(TAG, "desc is " + desc);
                newTopic.setShort_desc(desc);
                newTopic.setLong_desc(desc);
                JSONArray questionsData = new JSONArray(topic.get("questions").toString());
                List<Question> questions = new ArrayList<Question>();
                for (int questionNumber = 0; questionNumber < questionsData.length(); questionNumber++) {
                    Question newQuestion = new Question();
                    JSONObject question = new JSONObject(questionsData.get(questionNumber).toString());
                    String text = question.getString("text");
//                    Log.i(TAG, "question is " + text);
                    newQuestion.setQuestion(text);
                    int correct_answer = question.getInt("answer");
                    newQuestion.setAnswer(correct_answer);
                    JSONArray answers = question.getJSONArray("answers");
                    newQuestion.setOption1(answers.get(0).toString());
                    newQuestion.setOption2(answers.get(1).toString());
                    newQuestion.setOption3(answers.get(2).toString());
                    newQuestion.setOption4(answers.get(3).toString());
//                    Log.i(TAG, "first answer is " + answers.get(0));
//                    Log.i(TAG, "second answer is " + answers.get(1));
//                    Log.i(TAG, "third answer is " + answers.get(2));
//                    Log.i(TAG, "fourth answer is " + answers.get(3));
                    questions.add(newQuestion);
                }
                newTopic.setQuestions(questions);
                this.questions.add(newTopic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DownloadQuestions.startOrStopAlarm(this, true);

        alarmAlreadySet = false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        url = sharedPreferences.getString("url", "http://tednewardsandbox.site44.com/questions.json");
        interval = Integer.parseInt(sharedPreferences.getString("interval", "1")) * 60000;
        alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);


        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(QuizApp.this, url, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Making Toast");
            }
        };

        registerReceiver(alarmReceiver, new IntentFilter("humzam.washington.edu.getData"));

        Intent intent = new Intent();
        intent.setAction("humzam.washington.edu.getData");
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        changeUrl(url, interval);

        quiz.setTopics(questions);
    }

    public void writeToFile(String data) {
        try {
            Log.i(TAG, "writing downloaded to file");

            File file = new File(getFilesDir().getAbsolutePath(), "data.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
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


    public void changeUrl(String url, int interval) {
//        this.interval = interval * 15000;
//        this.url = url;
//        alarmIntent = new Intent(instance, CheckQuestions.class);
//        alarmIntent.putExtra("url", url);
//        AlarmManager alarmManager = (AlarmManager) instance.getSystemService(ALARM_SERVICE);
//        if (pendingIntent != null) {
//            alarmManager.cancel(pendingIntent);
//        }
//        pendingIntent = PendingIntent.getBroadcast(instance, 0, alarmIntent, 0);
//        alarmManager.setInexactRepeating(AlarmManager.RTC, 1000, interval, pendingIntent);
        this.url = url;
        this.interval = interval;
        if (alarmAlreadySet) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            alarmAlreadySet = false;
        }
        alarmAlreadySet = true;
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, interval, pendingIntent);


        Log.i(TAG, "changing URL method called");
    }

    public static QuizApp getInstance() {
        return instance;
    }

    public String readJSONFile(FileInputStream fileInputStream) throws IOException {
        int size = fileInputStream.available();
        byte[] buffer = new byte[size];
        fileInputStream.read(buffer);
        fileInputStream.close();

        return new String(buffer, "UTF-8");
    }

}
