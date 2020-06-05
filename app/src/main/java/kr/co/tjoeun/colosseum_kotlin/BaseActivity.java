package kr.co.tjoeun.colosseum_kotlin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext = this;

    public abstract void setupEvents();
    public abstract void setValues();

    public TextView activityTitleTxt;
    public ImageView notificationImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomActionBar();

    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        activityTitleTxt.setText(title);

    }

    public void setCustomActionBar(){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            getSupportActionBar().setBackgroundDrawable(null);

            Toolbar toolbar  = (Toolbar) getSupportActionBar().getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0,0);

            View customActionView = getSupportActionBar().getCustomView();

            activityTitleTxt = customActionView.findViewById(R.id.activityTitleTxt);
            notificationImg = customActionView.findViewById(R.id.notificationImg);

            notificationImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(mContext, NotificationActivity.class);
                    startActivity(myIntent);
                }
            });
//          기본화면 이름을 Collosseum으로 셋팅 (기본값으로 각각 메뉴에서 타이틀 정해주면 그것으로 생김)
            setTitle("Collosseum");


        }

    }

}
