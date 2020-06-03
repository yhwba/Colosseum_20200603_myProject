package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.colosseum_kotlin.adapters.TopicReplyAdapter;
import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewTopicBinding;
import kr.co.tjoeun.colosseum_kotlin.datas.Topic;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicSide;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class ViewTopicActivity extends BaseActivity {

    ActivityViewTopicBinding binding;

    Topic mTopic;
    TopicReplyAdapter mTopicReplyAdapter;
    int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {


        binding.replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( mTopic.getMySideId()==-1){
                    Toast.makeText(mContext, "진영을 선택해야 의견을 등록 가능합니다.", Toast.LENGTH_SHORT).show();
                }
//                내가 선택한 진영의 제목확인
                else{
                    String mySideTitle = null;
                    for (TopicSide ts:mTopic.getSideList()){
                        if (ts.getId() == mTopic.getMySideId()){
                            mySideTitle = ts.getTitle();
                        }
                    }
                    Intent myIntent = new Intent(mContext,EditReplyActivity.class);
                    myIntent.putExtra("topicTitle",mTopic.getTitle());
                    myIntent.putExtra("sideTitle",mySideTitle);
                    myIntent.putExtra("topicId",mTopic.getId());
                    startActivity(myIntent);
                }


            }
        });

        binding.voteToFirstSideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServerUtil.postRequestVote(mContext, mTopic.getSideList().get(0).getId(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("투표응답", json.toString());

                        try {
                            int code = json.getInt("code");
                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "참여해주셔서 감사", Toast.LENGTH_SHORT).show();
                                        getTopicFromServer();
                                    }
                                });
                            } else {
                                final String message = json.getString("message");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        binding.voteToSecondSideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServerUtil.postRequestVote(mContext, mTopic.getSideList().get(1).getId(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("투표응답", json.toString());
                        try {
                            int code = json.getInt("code");
                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "참여해주셔서 감사", Toast.LENGTH_SHORT).show();
                                        getTopicFromServer();
                                    }
                                });
                            } else {
                                final String message = json.getString("message");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void setValues() {

        topicId = getIntent().getIntExtra("topic_id", -1);

        if (topicId == -1) {

            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getTopicFromServer();
    }

    void getTopicFromServer() {
        ServerUtil.getRequestTopicById(mContext, topicId, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("토픽상세정보", json.toString());

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

    void setTopicValuesToUi() {
        binding.topicTitleTxt.setText(mTopic.getTitle());
        Glide.with(mContext).load(mTopic.getImageUrl()).into(binding.topicImg);

        binding.firstSideTitleTxt.setText(mTopic.getSideList().get(0).getTitle());
        binding.secondSideTitleTxt.setText(mTopic.getSideList().get(1).getTitle());

        binding.firstSideVoteCountTxt.setText(String.format("%,d표", mTopic.getSideList().get(0).getVoteCount()));
        binding.secondSideVoteCountTxt.setText(String.format("%,d표", mTopic.getSideList().get(1).getVoteCount()));

        TopicSide[] topicSides = new TopicSide[2];
        for (int i =0; i <topicSides.length; i++){
            topicSides[i]= mTopic.getSideList().get(i);
        }

        mTopicReplyAdapter = new TopicReplyAdapter(mContext, R.layout.topic_reply_list_item, mTopic.getReplyList(),topicSides);
        binding.replyListView.setAdapter(mTopicReplyAdapter);

        int mySideIndex =-1;
        for (int i = 0 ; i < mTopic.getSideList().size(); i++){

            if (mTopic.getSideList().get(i).getId() == mTopic.getMySideId()){
                mySideIndex = i;
            }

        }
        if (mySideIndex ==-1) {
            binding.voteToFirstSideBtn.setEnabled(true);
            binding.voteToSecondSideBtn.setEnabled(true);
        }
        else if(mySideIndex == 0){
            binding.voteToFirstSideBtn.setEnabled(false);
            binding.voteToSecondSideBtn.setEnabled(true);
        }
        else {
            binding.voteToFirstSideBtn.setEnabled(true);
            binding.voteToSecondSideBtn.setEnabled(false);
        }



    }
}
