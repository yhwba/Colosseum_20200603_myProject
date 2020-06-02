package kr.co.tjoeun.colosseum_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.tjoeun.colosseum_kotlin.utils.ContextUtil
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        title = "진행중인 토론 목록"

        Log.d("로그인토큰", ContextUtil.getUserToken(mContext))

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                Log.d("주제목록응답", json.toString())
            }

        })

    }

}
