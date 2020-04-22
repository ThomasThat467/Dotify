package com.thatt.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import java.util.*

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    fun shuffleSongs() {
        Collections.shuffle(listOfSongs)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songs = listOfSongs[position]
        holder.bind(songs.smallImageID, songs.title, songs.artist)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songAlbum = itemView.findViewById<ImageView>(R.id.albumImage)
        private val songName = itemView.findViewById<TextView>(R.id.songTitle)
        private val songArtist = itemView.findViewById<TextView>(R.id.artist)

        fun bind(album: Int, name: String, artist: String) {
            songAlbum.setImageResource(album)
            songName.text = name
            songArtist.text = artist
        }
    }
}