package com.thatt.dotify.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thatt.dotify.model.Song
import com.thatt.dotify.DotifyApp
import com.thatt.dotify.OnSongClickListener
import com.thatt.dotify.R
import com.thatt.dotify.fragment.NowPlayingFragment
import com.thatt.dotify.fragment.SongListFragment
import com.thatt.dotify.manager.ApiManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnSongClickListener {

    companion object {
        const val MINI_TEXT = "mini_text"
        const val BACK_BAR = "back_bar"
        const val HIDE_MINI = "hide_mini"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiManager = (application as DotifyApp).apiManager
        val allSongs: List<Song> = ArrayList()


        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        } else {
            fetchSongData(apiManager)
        }

        if (getNowPlayingFragment() == null && getSongListFragment() == null) {
            addListFragment(allSongs as ArrayList<Song>)
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
        outState.putString(MINI_TEXT, miniPlayerText.text.toString())
        outState.putBoolean(BACK_BAR, supportFragmentManager.backStackEntryCount > 0)
        outState.putBoolean(HIDE_MINI, miniPlayer.visibility == View.GONE)
        super.onSaveInstanceState(outState)
    }

    // Restore current view's state
    private fun restoreState(savedInstanceState: Bundle) {
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

    // Fetches the list of songs
    private fun fetchSongData(apiManager: ApiManager) {
        apiManager.fetchSongs(
            {changeList(it.songs)},
            {Toast.makeText(this, "Could not find songs", Toast.LENGTH_LONG).show()}
        )
    }

    // Now Playing fragment reference
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(
        NowPlayingFragment.TAG) as? NowPlayingFragment

    // Song List fragment reference
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    // Music Manager reference
    private fun getMusicManager() = (application as DotifyApp).musicManager

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

    // Changes the current list to new one
    private fun changeList(newList: List<Song>) {
        val fragment = getSongListFragment()
        fragment?.changeList(newList)
    }

    // Go to large player
    private fun onMiniPlayerClick() {
        if (getMusicManager().getCurrentSong() != null) {
            var nowPlayingFragment = getNowPlayingFragment()

            if (nowPlayingFragment == null) {
                miniPlayer.visibility = View.GONE
                nowPlayingFragment = NowPlayingFragment.getInstance(getMusicManager().getCurrentSong())
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
        (application as DotifyApp).musicManager.changeSong(song)
        miniPlayerText.text = getString(R.string.mini_player_text, song.title, song.artist)
    }

    // Displays message about which song has been deleted
    override fun onSongLongClicked(title: String, songList: ArrayList<Song>) {
        val deletedToast = Toast.makeText(applicationContext, "\"$title\" Deleted", Toast.LENGTH_LONG)
        deletedToast.show()
    }

}