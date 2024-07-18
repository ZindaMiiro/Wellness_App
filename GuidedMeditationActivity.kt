package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellness_app.databinding.ActivityGuidedMeditationBinding

class GuidedMeditationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuidedMeditationBinding
    private lateinit var guidedMeditationAdapter: GuidedMeditationAdapter
    private val meditations: MutableList<GuidedMeditation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuidedMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        loadGuidedMeditationSessions()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Guided Meditation"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        guidedMeditationAdapter = GuidedMeditationAdapter(meditations) { meditation ->
            playMeditation(meditation)
        }
        binding.recyclerViewMeditations.apply {
            layoutManager = LinearLayoutManager(this@GuidedMeditationActivity)
            adapter = guidedMeditationAdapter
        }
    }

    private fun loadGuidedMeditationSessions() {
        val initialMeditations = listOf(
            GuidedMeditation(1, "Morning Meditation", "Start your day with a calm mind.", generateCorrectUrl("https://youtu.be/jb9B39zrzEo"), 1000000),
            GuidedMeditation(2, "Relaxation", "Relax and unwind after a long day.", generateCorrectUrl("https://example.com/relaxation.mp3"), 900000),
            GuidedMeditation(3, "Sleep Meditation", "Prepare for a restful sleep.", generateCorrectUrl("https://example.com/sleep.mp3"), 1200000)
        )

        meditations.addAll(initialMeditations)
        guidedMeditationAdapter.notifyDataSetChanged()
    }

    private fun generateCorrectUrl(url: String): String {
        return if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
    }

    private fun playMeditation(meditation: GuidedMeditation) {
        val intent = Intent(this, MediaPlaybackActivity::class.java).apply {
            putExtra("VIDEO_URL", meditation.videoUrl)
            putExtra("TITLE", meditation.title)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
