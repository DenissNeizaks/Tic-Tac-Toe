package com.example.tictactoe_game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //Pirma aktivitate, paradas startejot speli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Inicialize apstiprinajuma pogu un listeneri, un piesaista tai funkciju
        val click_me = findViewById<Button>(R.id.enter)
        click_me.setOnClickListener {
            callActivity()

        }
    }

    //Funkcija atlasa ievadito vardu un parnes to uz nakamo aktivitati
    private fun callActivity() {
        val editText = findViewById<EditText>(R.id.name)
        val name  =editText.text.toString()
        val intent = Intent(this,ModeActivity::class.java).also {
            it.putExtra("name", name)
            startActivity(it)
        }
    }
}