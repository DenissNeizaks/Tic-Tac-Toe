package com.example.tictactoe_game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class BoardActivity : AppCompatActivity() {
    //Speles aktivitate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Iznem gamemode un vardu
        val mode = intent.getStringExtra("choice")
        val name = intent.getStringExtra("name")

        //Izveido jaunu speles objektu
        val game = Game()

        //Izveido mainigos visam pogam, kas atbilst katrai grid pozicijai
        val btn1 = findViewById<Button>(R.id.b1)
        btn1.setOnClickListener {
            processTurn(btn1, game, 1, mode, name)
        }
        val btn2 = findViewById<Button>(R.id.b2)
        btn2.setOnClickListener {
            processTurn(btn2, game, 2, mode, name)
        }
        val btn3 = findViewById<Button>(R.id.b3)
        btn3.setOnClickListener {
            processTurn(btn3, game, 3, mode, name)
        }
        val btn4 = findViewById<Button>(R.id.b4)
        btn4.setOnClickListener {
            processTurn(btn4, game, 4, mode, name)
        }
        val btn5 = findViewById<Button>(R.id.b5)
        btn5.setOnClickListener {
            processTurn(btn5, game, 5, mode, name)
        }
        val btn6 = findViewById<Button>(R.id.b6)
        btn6.setOnClickListener {
            processTurn(btn6, game, 6, mode, name)
        }
        val btn7 = findViewById<Button>(R.id.b7)
        btn7.setOnClickListener {
            processTurn(btn7, game, 7, mode, name)
        }
        val btn8 = findViewById<Button>(R.id.b8)
        btn8.setOnClickListener {
            processTurn(btn8, game, 8, mode, name)
        }
        val btn9 = findViewById<Button>(R.id.b9)
        btn9.setOnClickListener {
            processTurn(btn9, game, 9, mode, name)
        }

        //Poga uzsakt jaunu speli

        val new_game = findViewById<Button>(R.id.new_game)
        new_game.setOnClickListener {
            newGame(game, mode)
        }

        //Poga atgriezties uz pirmo aktivitati
        val main_menu = findViewById<Button>(R.id.mainMenu)
        main_menu.setOnClickListener {
            startOver()

        }

    }

    //Pariet uz pirmo aktivitati
    private fun startOver() {
        val intent = Intent(this,MainActivity::class.java).also {
            startActivity(it)
        }
    }

    //Datora gajiena izveles funkcija, izvelas viena nejausu vertibu no iespejamiem brivajiem laukiem
    fun chooseCell(gameArray : Array<Int>) : Int {
        val choices = mutableListOf<Int>()
        //Itere cauri masivam un atrod visos tuksos laukus
        for (i in 0 until 9) {
            if (gameArray[i] == 0) {
                choices.add(i)
            }
        }
        //Izvelas nejausu skaitli izveidota masiva robezas
        val randomNumber = Random.nextInt(0, choices.size)
        //Panem nejausu skaitli no masiva
        val choice = choices[randomNumber]
        //Speles objekta masiva element pie izveleta indekas klust 2 jeb pieder datoram
        gameArray[choice] = 2
        return choice+1
    }

    private fun processTurn(btn: Button, game:Game, choice : Int, mode:String?, name:String?) {

        //Cilveka izveletas pogas id
        val btn_id = btn.id

        //Parbauda vai laucins ir tukss
        if (btn.text == "") {

            //Parbauda kurs speletajs tagad iet (aktuals PvP mode) un maina pogu tekstu attiecigi
            val textView = findViewById<Button>(btn_id).apply {
                if (game.currentPlayer == 1) {
                    text = "X"
                }
                else if (game.currentPlayer == 2) {
                    text = "O"
                }
            }

            //Izmaina speles objekta masivu un ari parbauda vai spele ir beigta
            if (game.currentPlayer == 1) {
                game.changeField(1,choice)
                game.checkField(1)
            }
            else if (game.currentPlayer == 2) {
                game.changeField(2,choice)
                game.checkField(2)
            }

            //Ja spele ir pabeigta un 1 speletajs veica pedejo gajienu
            if (game.gameFinished && game.currentPlayer == 1) {
                val gameOver = findViewById<TextView>(R.id.choice).apply {
                    text = "$name wins!"
                }

                //Izsledz grid pogas
                disableButtons()

                //New game poga klust redzama
                val new_game = findViewById<Button>(R.id.new_game).apply {
                    visibility = View.VISIBLE
                }
                return

            }

            //Tas pats tikai otrajam speletajam (PvP)
            else if (game.gameFinished && game.currentPlayer == 2) {
                val gameOver = findViewById<TextView>(R.id.choice).apply {
                    text = "$name loses!"
                }
                disableButtons()
                val new_game = findViewById<Button>(R.id.new_game).apply {
                    visibility = View.VISIBLE
                }
                return
            }

            //Parbauda vai nav neiskirts pec paveikta gajiena
            if (game.checkForEnd()) {
                val gameOver = findViewById<TextView>(R.id.choice).apply {
                    text = "Draw!"
                }
                disableButtons()
                val new_game = findViewById<Button>(R.id.new_game).apply {
                    visibility = View.VISIBLE
                }
                return
            }

            //Gajiens tiek dots otrajam speletajam (PvP)
            if (mode == "PvP" && game.currentPlayer == 1) {
                game.currentPlayer = 2
            }
            else if (mode == "PvP" && game.currentPlayer == 2){
                game.currentPlayer = 1
            }

            //Pec cilveka gajiena dators veic gajienu (PvC)
            if (mode == "PvC") {
                computerTurn(game, name)
            }
        }

    }

    private fun computerTurn(game:Game, name:String? = "No name") {

        //Izvelas vienu iespejamo gajienu
        val computer_choice = chooseCell(game.gameField)

        //Izveido ID no izveleta cipara (b burts + cipars)
        val cc_identifier = "b$computer_choice"

        //Atrod elementu ar attiecigo ID (lai uzzinatu par attiecigo funkciju, ChatGPT devu sekojoso promptu:
        //i don't have the id available directly, i have a function that returns an integer,
        // and the id of the button the letter "b" plus that returned integer, and im trying to figure out
        // how to implement this
        val computerButtonId = resources.getIdentifier(cc_identifier, "id", packageName)

        //Attiecigai grid pogai ieliek O
        val buttonView = findViewById<Button>(computerButtonId).apply {
            text = "O"
        }

        //Parbauda vai dators nav uzvarejis (dators tiek uzskatits ka speletajs 2)
        game.checkField(2)
        if (game.gameFinished) {
            val gameOver = findViewById<TextView>(R.id.choice).apply {
                text = "$name loses!"
            }
            disableButtons()
            val new_game = findViewById<Button>(R.id.new_game).apply {
                visibility = View.VISIBLE
            }
            return
        }

        //Parbauda neizskirts
        if (game.checkForEnd()) {
            val gameOver = findViewById<TextView>(R.id.choice).apply {
                text = "Draw!"
            }
            disableButtons()
            val new_game = findViewById<Button>(R.id.new_game).apply {
                visibility = View.VISIBLE
            }
            return
        }
    }

    //Funkcija laukuma pogu izslegsanai speles beigas
    private fun disableButtons() {
        val btn1 = findViewById<Button>(R.id.b1)

        val btn2 = findViewById<Button>(R.id.b2)

        val btn3 = findViewById<Button>(R.id.b3)

        val btn4 = findViewById<Button>(R.id.b4)

        val btn5 = findViewById<Button>(R.id.b5)

        val btn6 = findViewById<Button>(R.id.b6)

        val btn7 = findViewById<Button>(R.id.b7)

        val btn8 = findViewById<Button>(R.id.b8)

        val btn9 = findViewById<Button>(R.id.b9)
        btn1.isEnabled = false
        btn2.isEnabled = false
        btn3.isEnabled = false
        btn4.isEnabled = false
        btn5.isEnabled = false
        btn6.isEnabled = false
        btn7.isEnabled = false
        btn8.isEnabled = false
        btn9.isEnabled = false

    }

    //Funkcija jaunajai spelei, mainamie parametri tiek atgriezti pie sakotnejam vertibam
    private fun newGame(game : Game, mode:String?) {
        game.gameField = arrayOf(0,0,0,0,0,0,0,0,0)
        game.gameFinished = false
        val btn1 = findViewById<Button>(R.id.b1)

        val btn2 = findViewById<Button>(R.id.b2)

        val btn3 = findViewById<Button>(R.id.b3)

        val btn4 = findViewById<Button>(R.id.b4)

        val btn5 = findViewById<Button>(R.id.b5)

        val btn6 = findViewById<Button>(R.id.b6)

        val btn7 = findViewById<Button>(R.id.b7)

        val btn8 = findViewById<Button>(R.id.b8)

        val btn9 = findViewById<Button>(R.id.b9)
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        btn5.text = ""
        btn6.text = ""
        btn7.text = ""
        btn8.text = ""
        btn9.text = ""
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
        btn5.isEnabled = true
        btn6.isEnabled = true
        btn7.isEnabled = true
        btn8.isEnabled = true
        btn9.isEnabled = true

        //Partaisa new game pogu neredzamu
        val new_game = findViewById<Button>(R.id.new_game)
        new_game.visibility=View.INVISIBLE

        //Speles nosleguma teksts tiek izdzests
        val gameOverText = findViewById<TextView>(R.id.choice)
        gameOverText.text=""

        //Ja iepriekseja spele saka pirmais speletajs, tagad saks otrais
        if (mode == "PvP" && game.startingPlayer == 1) {
            game.currentPlayer = 2
            game.startingPlayer = 2
        }

        //Tas pats tikai preteji
        else if (mode == "PvP" && game.startingPlayer == 2) {
            game.currentPlayer = 1
            game.startingPlayer = 1
        }

        //Ja ir PvC un cilveks ieprieks saka, dators automatiski veic 1 gajienu, un tiek saglabats,
        //ka soreiz dators saka
        if (mode == "PvC" && game.startingPlayer == 1) {
            computerTurn(game)
            game.startingPlayer = 2
        }

        //Ja dators ieprieks saka, tad soreiz cilveks saks
        else if (mode == "PvC" && game.startingPlayer == 2) {
            game.startingPlayer = 1
        }


    }
}