package com.example.workout_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateProgram : AppCompatActivity() {
    lateinit var day_list: ArrayList<day_form>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_program)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        day_list = arrayListOf<day_form>()
        day_list.add(day_form(this, 1))

        var seekBar = findViewById<SeekBar>(R.id.seekBar)
        var nrDays = findViewById<TextView>(R.id.textView3)
        var mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var mLayoutManager = LinearLayoutManager(this)
        var mAdapter = day_adapter(day_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        var currentProgress = 1
        seekBar.max = 6
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentProgress = progress + 1
                nrDays.text = currentProgress.toString()

                var ex_list = arrayListOf<ArrayList<Exercise>>()
                for (i in 0..(day_list.size - 1)){
                    ex_list.add(day_list[i].exercises)
                }

                day_list.clear()
                for (i in 1..(progress + 1)){
                    day_list.add(day_form(this@CreateProgram, i))
                    if ((i-1) < ex_list.size){
                        day_list[i-1].exercises = ex_list[i-1]
                    }
                }
                mAdapter = day_adapter(day_list)
                mRecyclerView.adapter = mAdapter
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            var exercises = data?.getParcelableArrayListExtra<Exercise>("Exercises")
            day_list[requestCode - 1].setExercises(exercises)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
