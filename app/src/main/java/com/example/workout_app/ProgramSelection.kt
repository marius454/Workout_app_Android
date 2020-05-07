package com.example.workout_app

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProgramSelection : AppCompatActivity() {
    lateinit var program_list: ArrayList<program_form>
    lateinit var mDatabaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_selection)
        mDatabaseHelper = DatabaseHelper(this)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        var button_create = findViewById<Button>(R.id.button)
        button_create.setOnClickListener{
            val i = Intent(this, CreateProgram::class.java)
            startActivity(i)
        }

        populatePrograms()
        createRecycler()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun createRecycler(){
        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val mLayoutManager = LinearLayoutManager(this)
        val mAdapter = program_adapter(program_list)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    fun populatePrograms() {
        val data = mDatabaseHelper.allPrograms
        program_list = arrayListOf()
        while(data.moveToNext()){
            val id = data.getInt(0)
            val name = data.getString(1)
            val weekNr = data.getInt(2)
            program_list.add(program_form(this, id, name, weekNr))
        }
    }
}
