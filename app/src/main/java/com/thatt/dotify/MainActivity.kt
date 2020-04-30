package com.thatt.dotify

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnSongClickListener {
    private lateinit var selectedSong: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allSongs: ArrayList<Song> = SongDataProvider.getAllSongs() as ArrayList<Song>

//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                miniPlayerText.text = getString(MINI_TEXT)
//            }
//        }

        val nowPlayingFragment = NowPlayingFragment()
        val argumentBundle = Bundle().apply {
            putParcelable(NowPlayingFragment.SELECTED_SONG, allSongs[0])
        }
        nowPlayingFragment.arguments = argumentBundle

        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) == null) {
            val songListFragment = SongListFragment()
            shuffleButton.setOnClickListener {
                shuffleSongs(songListFragment)
            }
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_LIST, allSongs)
            }
            songListFragment.arguments = argumentBundle
            supportFragmentManager.beginTransaction().add(R.id.fragContainer, songListFragment).commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val backStack = supportFragmentManager.backStackEntryCount > 0

            if (backStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

        miniPlayer.setOnClickListener {
            onMiniPlayerClick()
        }
    }

//    companion object {
//        const val MINI_TEXT = "mini_text"
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString(MINI_TEXT, miniPlayerText.text.toString())
//        super.onSaveInstanceState(outState)
//    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayerClick() {
        if (miniPlayerText.text.isNotEmpty()) {
            var nowPlayingFragment = getNowPlayingFragment()

            if (nowPlayingFragment == null) {
                miniPlayer.visibility = View.GONE
                nowPlayingFragment = NowPlayingFragment.getInstance(selectedSong)
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragment.updateSong(selectedSong)
            }
        }
    }

    private fun shuffleSongs(songListFragment: SongListFragment) {
        songListFragment.shuffleSongs()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
        return super.onSupportNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        miniPlayerText.visibility = View.VISIBLE
        miniPlayerText.text = getString(R.string.mini_player_text, song.title, song.artist)
        selectedSong = song
    }

    override fun onSongLongClicked(title: String) {
        val deletedToast = Toast.makeText(applicationContext, "\"$title\" Deleted", Toast.LENGTH_LONG)
        deletedToast.show()
    }

}