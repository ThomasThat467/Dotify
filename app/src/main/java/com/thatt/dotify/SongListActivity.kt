package com.thatt.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.fragment_song_list.*


class SongListActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_song_list)
//
//        // Set up view on launch
//        title = getString(R.string.song_list_title)
//        miniPlayerText.visibility = View.INVISIBLE
//
//        // Set up RecycleView adapter
//        val allSongs: List<Song> = SongDataProvider.getAllSongs()
//        val songAdapter = SongListAdapter(allSongs)
//        songList.adapter = songAdapter
//
//        // Set default song on select
//        var selectedSong: Song = allSongs[1]
//
//        shuffleButton.setOnClickListener {
//            songAdapter.shuffleSongs()
//        }
//        songAdapter.onSongClickListener = { song: Song ->
//            changePlayer(song)
//            selectedSong = song
//        }
//        songAdapter.onSongLongClickListener = {title: String ->
//            deleteMessage(title)
//        }
//        miniPlayer.setOnClickListener{
//            openMainPlayer(selectedSong)
//        }
//    }
//
//    // Changes text on the mini player
//    private fun changePlayer(song: Song) {
//        miniPlayerText.visibility = View.VISIBLE
//        miniPlayerText.text = getString(R.string.mini_player_text, song.title, song.artist)
//    }
//
//    // Displays message about which song has been deleted
//    private fun deleteMessage(title: String) {
//        val deletedToast = Toast.makeText(applicationContext, "\"$title\" Deleted", Toast.LENGTH_LONG)
//        deletedToast.show()
//    }
//
//    // Changes screen to main music player
//    private fun openMainPlayer(song: Song) {
//        if (miniPlayerText.visibility != View.INVISIBLE) {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("SELECTED_SONG", song)
//            startActivity(intent)
//        }
//    }
}
