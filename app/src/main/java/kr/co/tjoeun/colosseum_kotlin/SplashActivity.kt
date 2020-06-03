package kr.co.tjoeun.colosseum_kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash.*
import kr.co.tjoeun.colosseum_kotlin.utils.ContextUtil

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

        Handler().postDelayed({

            if(ContextUtil.isAutoLogin(mContext) && ContextUtil.getLoginUserToken(mContext) !=""){
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

}
