package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewProgram : AppCompatActivity() {
    lateinit var day_list: ArrayList<day_form>
    lateinit var mDatabaseHelper: DatabaseHelper
    lateinit var programName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_program)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mDatabaseHelper = DatabaseHelper(this)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        val program_name = intent.getStringExtra("programName")
        findViewById<TextView>(R.id.textView).text = program_name

        day_list = arrayListOf<day_form>()
        var max = 0
        val program_id = intent.getIntExtra("programID", -1)
        val list = arrayListOf<ArrayList<Any>>()
        val data = mDatabaseHelper.getProgramExercises(program_id)
        while(data.moveToNext()){
            val day_nr = data.getInt(2)
            if (day_nr > max) { max = day_nr }
            val exName = data.getString(3)
            val exSets = data.getInt(4)
            val exReps = data.getInt(4)
            list.add(arrayListOf<Any>(day_nr, exName, exSets, exReps))
        }
        val ex_list = arrayListOf<ArrayList<Exercise>>()
        for (i in 1..max){
            ex_list.add(arrayListOf())
        }
        for (item in list){
            ex_list[item[0].toString().toInt() - 1].add(Exercise(item[1].toString(), item[2].toString().toInt(), item[2].toString().toInt()))
        }
        for (i in 1..max){
            val day = day_form(this, i)
            day.location = "view"
            day_list.add(day)
            day_list[i-1].exercises = ex_list[i-1]
        }

        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val mLayoutManager = LinearLayoutManager(this)
        var mAdapter = day_adapter(day_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
