package kr.co.tjoeun.colosseum_kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_splash.*
import kr.co.tjoeun.colosseum_kotlin.datas.GlobalData
import kr.co.tjoeun.colosseum_kotlin.datas.User
import kr.co.tjoeun.colosseum_kotlin.utils.ContextUtil
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {

        getMyInfoFromServer()

        Handler().postDelayed({

            if(GlobalData.loginUser != null){
                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
            }
            else{
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }
            finish()

        }, 2000)

    }
//    내 정보 데이터를 서버에 요청하는 데이터
    fun getMyInfoFromServer(){
//        자동로그인이 된 경우
        if (ContextUtil.isAutoLogin(mContext) && ContextUtil.getLoginUserToken(mContext) !=""){
            ServerUtil.getRequestUserInfo(mContext, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject?) {
                    Log.d("내 정보",json.toString())

                    val data = json!!.getJSONObject("data")
                    val user = data.getJSONObject("user")

                    val loginUser = User.getUserFromJson(user)

                    GlobalData.loginUser = loginUser


                }


            })
        }

    }

}
