package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DayCustomization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_customization)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        var dayNrText = intent.getStringExtra("dayNrText")
        var title = findViewById<TextView>(R.id.TextDay)
        title.text = dayNrText

        var i = 1
        var ex_list = arrayListOf<exercise_form>()
        ex_list.add(exercise_form(i))

        var mRecyclerView = findViewById<RecyclerView>(R.id.ex_recycler)
        var mLayoutManager = LinearLayoutManager(this)
        var mAdapter = exercise_adapter(ex_list)

        mRecyclerView.setHasFixedSize(false)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        var buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener{
            if (i < 10) {
                i++
                ex_list.add(exercise_form(i))
                mAdapter.notifyItemInserted(i-1)
            }
        }

        var buttonRemove = findViewById<Button>(R.id.buttonRemove)
        buttonRemove.setOnClickListener{
            if (ex_list != arrayListOf<exercise_form>()){
                i--
                ex_list.removeAt(i)
                mAdapter.notifyItemRemoved(i)
            }
        }
    }
}
