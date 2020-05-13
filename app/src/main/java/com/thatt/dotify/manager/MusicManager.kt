package com.thatt.dotify.manager

import com.thatt.dotify.model.Song
import kotlin.random.Random

class MusicManager {
    private var currSong: Song? = null
    private var playCount: Int = 0
    private var nowPlaying: Boolean = false

    fun changeSong(song: Song) {
        currSong = song
    }

    fun getCurrentSong(): Song? {
        return currSong
    }

    fun initCount() {
        playCount = Random.nextInt(1000, 1000000)
    }

    fun play() {
        playCount++
    }

    fun isPlaying(): Boolean {
        return nowPlaying
    }
}