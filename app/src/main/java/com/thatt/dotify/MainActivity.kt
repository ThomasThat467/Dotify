package com.thatt.dotify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnSongClickListener {
    private lateinit var selectedSong: Song

    companion object {
        const val SELECT_SONG = "select_song"
        const val MINI_TEXT = "mini_text"
        const val BACK_BAR = "back_bar"
        const val HIDE_MINI = "hide_mini"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allSongs = SongDataProvider.getAllSongs() as ArrayList<Song>

        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        } else {
            selectedSong = allSongs[0]
        }

        if (getNowPlayingFragment() == null && getSongListFragment() == null) {
            addListFragment(allSongs)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            enableBackButton()
        }
        shuffleButton.setOnClickListener {
            shuffleList()
        }
        miniPlayer.setOnClickListener {
            onMiniPlayerClick()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SELECT_SONG, selectedSong)
        outState.putString(MINI_TEXT, miniPlayerText.text.toString())
        outState.putBoolean(BACK_BAR, supportFragmentManager.backStackEntryCount > 0)
        outState.putBoolean(HIDE_MINI, miniPlayer.visibility == View.GONE)
        super.onSaveInstanceState(outState)
    }

    // Restore current view's state
    private fun restoreState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            val previousSong = getParcelable<Song>(SELECT_SONG)
            if (previousSong != null) {
                selectedSong = previousSong
            }
            miniPlayerText.text = getString(MINI_TEXT)
            if (getBoolean(BACK_BAR)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            if (getBoolean(HIDE_MINI)) {
                miniPlayer.visibility = View.GONE
            }
        }
    }

    // Now Playing fragment reference
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    // Song List fragment reference
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    // Adds song list fragment to fragment manager
    private fun addListFragment(allSongs: ArrayList<Song>) {
        val songListFragment = SongListFragment()
        val argumentBundle = Bundle().apply {
            putParcelableArrayList(SongListFragment.ARG_LIST, allSongs)
        }
        songListFragment.arguments = argumentBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .commit()
    }

    // Enables back button on music player view
    private fun enableBackButton() {
        val backStack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(backStack)
    }

    // Shuffles the song list
    private fun shuffleList() {
        val fragment = getSongListFragment()
        fragment?.shuffleList()
    }

    // Go to large player
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
            }
        }
    }

    // Go back to song list
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
    }

    // Changes text on the mini player
    override fun onSongClicked(song: Song) {
        miniPlayerText.visibility = View.VISIBLE
        miniPlayerText.text = getString(R.string.mini_player_text, song.title, song.artist)
        selectedSong = song
    }

    // Displays message about which song has been deleted
    override fun onSongLongClicked(title: String, songList: ArrayList<Song>) {
        val deletedToast = Toast.makeText(applicationContext, "\"$title\" Deleted", Toast.LENGTH_LONG)
        deletedToast.show()
    }

}