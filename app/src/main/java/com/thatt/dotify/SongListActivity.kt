package com.thatt.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*
import java.util.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"
        val allSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(allSongs)
        songList.adapter = songAdapter
        shuffleButton.setOnClickListener {
            songAdapter.shuffleSongs()
        }
    }
}
