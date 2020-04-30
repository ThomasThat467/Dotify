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

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                miniPlayerText.text = getString(MINI_TEXT)
                if (getBoolean(BACK_BAR)) {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                if (getBoolean(HIDE_MINI)) {
                    miniPlayer.visibility = View.GONE
                }
            }
        }
        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) == null) {
            val songListFragment = SongListFragment()
            shuffleButton.setOnClickListener {
                songListFragment.shuffleList()
            }
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_LIST, allSongs)
            }
            songListFragment.arguments = argumentBundle
            supportFragmentManager.beginTransaction().add(R.id.fragContainer, songListFragment)
                .commit()
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

    companion object {
        const val MINI_TEXT = "mini_text"
        const val BACK_BAR = "back_bar"
        const val HIDE_MINI = "hide_mini"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MINI_TEXT, miniPlayerText.text.toString())
        outState.putBoolean(BACK_BAR, supportFragmentManager.backStackEntryCount > 0)
        outState.putBoolean(HIDE_MINI, miniPlayer.visibility == View.GONE)
        super.onSaveInstanceState(outState)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayerClick() {
        if (miniPlayerText.text.isNotEmpty()) {
            var nowPlayingFragment = getNowPlayingFragment()

            if (nowPlayingFragment == null) {
                miniPlayer.visibility = View.GONE
                nowPlayingFragment = NowPlayingFragment.getInstance(selectedSong)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragContainer, nowPlayingFragment)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragment.updateSong(selectedSong)
            }
        }
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

    private fun skipTrack(track: String) {
        Toast.makeText(applicationContext, getString(R.string.skip_track, track), Toast.LENGTH_SHORT).show()
    }

}