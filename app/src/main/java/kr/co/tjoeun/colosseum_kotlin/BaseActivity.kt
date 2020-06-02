package kr.co.tjoeun.colosseum_kotlin

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(Html.fromHtml("<font color='#000000'>제목변경</font>"))
    }

}