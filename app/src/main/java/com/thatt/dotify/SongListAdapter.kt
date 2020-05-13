package com.thatt.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thatt.dotify.model.Song

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    var listOfSongsCopy = listOfSongs.toMutableList() // Made copy so the original is not changed
    var onSongClickListener: ((song: Song) -> Unit)? = null
    var onSongLongClickListener: ((title: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongsCopy.size

    // Shuffles the songs
    fun shuffleSongs() {
        val newList = listOfSongsCopy.toMutableList()
        newList.shuffle()
        updateChanges(listOfSongsCopy, newList)
        listOfSongsCopy = newList
    }

    fun updateChanges(newList: List<Song>) {
        updateChanges(listOfSongsCopy, newList)
    }

    // Animates changes to list
    fun updateChanges(oldList: List<Song>, newList: List<Song>) {
        val callback = SongDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
    }

    // Displays items at specified positions
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongsCopy[position]
        holder.bind(song)
    }

    // Creates view for each item
    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songAlbum = itemView.findViewById<ImageView>(R.id.albumImage)
        private val songName = itemView.findViewById<TextView>(R.id.songTitle)
        private val songArtist = itemView.findViewById<TextView>(R.id.artist)

        fun bind(song: Song) {
            //songAlbum.setImageResource(song.smallImageID)
            songName.text = song.title
            songArtist.text = song.artist

            // Finds song that is clicked
            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }

            // Removes song that is long clicked
            itemView.setOnLongClickListener {
                val newList = listOfSongsCopy.toMutableList()
                newList.remove(song)
                updateChanges(listOfSongsCopy, newList)
                listOfSongsCopy = newList
                onSongLongClickListener?.invoke(song.title)
                return@setOnLongClickListener true
            }
        }
    }
}