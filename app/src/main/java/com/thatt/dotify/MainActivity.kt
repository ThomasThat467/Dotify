package com.thatt.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var plays = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        albumCover.setImageResource(song.largeImageID)
        songTitle.text = song.title
        artists.text = song.artist
        plays = Random.nextInt(1000, 1000000)
        playCount.text = getString(R.string.play_count, plays)
        var changedColor = false
        userButton.setOnClickListener {
            changeUser()
        }
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

    companion object {
        const val SONG_KEY = "SELECTED_SONG"
    }

    private fun changeUser() {
        if (userButton.text == getString(R.string.change_button)) {
            user.visibility = View.GONE
            userButton.text = getString(R.string.apply_button)
            editText.visibility = View.VISIBLE
            editText.setText(user.text)
        } else if (editText.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.username_error), Toast.LENGTH_SHORT).show()
        } else {
            user.visibility = View.VISIBLE
            userButton.text = getString(R.string.change_button)
            editText.visibility = View.GONE
            user.text = editText.text
        }
    }

    private fun changeColor(changedColor: Boolean) {
        if (!changedColor) {
            val colorRed = getColor(R.color.red)
            user.setTextColor(colorRed)
            editText.setTextColor(colorRed)
            userButton.setTextColor(colorRed)
            songTitle.setTextColor(colorRed)
            artists.setTextColor(colorRed)
            playCount.setTextColor(colorRed)
        } else {
            val colorBlack = getColor(R.color.black)
            user.setTextColor(colorBlack)
            editText.setTextColor(colorBlack)
            userButton.setTextColor(colorBlack)
            songTitle.setTextColor(colorBlack)
            artists.setTextColor(colorBlack)
            playCount.setTextColor(colorBlack)
        }
    }

    private fun incrementUp() {
        plays++
        playCount.text = getString(R.string.play_count, plays)
    }

    private fun skipTrack(track: String) {
        Toast.makeText(applicationContext, getString(R.string.skip_track, track), Toast.LENGTH_SHORT).show()
    }
}
