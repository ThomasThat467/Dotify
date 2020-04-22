package com.thatt.dotify

import android.content.Intent
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
        miniPlayerText.visibility = View.INVISIBLE
        val allSongs: List<Song> = SongDataProvider.getAllSongs()
        var selectedSong: Song = allSongs[1]
        val songAdapter = SongListAdapter(allSongs)
        songList.adapter = songAdapter
        shuffleButton.setOnClickListener {
            songAdapter.shuffleSongs()
            songAdapter.notifyDataSetChanged()
        }
        songAdapter.onSongClickListener = { selected: Song ->
            miniPlayerText.visibility = View.VISIBLE
            selectedSong = selected
            miniPlayerText.text = selected.title + " - " + selected.artist
        }
        miniPlayer.setOnClickListener{
            if (miniPlayerText.visibility != View.INVISIBLE) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("SELECTED_SONG", selectedSong)
                startActivity(intent)
            }
        }
    }
}
