package com.example.workout_app

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateProgram : AppCompatActivity() {
    lateinit var day_list: ArrayList<day_form>
    lateinit var mDatabaseHelper: DatabaseHelper
    lateinit var programName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_program)
        mDatabaseHelper = DatabaseHelper(this)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        day_list = arrayListOf<day_form>()
        var max = 0
        val program_id = intent.getIntExtra("programID", -1)
        if (program_id != -1){
            programName = intent.getStringExtra("programName")
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
                day_list.add(day_form(this, i))
                day_list[i-1].exercises = ex_list[i-1]
            }
        } else {
            day_list.add(day_form(this, 1))
        }

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val nrDays = findViewById<TextView>(R.id.textView3)
        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val mLayoutManager = LinearLayoutManager(this)
        var mAdapter = day_adapter(day_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        var currentProgress = 0
        if (program_id != 0){
            currentProgress = max
        } else {
            currentProgress = 1
        }
        seekBar.max = 6
        val ex_list = arrayListOf<ArrayList<Exercise>>()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentProgress = progress + 1
                nrDays.text = currentProgress.toString()

                for (i in 0 until day_list.size){
                    if (ex_list.size < day_list.size){
                        ex_list.add(day_list[i].exercises)
                    } else{
                        ex_list[i] = day_list[i].exercises
                    }
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

        val saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.setOnClickListener {
            for (day in day_list){
                if (day.exercises == arrayListOf<Exercise>()){
                    Toast.makeText(this, day.dayNrText + " form not filled", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            nameAlert(program_id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            val exercises = data?.getParcelableArrayListExtra<Exercise>("Exercises")
            day_list[requestCode - 1].setExercises(exercises)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun save(name:String, day_list: ArrayList<day_form>){
        val insertData = mDatabaseHelper.addProgram(name, day_list)

        if (insertData){
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEdits(program_id: Int, day_list: ArrayList<day_form>){
        val saveEdit = mDatabaseHelper.EditProgram(programName, program_id, day_list)

        if (saveEdit){
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nameAlert(program_id: Int) {
        val alert = AlertDialog.Builder(this)
        val nameEditText = EditText(this)
        nameEditText.filters = arrayOf<InputFilter> (InputFilter.LengthFilter(20))
        if (program_id != -1){
            nameEditText.setText(programName)
            alert.setTitle("Edit program name")
            alert.setMessage("You can edit your program name here if you wish")
        } else{
            programName = ""
            alert.setTitle("Name your program")
            alert.setMessage("Enter the name you wish to give to this program")
        }
        alert.setView(nameEditText)
        alert.setPositiveButton("Save"){ dialog, positiveButton ->
            programName = nameEditText.text.toString()
            if (program_id != -1){
                saveEdits(program_id, day_list)
                val intent = Intent(this, ViewProgram::class.java).apply{
                    putExtra("programID", program_id)
//                    putExtra("programName", programName)
                }
                startActivity(intent)
            } else{
                save(programName, day_list)
                val intent = Intent(this, ProgramSelection::class.java)
                startActivity(intent)
            }
        }
        alert.show()
    }
}
