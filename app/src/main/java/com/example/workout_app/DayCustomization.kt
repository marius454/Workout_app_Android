package com.example.workout_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

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
        var exercises = intent.getParcelableArrayListExtra<Exercise>("Exercises")
        if (exercises != arrayListOf<Exercise>()){
            for (item in exercises){
                ex_list.add(exercise_form(i))
                ex_list[i-1].setExercise(item)
                i++
            }
        }else{
            ex_list.add(exercise_form(i))
        }


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

        var buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener{
            var test_fill = 0
            var test_range = 0
            for (j in 1..i){
                var name = findViewById<EditText>(j).text.toString()
                var sets = findViewById<EditText>(j+100).text.toString().toInt()
                var reps = findViewById<EditText>(j+200).text.toString().toInt()
                if (name == null || sets == null || reps == null){
                    test_fill ++
                }
                if(sets > 999 || sets < 0 || reps > 999 || reps < 0){
                    test_range ++
                }
                ex_list[j-1].setExercise(name, sets, reps)
            }
            if (test_fill > 0){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
            }
            if (test_range > 0){
                Toast.makeText(this, "Sets or Reps not in acceptable range [0;999]", Toast.LENGTH_SHORT)
            }
            var exercises = arrayListOf<Exercise>()
            for (item in ex_list){
                exercises.add(item.exercise)
            }
            var resultIntent = Intent().apply{
                putExtra("Exercises", exercises)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
