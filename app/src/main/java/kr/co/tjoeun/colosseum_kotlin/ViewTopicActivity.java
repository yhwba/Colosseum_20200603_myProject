package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.colosseum_kotlin.adapters.TopicReplyAdapter;
import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewTopicBinding;
import kr.co.tjoeun.colosseum_kotlin.datas.Topic;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class ViewTopicActivity extends BaseActivity {
    ActivityViewTopicBinding binding;

    Topic mTopic;
    TopicReplyAdapter mTopicReplyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_topic);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {


        
        int topicId = getIntent().getIntExtra("topic_id",-1);
        
        if(topicId ==-1){

            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        ServerUtil.getRequestTopicById(mContext, topicId, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("토픽상세정보",json.toString());

                try {
                    JSONObject data = json.getJSONObject("data");
                    JSONObject topic = data.getJSONObject("topic");

                    mTopic = Topic.getTopicFromJson(topic);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTopicValuesToUi();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void setTopicValuesToUi(){
        binding.topicTitleTxt.setText(mTopic.getTitle());
        Glide.with(mContext).load(mTopic.getImageUrl()).into(binding.topicImg);

        binding.firstSideTitleTxt.setText(mTopic.getSideList().get(0).getTitle());
        binding.secondSideTitleTxt.setText(mTopic.getSideList().get(1).getTitle());

        binding.firstSideVoteCountTxt.setText(String.format("%,d표",mTopic.getSideList().get(0).getVoteCount()));
        binding.secondSideVoteCountTxt.setText(String.format("%,d표",mTopic.getSideList().get(1).getVoteCount()));

        mTopicReplyAdapter = new TopicReplyAdapter(mContext,R.layout.topic_reply_list_item, mTopic.getReplyList());
        binding.replyListView.setAdapter(mTopicReplyAdapter);







    }
}