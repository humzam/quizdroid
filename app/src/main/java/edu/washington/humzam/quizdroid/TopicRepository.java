package edu.washington.humzam.quizdroid;

import java.util.List;

/**
 * Created by humzamangrio on 5/11/15.
 */
public interface TopicRepository {

    public List<Topic> getTopics();

    public void setTopics(List<Topic> questions);
}
