package com.example.tictactoe_game

class Game {

    //Speles masivs (3x3 lauks tiek atspogulots viendimensiju masiva veida ar 9 elementiem
    var gameField = arrayOf(0,0,0,0,0,0,0,0,0)

    //Speli inicializejot, ta nav pabeigta
    var gameFinished = false

    //Pec default sak 1. speletajs
    var currentPlayer = 1
    var startingPlayer = 1

    //Gajiena apstrade
    fun changeField(player : Int, index : Int) {
        if (player == 1) {
            gameField[index-1] = 1
        }
        else if (player == 2) {
            gameField[index-1] = 2
        }
    }

    //Parbauda vai kads speletajs nav uzvarejis
    fun checkField(player : Int) {
        val chosen = mutableListOf<Int>()

        //Ieliek visus indeksus, kuri pieder speletajam, masiva
        for (i in 0 until 9) {
            if (gameField[i] == player) {
                chosen.add(i+1)
            }
        }

        //Parbauda visus iespejamos speles uzvaras nosacijumus
        if (chosen.containsAll(listOf(1,2,3))) gameFinished = true
        else if (chosen.containsAll(listOf(4,5,6))) gameFinished = true
        else if (chosen.containsAll(listOf(7,8,9))) gameFinished = true
        else if (chosen.containsAll(listOf(1,4,7))) gameFinished = true
        else if (chosen.containsAll(listOf(2,5,8))) gameFinished = true
        else if (chosen.containsAll(listOf(3,6,9))) gameFinished = true
        else if (chosen.containsAll(listOf(1,5,9))) gameFinished = true
        else if (chosen.containsAll(listOf(7,5,3))) gameFinished = true

    }

    //Parbauda vai ir neizskirts (vai nav neviena 0 elementa masiva jeb lauks ir pilns)
    fun checkForEnd() : Boolean{
        for (i in 0 until 9) {
            if (gameField[i] == 0)
                return false
        }
        return true
    }

}