package com.example.workout_app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ViewProgram : AppCompatActivity() {
    lateinit var day_list: ArrayList<day_form>
    lateinit var mDatabaseHelper: DatabaseHelper
    lateinit var programName: String
    var weekNr = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_program)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mDatabaseHelper = DatabaseHelper(this)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        day_list = arrayListOf<day_form>()
        var max = 0
        val program_id = intent.getIntExtra("programID", -1)
        val program = mDatabaseHelper.getProgram(program_id)
        while(program.moveToNext()){
            weekNr = program.getInt(2)
            programName = program.getString(1)
        }
        findViewById<TextView>(R.id.textView).text = programName
        val week = findViewById<TextView>(R.id.weekText)
        week.text = "week: " +  weekNr.toString()

        val list = arrayListOf<ArrayList<Any>>()
        val data = mDatabaseHelper.getProgramExercises(program_id)
        while(data.moveToNext()){
            val day_nr = data.getInt(2)
            if (day_nr > max) { max = day_nr }
            val exID = data.getInt(0)
            val exName = data.getString(3)
            val exSets = data.getInt(4)
            val exReps = data.getInt(5)
            list.add(arrayListOf<Any>(exID, day_nr, exName, exSets, exReps))
        }
        val ex_list = arrayListOf<ArrayList<Exercise>>()
        for (i in 1..max){
            ex_list.add(arrayListOf())
        }
        for (item in list){
            ex_list[item[1].toString().toInt() - 1].add(Exercise(item[0].toString().toInt(), item[2].toString(), item[3].toString().toInt(), item[4].toString().toInt()))
        }
        for (i in 1..max){
            val day = day_form(this, i)
            day.location = "view"
            day_list.add(day)
            day_list[i-1].exercises = ex_list[i-1]
        }
        setWeek(day_list)

        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val mLayoutManager = LinearLayoutManager(this)
        val mAdapter = day_adapter(day_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        val buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonNext.setOnClickListener {
            weekNr ++
            mDatabaseHelper.setProgramWeek(weekNr)
            week.text = "week: " +  weekNr.toString()
            setWeek(day_list)
        }

        val buttonPrevious = findViewById<Button>(R.id.buttonPrevious)
        buttonPrevious.setOnClickListener {
            if (weekNr > 0){
                weekNr --
                mDatabaseHelper.setProgramWeek(weekNr)
                week.text = "week: " +  weekNr.toString()
                setWeek(day_list)
            }
        }

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        buttonEdit.setOnClickListener{
            val intent = Intent(this, CreateProgram::class.java).apply {
                putExtra("programID", program_id)
                putExtra("programName", programName)
            }
            startActivity(intent)
        }

        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you wish to delete?")
            builder.setMessage("Clicking yes will permanently delete this" +
                    " program and all associated data")
            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                mDatabaseHelper.DeleteProgram(program_id)
                val intent = Intent(this, ProgramSelection::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No") { _: DialogInterface, _: Int -> }
            builder.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setWeek(days: ArrayList<day_form>){
        for (i in 0 until days.size){
            days[i].weekNr = weekNr
        }
    }
}
