package com.xiaokun.kotlinexplore.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.xiaokun.kotlinexplore.R
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * Created by xiaokun on 2019/6/6.
 * @date 2019/6/6
 * @author xiaokun
 */
class WeatherActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        forecast_list.layoutManager = LinearLayoutManager(this)

    }
}