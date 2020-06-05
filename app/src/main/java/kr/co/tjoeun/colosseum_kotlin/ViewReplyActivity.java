package kr.co.tjoeun.colosseum_kotlin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.colosseum_kotlin.adapters.TopicReplyAdapter;
import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewReplyBinding;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicReply;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class ViewReplyActivity extends BaseActivity {

    ActivityViewReplyBinding binding;

    int replyId;
    TopicReply mReplyData;

    TopicReplyAdapter tra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_reply);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        binding.replyPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.replyContentEdt.getText().toString();
                ServerUtil.postRequestReReply(mContext, replyId, input, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("대댓글응답",json.toString());
                    }
                });
            }
        });

    }

    @Override
    public void setValues() {
//         => 대댓글 목록을 뿌릴때 필요한 진영 정보를 어떻게 주느냐?
//        tra =  new TopicReplyAdapter(mContext, R.layout.topic_reply_list_item,mReplyData.getReplyList(), );

        replyId = getIntent().getIntExtra("replyId", -1);

        if (replyId != -1) {
//            서버에서 의견의 상세정보를 불러오자
            getReplyDataFromServer();
        }

    }

    void getReplyDataFromServer() {

        ServerUtil.getRequestTopicReplyById(mContext, replyId, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("의견상세", json.toString());

                try {
                    JSONObject data = json.getJSONObject("data");
                    JSONObject reply = data.getJSONObject("reply");

                    mReplyData = TopicReply.getTopicReplyFromJson(reply);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUiByReplyData();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    void setUiByReplyData(){
        binding.writerNickNameTxt.setText(mReplyData.getWriter().getNickName());
        binding.contentTxt.setText(mReplyData.getContent());
        binding.sideTitleTxt.setText(mReplyData.getSelectedSide().getTitle());
    }
}
