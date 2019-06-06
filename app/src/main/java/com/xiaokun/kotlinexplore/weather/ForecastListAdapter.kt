package com.xiaokun.kotlinexplore.weather

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by xiaokun on 2019/6/6.
 * @date 2019/6/6
 * @author xiaokun
 */
class ForecastListAdapter(val items: List<String>) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}