package kr.co.tjoeun.colosseum_kotlin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.tjoeun.colosseum_kotlin.R;
import kr.co.tjoeun.colosseum_kotlin.datas.Notification;


public class NotificationAdapter extends ArrayAdapter<Notification> {


    Context mContext;
    List<Notification> mList;
    LayoutInflater inf;

    public NotificationAdapter(@NonNull Context context, int resource, @NonNull List<Notification> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = inf.inflate(R.layout.notification_list_item, null);
        }

        TextView notiTitleTxt = row.findViewById(R.id.notiTitleTxt);

        Notification data = mList.get(position);


        notiTitleTxt.setText(data.getTitle());
        return row;
    }
}
