package com.example.wellness_app


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoodTrackingActivity : AppCompatActivity() {

    private lateinit var spinnerMood: Spinner
    private lateinit var etDiaryEntry: EditText
    private lateinit var btnSaveMood: Button
    private lateinit var rvMoodEntries: RecyclerView

    private lateinit var db: DatabaseHelper
    private lateinit var moodEntriesAdapter: MoodEntriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_tracking)

        spinnerMood = findViewById(R.id.spinnerMood)
        etDiaryEntry = findViewById(R.id.etDiaryEntry)
        btnSaveMood = findViewById(R.id.btnSaveMood)
        rvMoodEntries = findViewById(R.id.rvMoodEntries)

        db = DatabaseHelper(this)

        setupSpinner()
        setupRecyclerView()

        btnSaveMood.setOnClickListener {
            saveMood()
        }

        loadMoodEntries()
    }

    private fun setupSpinner() {
        val moods = resources.getStringArray(R.array.mood_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, moods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMood.adapter = adapter
    }

    private fun setupRecyclerView() {
        moodEntriesAdapter = MoodEntriesAdapter()
        rvMoodEntries.apply {
            adapter = moodEntriesAdapter
            layoutManager = LinearLayoutManager(this@MoodTrackingActivity)
        }
    }

    private fun saveMood() {
        val selectedMood = spinnerMood.selectedItem.toString()
        val diaryEntry = etDiaryEntry.text.toString().trim()

        if (diaryEntry.isNotEmpty()) {
            val moodEntry = MoodEntry(
                P_id = "default_user", // Replace with actual user ID if available
                mood = selectedMood,
                diary = diaryEntry,
                timestamp = System.currentTimeMillis()
            )

            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) {
                    db.insertMoodEntry(moodEntry)
                }
                if (result != -1L) {
                    Toast.makeText(this@MoodTrackingActivity, "Mood entry saved", Toast.LENGTH_SHORT).show()
                    etDiaryEntry.text.clear()
                    loadMoodEntries()
                } else {
                    Toast.makeText(this@MoodTrackingActivity, "Error saving mood entry", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please write a diary entry", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMoodEntries() {
        lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) {
                db.getAllMoodEntries()
            }
            moodEntriesAdapter.submitList(entries)
        }
    }
}