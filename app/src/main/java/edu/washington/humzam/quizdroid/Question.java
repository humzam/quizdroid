package edu.washington.humzam.quizdroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by humzamangrio on 4/28/15.
 */
public class Question implements Parcelable {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;

    public Question() {

    }

    public Question(String question, String option1, String option2, String option3, String option4, int answer ) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public void setQuestion(String question) { this.question = question; }

    public void setOption1(String text) { this.option1 = text; }

    public void setOption2(String text) { this.option2 = text; }

    public void setOption3(String text) { this.option3 = text; }

    public void setOption4(String text) { this.option4 = text; }

    public void setAnswer(int correct) { this.answer = correct;
    }
    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswer() {
        return answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "question is + " + getQuestion();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(answer);
    }
}
