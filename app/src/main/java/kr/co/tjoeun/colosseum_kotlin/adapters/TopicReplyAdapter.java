package kr.co.tjoeun.colosseum_kotlin.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kr.co.tjoeun.colosseum_kotlin.R;
import kr.co.tjoeun.colosseum_kotlin.ViewReplyActivity;
import kr.co.tjoeun.colosseum_kotlin.datas.Topic;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicReply;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicSide;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;


public class TopicReplyAdapter extends ArrayAdapter<TopicReply> {

//     주제에서 선택가능한 진영(side)의 id들이 담긴 배열
    TopicSide[] topicSideArr;

    Context mContext;
    List<TopicReply> mList;
    LayoutInflater inf;

    public TopicReplyAdapter(@NonNull Context context, int resource, @NonNull List<TopicReply> objects, TopicSide[] sideArr) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);
        topicSideArr = sideArr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = inf.inflate(R.layout.topic_reply_list_item, null);
        }

        TextView contentTxt = row.findViewById(R.id.contentTxt);
        TextView writerNickNameTxt = row.findViewById(R.id.writerNickNameTxt);
        TextView sideTxt = row.findViewById(R.id.sideTxt);
        TextView createdAtTxt = row.findViewById(R.id.createdAtTxt);

//        좋아요 / 실헝요 관련 뷰 추기ㅏ
        Button replyCountBtn = row.findViewById(R.id.replyCountBtn);
        final Button likeCountBtn = row.findViewById(R.id.likeCountBtn);
        Button dislikeCountBtn = row.findViewById(R.id.dislikeCountBtn);

        final TopicReply data = mList.get(position);

        contentTxt.setText(data.getContent());
        writerNickNameTxt.setText(data.getWriter().getNickName());

        int sideIndex = 0;
        for (int i = 0; i< topicSideArr.length; i++){
            if (topicSideArr[i].getId() == data.getSide_id()) {
                sideIndex = i;

            }
        }

        if(sideIndex ==0){
            sideTxt.setBackgroundResource(R.drawable.red_border_box);
            sideTxt.setTextColor(Color.RED);
        }
        else {
            sideTxt.setBackgroundResource(R.drawable.blue_border_box);
            sideTxt.setTextColor(Color.BLUE);
        }

        sideTxt.setText(topicSideArr[sideIndex].getTitle());


//        언제 댓글을 남겼는지 표시. => 의견에 있는 기능 활용
        createdAtTxt.setText(data.getFormattedTimeAgo());

//        좋아요 / 싫어요 갯수 표시
        likeCountBtn.setText(String.format("좋아요 %,d", data.getLikeCount()));
        dislikeCountBtn.setText(String.format("싫어요 %,d", data.getDislikeCount()));


//        대댓글 갯수 표시
        replyCountBtn.setText(String.format("답글 %,d",data.getReplyCount()));




//        좋아요
        if (data.isMyLike()){
            likeCountBtn.setBackgroundResource(R.drawable.red_border_box);
            likeCountBtn.setTextColor(Color.RED);
        }
        else{
            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box);
            likeCountBtn.setTextColor(mContext.getResources().getColor(R.color.gray));
        }
//        싫어요
        if (data.isMyDislike()){
            dislikeCountBtn.setBackgroundResource(R.drawable.blue_border_box);
            dislikeCountBtn.setTextColor(Color.BLUE);
        }
        else{
            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box);
            dislikeCountBtn.setTextColor(mContext.getResources().getColor(R.color.gray));
        }

//        좋아요 버튼 누른 처리
        likeCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerUtil.postRequestReplyLike(mContext, data.getId(), true, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("좋아요 누름",json.toString());

                        try {
//                            String message = json.getString("message");
                            JSONObject dataObj = json.getJSONObject("data");
                            JSONObject reply = dataObj.getJSONObject("reply");

                            data.setLikeCount(reply.getInt("like_count"));
                            data.setMyLike(reply.getBoolean("my_like"));
                            data.setDislikeCount(reply.getInt("dislike_count"));
                            data.setMyDislike(reply.getBoolean("my_dislike"));

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        dislikeCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerUtil.postRequestReplyLike(mContext, data.getId(), false, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("싫어요 누름",json.toString());
                        try {
//                            String message = json.getString("message");
                            JSONObject dataObj = json.getJSONObject("data");
                            JSONObject reply = dataObj.getJSONObject("reply");

                            data.setLikeCount(reply.getInt("like_count"));
                            data.setMyLike(reply.getBoolean("my_like"));
                            data.setDislikeCount(reply.getInt("dislike_count"));
                            data.setMyDislike(reply.getBoolean("my_dislike"));

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        replyCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ViewReplyActivity.class);
                myIntent.putExtra("replyId",data.getId());
                mContext.startActivity(myIntent);
            }
        });
        return row;
    }
}
