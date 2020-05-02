package com.example.workout_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateProgram : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_program)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        var day_list = arrayListOf<day_form>()
        day_list.add(day_form(this, 1))

        var seekBar = findViewById<SeekBar>(R.id.seekBar)
        var nrDays = findViewById<TextView>(R.id.textView3)
        var mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var mLayoutManager = LinearLayoutManager(this)
        var mAdapter = day_adapter(day_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        seekBar.max = 6
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var currentProgress = progress + 1
                nrDays.text = currentProgress.toString()

                day_list.clear()
                for (i in 1..(progress + 1)){
                    day_list.add(day_form(this@CreateProgram, i))
                }
                mAdapter = day_adapter(day_list)
                mRecyclerView.layoutManager = mLayoutManager
                mRecyclerView.adapter = mAdapter
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
