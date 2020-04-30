package com.thatt.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songList: List<Song>
    private lateinit var songListAdapter: SongListAdapter
    private var onSongSelectedListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val ARG_LIST = "arg_list"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let { args ->
            val listOfSongs = args.getParcelableArrayList<Song>(ARG_LIST)
            if (listOfSongs != null) {
                songList = listOfSongs
            }
        }

        if (context is OnSongClickListener) {
            onSongSelectedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(songList)
        allSongsList.adapter = songListAdapter

        songListAdapter.onSongClickListener = { song ->
            onSongSelectedListener?.onSongClicked(song)
        }
        songListAdapter.onSongLongClickListener = { title: String ->
            onSongSelectedListener?.onSongLongClicked(title)
        }
    }

    fun shuffleList() {
        songListAdapter.shuffleSongs()
    }

}