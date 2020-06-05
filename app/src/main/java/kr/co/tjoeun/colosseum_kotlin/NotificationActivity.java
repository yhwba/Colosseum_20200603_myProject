package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        notificationImg.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setValues() {

    }

}
