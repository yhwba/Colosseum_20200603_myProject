package kr.co.tjoeun.colosseum_kotlin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.tjoeun.colosseum_kotlin.R;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicReply;


public class TopicReplyAdapter extends ArrayAdapter<TopicReply> {

//     주제에서 선택가능한 진영(side)의 id들이 담긴 배열
    int[] topicSideIds;

    Context mContext;
    List<TopicReply> mList;
    LayoutInflater inf;

    public TopicReplyAdapter(@NonNull Context context, int resource, @NonNull List<TopicReply> objects, int[] sideIdArr) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);
        topicSideIds = sideIdArr;
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

        TopicReply data = mList.get(position);

        contentTxt.setText(data.getContent());
        writerNickNameTxt.setText(data.getWriter().getNickName());


//        언제 댓글을 남겼는지 표시. => 의견에 있는 기능 활용
        createdAtTxt.setText(data.getFormattedTimeAgo());

//        data.getcre

        return row;
    }
}
