package com.example.workout_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
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

        val dayNrText = intent.getStringExtra("dayNrText")
        val title = findViewById<TextView>(R.id.TextDay)
        title.text = dayNrText

        var i = 1
        val ex_list = arrayListOf<exercise_form>()
        val exercises = intent.getParcelableArrayListExtra<Exercise>("Exercises")
        if (exercises != arrayListOf<Exercise>()){
            for (item in exercises){
                ex_list.add(exercise_form(i))
                ex_list[i-1].setExercise(item)
                i++
            }
            i--
        }else{
            ex_list.add(exercise_form(i))
        }


        val mRecyclerView = findViewById<RecyclerView>(R.id.ex_recycler)
        val mLayoutManager = LinearLayoutManager(this)
        val mAdapter = exercise_adapter(ex_list)

        mRecyclerView.setHasFixedSize(false)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener{
            if (i < 10) {
                i++
                ex_list.add(exercise_form(i))
                mAdapter.notifyItemInserted(i-1)
            }
        }

        val buttonRemove = findViewById<Button>(R.id.buttonRemove)
        buttonRemove.setOnClickListener{
            if (ex_list != arrayListOf<exercise_form>()){
                i--
                ex_list.removeAt(i)
                mAdapter.notifyItemRemoved(i)
            }
        }

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener{
            var test_fill = 0
            for (j in 1..i){
                val name = findViewById<EditText>(j).text.toString()
                val setsText = findViewById<EditText>(j+100).text.toString()
                val repsText = findViewById<EditText>(j+200).text.toString()
                var sets = 0
                var reps = 0
                if (name == "" || setsText == "" || repsText == ""){
                    test_fill ++
                } else {
                    sets = setsText.toInt()
                    reps = repsText.toInt()
                }
                ex_list[j-1].setExercise(name, sets, reps)
            }
            if (test_fill > 0){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val exercises = arrayListOf<Exercise>()
            for (item in ex_list){
                exercises.add(item.exercise)
            }
            val resultIntent = Intent().apply{
                putExtra("Exercises", exercises)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
