package edu.washington.humzam.quizdroid;

import java.util.List;

/**
 * Created by humzamangrio on 5/11/15.
 */
public class InMemoryRepository implements TopicRepository {

    private List<Question> topics;

    public InMemoryRepository() {

    }

    public InMemoryRepository(String[] titles, String[] short_desc, String[] long_desc) {
        for (int i = 0; i < titles.length; i++) {
            Topic topic = new Topic();
        }
    }

    public List<Question> getTopics() {
        return topics;
    }
}
