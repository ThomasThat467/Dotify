package com.thatt.dotify

import androidx.recyclerview.widget.DiffUtil
import com.thatt.dotify.model.Song

class SongDiffCallback (private val oldSongs: List<Song>, private val newSongs: List<Song>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSongs.size

    override fun getNewListSize(): Int = newSongs.size

    override fun areItemsTheSame(oldSongPosition: Int, newSongPosition: Int): Boolean {
        val oldPerson = oldSongs[oldSongPosition]
        val newPerson = newSongs[newSongPosition]
        return oldPerson.id == newPerson.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldSongs[oldItemPosition]
        val newPerson = oldSongs[newItemPosition]
        return oldPerson.title == newPerson.title
    }
}