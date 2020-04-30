package com.thatt.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {
    private var song: Song? = null
    private var plays = 0

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SELECTED_SONG = "selected_song"
        private const val PLAY_COUNT = "play_count"

        fun getInstance(song: Song) = NowPlayingFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SELECTED_SONG, song)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                plays = getInt(PLAY_COUNT)
            }
        } else {
            plays = Random.nextInt(1000, 1000000)
        }

//        playButton.setOnClickListener {
//            incrementUp()
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAY_COUNT, plays)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            args.getParcelable<Song>(SELECTED_SONG)?.let {
                song = it
            }
        }

        song?.let { updateSong(it) }
    }

    fun updateSong(song: Song) {
        albumCover.setImageResource(song.largeImageID)
        songTitle.text = song.title
        artists.text = song.artist
    }

    private fun incrementUp() {
        plays++
        playCount.text = getString(R.string.play_count, plays)
    }
}