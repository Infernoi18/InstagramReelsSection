package com.example.instagramreelssection
import androidx.media3.common.util.UnstableApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
class VideoAdapter(
    private val videos: List<Int>
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private val players = mutableListOf<ExoPlayer?>()

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerView: PlayerView = view.findViewById(R.id.playerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        players.add(null)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val context = holder.itemView.context

        val player = ExoPlayer.Builder(context).build()
        players[position] = player

        holder.playerView.player = player

        val uri = "android.resource://${context.packageName}/${videos[position]}"
        val mediaItem = MediaItem.fromUri(uri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.repeatMode = Player.REPEAT_MODE_ONE

        holder.itemView.setOnClickListener {
            if (player.isPlaying) player.pause() else player.play()
        }
    }

    override fun getItemCount() = videos.size

    fun playVideo(position: Int) {
        players.forEachIndexed { index, player ->
            if (index == position) player?.play()
            else player?.pause()
        }
    }

    fun releaseAll() {
        players.forEach { it?.release() }
    }
}