package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class ViewDay : AppCompatActivity() {
    var exNr = 0
    var setNr = 0
    var check = -1
    var sum_sets = 0
    var weekNr = 0

    lateinit var ex_list: ArrayList<exercise_form>
    lateinit var mDatabaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_day)
        mDatabaseHelper = DatabaseHelper(this)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        val dayNrText = intent.getStringExtra("dayNrText")
        val title = findViewById<TextView>(R.id.TextDay)
        title.text = dayNrText

        var i = 1
        ex_list = arrayListOf()
        val exercises = intent.getParcelableArrayListExtra<Exercise>("Exercises")
        weekNr = intent.getIntExtra("weekNr", -1)

        for (item in exercises) {
            ex_list.add(exercise_form(i))
            ex_list[i - 1].setExercise(item)
            i++
        }

        val top1 = findViewById<TextView>(R.id.textTop1)
        val top2 = findViewById<TextView>(R.id.textTop2)
        val main = findViewById<TextView>(R.id.textMain)
        val bottom1 = findViewById<TextView>(R.id.textBottom1)
        val bottom2 = findViewById<TextView>(R.id.textBottom2)

        val text_list = arrayListOf<TextView>(top1, top2, main, bottom1, bottom2)

        exNr=1
        setNr=1

        for (item in ex_list){
            sum_sets = sum_sets + item.exercise.sets
        }

        val editWeight = findViewById<EditText>(R.id.EditWeight)
        val editReps = findViewById<EditText>(R.id.EditReps)

        setWeightReps(editWeight, editReps)
        setText(text_list, ex_list)

        val buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonNext.setOnClickListener {

            if (editWeight.text.isNotEmpty() && editReps.text.isNotEmpty()){
                var test =

                mDatabaseHelper.addToJournal(ex_list[exNr-1].exercise.id, weekNr,
                    setNr, editWeight.text.toString().toFloat(), editReps.text.toString().toInt())
            }
            if (check + 2 < sum_sets) {
                if (setNr == ex_list[exNr-1].exercise.sets){
                    exNr++
                    setNr = 1
                } else {
                    setNr++
                }
                check++
            }
            setWeightReps(editWeight, editReps)
            setText(text_list, ex_list)
        }

        val buttonPrevious = findViewById<Button>(R.id.buttonPrevious)
        buttonPrevious.setOnClickListener {

            if (exNr > 1 || setNr > 1){
                if (setNr == 1){
                    exNr--
                    setNr = ex_list[exNr-1].exercise.sets
                }
                else {
                    setNr--
                }
            }
            if (check > -1){
                check--
            }
            setWeightReps(editWeight, editReps)
            setText(text_list, ex_list)
        }
    }

    private fun setWeightReps(editWeight: EditText, editReps: EditText){
        var currentWeightReps = mDatabaseHelper.getJournalEntry(ex_list[exNr-1].exercise.id, weekNr, setNr)
        if (currentWeightReps != arrayListOf<Float>()){
            editWeight.setText(currentWeightReps[0].toString())
            editReps.setText(currentWeightReps[1].toString())
        } else {
            editWeight.setText("")
            editReps.setText("")
        }

        val nrWeightLast = findViewById<TextView>(R.id.nrWeightLast)
        val nrRepsLast = findViewById<TextView>(R.id.nrRepsLast)

        var lastWeightReps = mDatabaseHelper.getJournalEntry(ex_list[exNr-1].exercise.id, weekNr - 1, setNr)
        if (lastWeightReps != arrayListOf<Float>()){
            nrWeightLast.text = lastWeightReps[0].toString()
            nrRepsLast.text = lastWeightReps[1].toString()
        } else {
            nrRepsLast.text = "0"
            nrWeightLast.text = "0"
        }
    }

    private fun setText(text_list: ArrayList<TextView>, ex_list: ArrayList<exercise_form>){
        var check = check
        var i = exNr
        var j = setNr
        for (k in 1..2){
            if (j <= 1){
                i--
                if (i-1 >= 0){
                    j = ex_list[i-1].exercise.sets
                } else {
                    j--
                }
            } else {
                j--
            }
        }
        for (item in text_list){
            if (check <= 0){
                item.text = ""
                i++
                j++
                check++
            } else if (check > sum_sets) {
                item.text = ""
            } else {
                item.text = i.toString() + "." + j.toString() + " " + ex_list[i-1].exercise.name + " x" + ex_list[i-1].exercise.reps.toString()
                check++
                if (j == ex_list[i-1].exercise.sets){
                    i++
                    j = 1
                } else {
                    j++
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
