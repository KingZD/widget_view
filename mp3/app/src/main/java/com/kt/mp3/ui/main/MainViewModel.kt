package com.kt.mp3.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var playerFlag = false

    fun startPlayer(flag: Boolean) {
        playerFlag = flag
    }

    fun isPlayer(): Boolean {
        return playerFlag
    }
}