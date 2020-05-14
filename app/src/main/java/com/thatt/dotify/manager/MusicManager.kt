package com.thatt.dotify.manager

import com.thatt.dotify.model.Song

class MusicManager {
    private var currSong: Song? = null

    fun changeSong(song: Song) {
        currSong = song
    }

    fun getCurrentSong(): Song? {
        return currSong
    }
}