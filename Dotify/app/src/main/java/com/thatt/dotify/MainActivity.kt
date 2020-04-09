package com.thatt.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var plays = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plays = Random.nextInt(1000, 1000000)
        playCount.text = getString(R.string.play_count, plays)
        var changedColor = false
        albumCover.setOnLongClickListener() {
            if (!changedColor) {
                user.setTextColor(getColor(R.color.red))
                editText.setTextColor(getColor(R.color.red))
                userButton.setTextColor(getColor(R.color.red))
                songTitle.setTextColor(getColor(R.color.red))
                artists.setTextColor(getColor(R.color.red))
                playCount.setTextColor(getColor(R.color.red))
            } else {
                user.setTextColor(getColor(R.color.black))
                editText.setTextColor(getColor(R.color.black))
                userButton.setTextColor(getColor(R.color.black))
                songTitle.setTextColor(getColor(R.color.black))
                artists.setTextColor(getColor(R.color.black))
                playCount.setTextColor(getColor(R.color.black))
            }
            changedColor = !changedColor
            return@setOnLongClickListener true
        }
    }

    fun changeUser(view: View) {
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

    fun incrementUp(view: View) {
        plays++
        playCount.text = getString(R.string.play_count, plays)
    }

    fun nextClick(view: View) {
        Toast.makeText(applicationContext, getString(R.string.next_track), Toast.LENGTH_SHORT).show()
    }

    fun prevClick(view: View) {
        Toast.makeText(applicationContext, getString(R.string.prev_track), Toast.LENGTH_SHORT).show()
    }
}
