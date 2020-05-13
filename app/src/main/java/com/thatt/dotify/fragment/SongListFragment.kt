package com.thatt.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thatt.dotify.model.Song
import com.thatt.dotify.OnSongClickListener
import com.thatt.dotify.R
import com.thatt.dotify.SongListAdapter
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songList: List<Song>
    private lateinit var songListAdapter: SongListAdapter
    private var onSongSelectedListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val ARG_LIST = "arg_list"
        const val SAVED_LIST = "saved_list"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let { args ->
            args.getParcelableArrayList<Song>(ARG_LIST)?.let {
                songList = it
            }
        }

        if (context is OnSongClickListener) {
            onSongSelectedListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(SAVED_LIST) as List<Song>
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            updateList()
            onSongSelectedListener?.onSongLongClicked(title, getList())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(SAVED_LIST, songList as ArrayList)
        super.onSaveInstanceState(outState)
    }

    // Shuffles the song list
    fun shuffleList() {
        songListAdapter.shuffleSongs()
        updateList()
    }

    fun changeList(newList: List<Song>) {
        songListAdapter.updateChanges(newList)
        updateList()
    }

    // Gets current list
    private fun getList(): ArrayList<Song> {
        return songListAdapter.listOfSongsCopy as ArrayList<Song>
    }

    // Updates list in fragment to current list in adapter
    private fun updateList() {
        songList = songListAdapter.listOfSongsCopy
    }

}