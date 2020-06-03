package kr.co.tjoeun.colosseum_kotlin.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Topic implements Serializable {

    private int id;
    private String title;
    private String imageUrl;

    public static Topic getTopicFromJson(JSONObject jsonObject) {
        Topic topic = new Topic();

        try {
            topic.id = jsonObject.getInt("id");
            topic.title = jsonObject.getString("title");
            topic.imageUrl = jsonObject.getString("img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return topic;
    }

    public Topic() {
    }

    public Topic(int id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
