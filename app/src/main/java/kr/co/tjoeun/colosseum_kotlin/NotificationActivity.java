package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.colosseum_kotlin.datas.Notification;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class NotificationActivity extends BaseActivity {

    List<Notification> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {



    }

    @Override
    public void setValues() {

        notificationImg.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotiFromServer();
    }

    void getNotiFromServer(){

        ServerUtil.getRequestNotifications(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("알림목록 응답", json.toString());

                try {
                    JSONObject data = json.getJSONObject("data");
                    JSONArray notis = data.getJSONArray("notifications");

                    notificationList.clear();

                    for ( int i =0; i < notis.length(); i++){
                        JSONObject notiObj = notis.getJSONObject(i);
                        notificationList.add(Notification.getNotiFromJson(notiObj));

                    }
//                    notifyDataSetChange가 필요



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
