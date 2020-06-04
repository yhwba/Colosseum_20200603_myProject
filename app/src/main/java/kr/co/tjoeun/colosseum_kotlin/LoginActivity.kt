package kr.co.tjoeun.colosseum_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import kr.co.tjoeun.colosseum_kotlin.datas.GlobalData
import kr.co.tjoeun.colosseum_kotlin.datas.User
import kr.co.tjoeun.colosseum_kotlin.utils.ContextUtil
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        autoLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            ContextUtil.setAutoLogin(mContext, isChecked)
        }



        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val pw = pwEdt.text.toString()

            ServerUtil.postRequestLogin(mContext, email, pw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    Log.d("로그인응답", json.toString())


                    val data = json.getJSONObject("data")
                    val token = data.getString("token")

                    ContextUtil.setLoginUserToken(mContext, token)

//                    수동로그인을 할때도 글로벌클래스로 유저 넘겨 받기..
                    val user = data.getJSONObject("user")
                    val loginUser = User.getUserFromJson(user)
                    GlobalData.loginUser = loginUser

                    val myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)

                    finish()


                }

            })

        }

    }

    override fun setValues() {

    }

}
