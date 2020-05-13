package com.thatt.dotify.manager

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.thatt.dotify.fragment.SongListFragment.Companion.TAG
import com.thatt.dotify.model.Song
import com.thatt.dotify.model.SongList

class ApiManager {
    var songList: SongList? = null

    fun fetchSongs(context: Context, initSongList: (SongList) -> Unit, onError: (() -> Unit)? = null) {
        val queue = Volley.newRequestQueue(context)
        val songListURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

        val request = StringRequest(
            Request.Method.GET, songListURL,
            { response ->
                val gson = Gson()
                val allSongs = gson.fromJson(response, SongList::class.java)

                songList = allSongs
                initSongList(allSongs)
            },
            {
                Log.i(TAG, "Error")
                onError?.invoke()
            }
        )

        queue.add(request)
    }
}