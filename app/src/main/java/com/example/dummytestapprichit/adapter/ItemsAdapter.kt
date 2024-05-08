package com.example.dummytestapprichit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.dummytestapprichit.R
import com.example.dummytestapprichit.beans.ArticleItem
import com.example.dummytestapprichit.beans.Item
import com.example.dummytestapprichit.beans.ItemNew
import com.example.dummytestapprichit.beans.UserItem
import com.example.dummytestapprichit.utils.GenericAdapter
import com.example.dummytestapprichit.utils.Utils

class ItemsAdapter(context: Context) : GenericAdapter<ArticleItem>(context) {
    var mContext: Context

    init {
        mContext = context
    }

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.row_items, viewGroup, false)
    }

    override fun bindView(item: ArticleItem, viewHolder: ViewHolder) {
        val tv_name = viewHolder.getView(R.id.tv_name) as TextView
        val tv_price = viewHolder.getView(R.id.tv_price) as TextView
        val row_item = viewHolder.getView(R.id.row_item) as LinearLayout


        tv_name.text = item.title
        tv_price.text = item.description
        val str = "Id = ${item.id} Title = ${item.title} Description = ${item.description}"

        row_item.setOnClickListener {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
        }
    }
}
