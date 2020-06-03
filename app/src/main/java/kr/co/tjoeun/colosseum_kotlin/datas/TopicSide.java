package kr.co.tjoeun.colosseum_kotlin.datas;

import org.json.JSONException;
import org.json.JSONObject;

public class TopicSide {

    private int id ;
    private int topicId;
    private String title;

    public  static  TopicSide getTopicSideFromJson(JSONObject json){
        TopicSide ts = new TopicSide();

        try {
            ts.id = json.getInt("id");
            ts.topicId =json.getInt("topic_id");
            ts.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  ts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
