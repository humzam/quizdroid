package edu.washington.humzam.quizdroid;

import android.app.Application;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by humzamangrio on 5/7/15.
 */
public class QuizApp extends Application {
    private static QuizApp instance = null;
    public static TopicRepository quiz = new InMemoryRepository();
    public static final String TAG = "QuizApp";

    public QuizApp() {
        Log.i(TAG, "constructor fired");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one " + TAG);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate fired");

        String json = null;

        // Fetch data.json in assets/ folder
        try {
            InputStream inputStream = getAssets().open("data.json");
            json = readJSONFile(inputStream);

            JSONObject jsonData = new JSONObject(json);

            // get the array that exist in the key 'questions'
            /*
                {
                    question: "Why is it always raining here?",
                    food : 124
                }
             */

            String questionString = jsonData.getString("question");
            int food = jsonData.getInt("food");

//            this.questions.put(questionString, food); // populate your repository

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
