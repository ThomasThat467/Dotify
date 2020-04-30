package com.thatt.dotify

import com.ericchee.songdataprovider.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
    fun onSongLongClicked(title: String, songList: ArrayList<Song>)
}