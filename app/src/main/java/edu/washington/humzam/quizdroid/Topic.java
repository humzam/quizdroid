package edu.washington.humzam.quizdroid;

import java.util.List;

/**
 * Created by humzamangrio on 5/11/15.
 */
public class Topic {

    private String title;
    private String short_desc;
    private String long_desc;
    private List<Question> questions;

    public Topic() {

    }

    public Topic(String title, String short_desc, String long_desc, List<Question> questions) {
        this.title = title;
        this.short_desc = short_desc;
        this.long_desc = long_desc;
        this.questions = questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
