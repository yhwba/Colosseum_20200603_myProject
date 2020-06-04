package kr.co.tjoeun.colosseum_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.colosseum_kotlin.R
import kr.co.tjoeun.colosseum_kotlin.datas.Topic

class OldTopicAdapter(context:Context, resId:Int, list:List<Topic>) : ArrayAdapter<Topic>(context, resId, list) {

    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {
//            null이 아닐때 실행.
        }.let {
//            null 일때 실행
            tempRow = inf.inflate(R.layout.old_topic_list_item, null)
        }

        val row = tempRow!!

        val oldTopicImg = row.findViewById<ImageView>(R.id.oldTopicImg)
        val oldTitleTxt = row.findViewById<TextView>(R.id.oldTopicImg)

        val data = mList[position]

        Glide.with(mContext).load(data.imageUrl).into(oldTopicImg)
        oldTitleTxt.text = data.title


        return row

    }

}