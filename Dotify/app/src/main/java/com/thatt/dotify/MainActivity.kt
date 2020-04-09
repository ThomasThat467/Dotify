package com.thatt.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var plays = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plays = Random.nextInt(1000, 1000000)
        playCount.text = "$plays plays"
    }

    fun changeUser(view: View) {
        if (userButton.text == "CHANGE USER") {
            user.visibility = View.GONE
            userButton.text = "APPLY"
            editText.visibility = View.VISIBLE
            editText.setText(user.text)
        } else {
            user.visibility = View.VISIBLE
            userButton.text = "CHANGE USER"
            editText.visibility = View.GONE
            user.text = editText.text
        }
    }

    fun incrementUp(view: View) {
        plays++
        playCount.text = "$plays plays"
    }

    fun nextClick(view: View) {
        Toast.makeText(applicationContext,"Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    fun prevClick(view: View) {
        Toast.makeText(applicationContext,"Skipping to previous track", Toast.LENGTH_SHORT).show()
    }
}
