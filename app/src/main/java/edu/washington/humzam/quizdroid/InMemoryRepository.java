package edu.washington.humzam.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by humzamangrio on 5/11/15.
 */
public class InMemoryRepository implements TopicRepository {

    private List<Topic> topics = new ArrayList<Topic>();

    public InMemoryRepository() {

    }

    public InMemoryRepository(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
