package com.thatt.dotify

import com.thatt.dotify.model.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
    fun onSongLongClicked(title: String, songList: ArrayList<Song>)
}