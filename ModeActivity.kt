package com.example.tictactoe_game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ModeActivity : AppCompatActivity() {
    //Otra aktivitate, kur jaizvelas gamemode (PvP or PvC)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Iznem parnesto vardu
        val name = intent.getStringExtra("name")

        //Custom greeting
        val textView = findViewById<TextView>(R.id.welcome).apply {
            text = "Welcome, " + name + "!"
        }
        //PvC poga
        val button_pvc = findViewById<Button>(R.id.PvC)
        button_pvc.setOnClickListener {
            callActivity_PvC(name)
        }

        //PvP poga
        val button_pvp = findViewById<Button>(R.id.PvP)
        button_pvp.setOnClickListener {
            callActivity_PvP(name)
        }

    }

    //Funkcija parnes talak uz nakamo aktivitati vardu un izveleto gamemode
    private fun callActivity_PvC(name:String?) {
        val choice = "PvC"
        val intent = Intent(this,BoardActivity::class.java).also {
            it.putExtra("choice", choice)
            it.putExtra("name", name)
            startActivity(it)
        }
    }

    //Tas pats, tikai ar PvP
    private fun callActivity_PvP(name:String?) {
        val choice = "PvP"
        val intent = Intent(this,BoardActivity::class.java).also {
            it.putExtra("choice", choice)
            it.putExtra("name", name)
            startActivity(it)
        }
    }
}