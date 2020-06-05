package kr.co.tjoeun.colosseum_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.colosseum_kotlin.adapters.TopicAdapter
import kr.co.tjoeun.colosseum_kotlin.datas.Topic
import kr.co.tjoeun.colosseum_kotlin.utils.ContextUtil
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val topicList = ArrayList<Topic>()
    lateinit var topicAdapter : TopicAdapter
//    val oldTopicList = ArrayList<OldTopic>()
//    lateinit var oldTopicAdapter : OldTopicAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
//        setCustomActionBar()
    }

    override fun setupEvents() {

        topicListView.setOnItemClickListener { parent, view, position, id ->
            val clickedTopic = topicList.get(position)

            val myIntent = Intent(mContext,ViewTopicActivity::class.java)
            myIntent.putExtra("topic_id",clickedTopic.id)
            startActivity(myIntent)
        }
        topicListView.setOnItemClickListener { parent, view, position, id ->
            val clickedTopic = topicList.get(position)

            val myIntent = Intent(mContext,ViewTopicActivity::class.java)
            myIntent.putExtra("topic_id",clickedTopic.id)
            startActivity(myIntent)
        }

//        oldTopic
//        oldTopicListView.setOnItemClickListener { parent, view, position, id ->
//            val clickedOldTopic = oldTopicList.get(position)
//
//            val myIntent = Intent(mContext,ViewOldTopicActivity::class.java)
//            myIntent.putExtra("topic_id",clickedOldTopic.oldId)
//            startActivity(myIntent)
//        }
//        oldTopicListView.setOnItemClickListener { parent, view, position, id ->
//            val clickedOldTopic = oldTopicList.get(position)
//
//            val myIntent = Intent(mContext,ViewOldTopicActivity::class.java)
//            myIntent.putExtra("topic_id",clickedOldTopic.oldId)
//            startActivity(myIntent)
//        }

    }

    override fun setValues() {

        topicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, topicList)
        topicListView.adapter = topicAdapter

        title = "진행중인 토론 목록"

        Log.d("로그인토큰", ContextUtil.getLoginUserToken(mContext))

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                Log.d("주제목록응답", json.toString())

                val data = json.getJSONObject("data")
                val topicArr = data.getJSONArray("topics")

                for (i in 0..topicArr.length()-1) {
                    val topicObj = topicArr.getJSONObject(i)

                    val tp = Topic.getTopicFromJson(topicObj)
                    topicList.add(tp)
                }

                runOnUiThread {
                    topicAdapter.notifyDataSetChanged()
                }


            }

        })

//        oldTopicAdapter = OldTopicAdapter(mContext, R.layout.old_topic_list_item, oldTopicList )
//        oldTopicListView.adapter = oldTopicAdapter
//
//        title = "진행중인 토론 목록"
//
//        Log.d("로그인토큰", ContextUtil.getLoginUserToken(mContext))
//
//        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
//            override fun onResponse(json: JSONObject) {
//                Log.d("주제목록응답", json.toString())
//
//                val data = json.getJSONObject("data")
//                val topicArr = data.getJSONArray("old_topics")
//
//                for (i in 0..topicArr.length()-1) {
//                    val topicObj = topicArr.getJSONObject(i)
//
//                    val tp = Topic.getTopicFromJson(topicObj)
//                    topicList.add(tp)
//                }
//
//                runOnUiThread {
//                    topicAdapter.notifyDataSetChanged()
//                }
//
//
//            }
//
//        })


    }

}
