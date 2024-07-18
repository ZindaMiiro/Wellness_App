package com.example.wellness_app

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.example.wellness_app.databinding.ActivityMediaPlaybackBinding

class MediaPlaybackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlaybackBinding
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlaybackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaUrl = intent.getStringExtra("VIDEO_URL")
        val title = intent.getStringExtra("TITLE")

        setupActionBar(title)
        setupExoPlayer(mediaUrl)
    }

    private fun setupActionBar(title: String?) {
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupExoPlayer(mediaUrl: String?) {
        if (mediaUrl != null && mediaUrl.isNotEmpty()) {
            try {
                exoPlayer = ExoPlayer.Builder(this).build().also { player ->
                    binding.playerView.player = player
                    val mediaItem = MediaItem.fromUri(Uri.parse(mediaUrl))
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error initializing ExoPlayer", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Media URL is null or empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
