package kr.co.tjoeun.colosseum_kotlin.datas;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String email;
    private String nickName;

    public static User getUserFromJson(JSONObject jsonObject) {
        User u = new User();

        try {
            u.id = jsonObject.getInt("id");
            u.email = jsonObject.getString("email");
            u.nickName = jsonObject.getString("nick_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public User() {
    }

    public User(int id, String email, String nickName) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
