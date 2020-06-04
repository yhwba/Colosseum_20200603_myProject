package kr.co.tjoeun.colosseum_kotlin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
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

    //    의견 수정시에는 값이 -1 이 아니게 변경할 예정
//     -1이면 새로 등록하는 경우.
    int replyId = -1;

    ActivityEditReplyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_reply);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = binding.contentEdt.getText().toString();

                if (replyId == -1) {
                    ServerUtil.postRequestReply(mContext, topicId, input, new ServerUtil.JsonResponseHandler() {
                        @Override
                        public void onResponse(JSONObject json) {
                            Log.d("댓글달기 응답", json.toString());

                            try {
                                int code = json.getInt("code");

                                if (code == 200) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "의견등록 완료", Toast.LENGTH_SHORT).show();
                                            finish();
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
                } else {
//                    댓글을 수정해야하는 경우

                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("의견 수정");
                    alert.setMessage("정말 의견을 수정 하시겠습니까? 의견을 수정해도 이전 내용은 계속 보관됩니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ServerUtil.putRequestReply(mContext, replyId, input, new ServerUtil.JsonResponseHandler() {
                                @Override
                                public void onResponse(JSONObject json) {
                                    Log.d("수정", json.toString());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            코드가 200인걸 확인하고 의견 수정 완료된거를 찍는것이 더 좋음.
                                            Toast.makeText(mContext, "의견 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });

                                }
                            });
                        }
                    });
                    alert.setNegativeButton("취소", null);
                    alert.show();


                }

            }
        });
    }

    @Override
    public void setValues() {
        topicTitle = getIntent().getStringExtra("topicTitle");

        binding.topicTitleTxt.setText(topicTitle);

        mySideTitle = getIntent().getStringExtra("sideTitle");
        binding.sideTitleTxt.setText(mySideTitle);

        topicId = getIntent().getIntExtra("topicId", -1);

//        어느 댓글을 수정할지 id를 받아서 replyId 변수에 저장
        replyId = getIntent().getIntExtra("replyId", -1);

        if (replyId != -1) {
            setTitle("의견 수정하기");
            binding.postBtn.setText("의견 수정하기");

            binding.contentEdt.setText(getIntent().getStringExtra("content"));

        } else {
            setTitle("의견 등록하기");
        }

    }
}
