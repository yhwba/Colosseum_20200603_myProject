package kr.co.tjoeun.colosseum_kotlin.datas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {

    private int id;
    private String title;
    private String imageUrl;
    private List<TopicSide> sideList = new ArrayList<>();
    private List<TopicReply> replyList = new ArrayList<>();
    private int mySideId;


    public static Topic getTopicFromJson(JSONObject jsonObject) {
        Topic topic = new Topic();

        try {
            topic.id = jsonObject.getInt("id");
            topic.title = jsonObject.getString("title");
            topic.imageUrl = jsonObject.getString("img_url");
            topic.mySideId = jsonObject.getInt("my_side_id");

//            같이 따라오는 진영들을 목록에 추가
            JSONArray sides = jsonObject.getJSONArray("sides");
            for (int i = 0; i < sides.length(); i++) {
                JSONObject side = sides.getJSONObject(i);
                TopicSide topicSide = TopicSide.getTopicSideFromJson(side);
                topic.sideList.add(topicSide);
            }

//            따라오는 의견 목록을 추가 파싱=> 있을때도 없을 때도 있기 때문에
            if (!jsonObject.isNull("replies")) {
                JSONArray replies = jsonObject.getJSONArray("replies");
                for (int i = 0; i < replies.length(); i++) {
                    JSONObject reply = replies.getJSONObject(i);
                    TopicReply tr = TopicReply.getTopicReplyFromJson(reply);
                    topic.replyList.add(tr);
                }
            }

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

    public List<TopicSide> getSideList() {
        return sideList;
    }

    public List<TopicReply> getReplyList() {
        return replyList;
    }

    public int getMySideId() {
        return mySideId;
    }

    public void setMySideId(int mySideId) {
        this.mySideId = mySideId;
    }

    public int getMySideIndex() {
        int mySideIndex =-1;
        for (int i = 0 ; i < this.sideList.size(); i++){

            if (this.sideList.get(i).getId() ==this.mySideId){
                mySideIndex = i;
            }

        }
        return  mySideIndex;
    }

}
