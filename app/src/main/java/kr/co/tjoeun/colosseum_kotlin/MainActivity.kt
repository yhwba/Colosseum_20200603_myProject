package kr.co.tjoeun.colosseum_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {
        testBtn.setOnClickListener {
            testTxt.text = "버튼 눌림"
        }
    }

    override fun setValues() {

        testTxt.text = "변경 문구"

    }

}
