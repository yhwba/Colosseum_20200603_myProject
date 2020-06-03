package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityEditReplyBinding;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class EditReplyActivity extends BaseActivity {
    String topicTitle;
    String mySideTitle;
    int topicId;

    ActivityEditReplyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_reply);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.contentEdt.getText().toString();
                ServerUtil.postRequestReply(mContext, topicId, input, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("댓글달기 응답",json.toString());

                        try {
                            int code = json.getInt("code");

                            if (code == 200){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "의견등록 완료", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                            else{
                                final String message= json.getString("message");
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
        topicTitle = getIntent().getStringExtra("topicTitle");

        binding.topicTitleTxt.setText(topicTitle);

        mySideTitle = getIntent().getStringExtra("sideTitle");
        binding.sideTitleTxt.setText(mySideTitle);

        topicId =getIntent().getIntExtra("topicId",-1);
    }
}
