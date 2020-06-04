package kr.co.tjoeun.colosseum_kotlin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewReplyBinding;

public class ViewReplyActivity extends BaseActivity {

    ActivityViewReplyBinding binding;

    int replyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_reply);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        replyId = getIntent().getIntExtra("replyId", -1);

        if (replyId != -1) {
//            서버에서 의견의 상세정보를 불러오자
            getReplyDataFromServer();
        }

    }

    void getReplyDataFromServer() {


    }
}
