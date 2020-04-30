package com.thatt.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingActivity : AppCompatActivity() {
    private var plays = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_now_playing)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                plays = getInt(PLAY_COUNT)
            }
        } else {
            plays = Random.nextInt(1000, 1000000)
        }

        playCount.text = getString(R.string.play_count, plays)

        // Display song information
        val song: Song? = intent.getParcelableExtra(SONG_KEY)
        if (song != null) {
            albumCover.setImageResource(song.largeImageID)
            songTitle.text = song.title
            artists.text = song.artist
        }

        // Set initial text color
        var changedColor = false

        // Display back button on action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        albumCover.setOnLongClickListener() {
            changeColor(changedColor)
            changedColor = !changedColor
            return@setOnLongClickListener true
        }
        playButton.setOnClickListener {
            incrementUp()
        }
        nextButton.setOnClickListener {
            skipTrack("next")
        }
        prevButton.setOnClickListener {
            skipTrack("previous")
        }
    }

    // Keys for Extras
    companion object {
        const val SONG_KEY = "SELECTED_SONG"

        private const val PLAY_COUNT = "play_count"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAY_COUNT, plays)
        super.onSaveInstanceState(outState)
    }

    // Ends activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.finish()
        return super.onOptionsItemSelected(item)
    }


    // Changes color of text
    private fun changeColor(changedColor: Boolean) {
        if (!changedColor) {
            val colorRed = getColor(R.color.red)
            songTitle.setTextColor(colorRed)
            artists.setTextColor(colorRed)
            playCount.setTextColor(colorRed)
        } else {
            val colorBlack = getColor(R.color.black)
            songTitle.setTextColor(colorBlack)
            artists.setTextColor(colorBlack)
            playCount.setTextColor(colorBlack)
        }
    }

    // Increase play count
    private fun incrementUp() {
        plays++
        playCount.text = getString(R.string.play_count, plays)
    }

    // Skips to the next or previous song
    private fun skipTrack(track: String) {
        Toast.makeText(applicationContext, getString(R.string.skip_track, track), Toast.LENGTH_SHORT).show()
    }
}
