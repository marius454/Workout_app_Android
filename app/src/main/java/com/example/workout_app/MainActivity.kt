package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button_custom = findViewById<Button>(R.id.button)
        var button_premade = findViewById<Button>(R.id.button2)
        var button_general = findViewById<Button>(R.id.button3)
        button_custom.setOnClickListener{
            val i = Intent(this, ProgramSelection::class.java)
//            var line = inputText.text.toString()
//            i.putExtra("input", line)
            startActivity(i)
        }
    }
}
