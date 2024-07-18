package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

class HomePageActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeactivity)

        sessionManager = SessionManager(this)

        // Set welcome message with username from session
        val welcomeText: TextView = findViewById(R.id.welcome_text)
        val username = sessionManager.getUsername()
        welcomeText.text = "Welcome, $username!"

        // Set tooltips and hover listeners for buttons
        setButtonWithTooltip(findViewById(R.id.button_therapy_work), "This section is for Therapy Work", UserTherapyActivity::class.java)
        setButtonWithTooltip(findViewById(R.id.button_knowledge), "This section contains knowledge on various topics", ViewKnowledgeActivity::class.java)
        setButtonWithTooltip(findViewById(R.id.button_diary), "This section is for Diary entries", MoodTrackingActivity::class.java)

        // Set tooltips and click listener for help button
        findViewById<Button>(R.id.button_help).apply {
            ViewCompat.setTooltipText(this, "Click here for Help")
            setOnHoverListener { v, event ->
                showToast("Guided Meditation")
                true
            }
            setOnClickListener {
                val intent = Intent(this@HomePageActivity, GuidedMeditationActivity::class.java)
                startActivity(intent)
            }
        }

        // Set click listeners for navigation buttons
        findViewById<ImageButton>(R.id.button_home).setOnClickListener {
            // Navigate to Home page (or perform desired action)
            navigateToPage(HomePageActivity::class.java)
        }

        findViewById<ImageButton>(R.id.button_about_us).setOnClickListener {
            // Navigate to About Us page (or perform desired action)
            navigateToPage(PeerSupportActivity::class.java)
        }

        findViewById<ImageButton>(R.id.button_bio_data).setOnClickListener {
            // Navigate to Bio Data page (or perform desired action)
            navigateToPage(BioActivity::class.java)
        }
    }

    private fun setButtonWithTooltip(button: Button, tooltipText: String, activityClass: Class<*>) {
        ViewCompat.setTooltipText(button, tooltipText)
        button.setOnHoverListener { v, event ->
            showToast(tooltipText)
            true
        }
        button.setOnClickListener {
            navigateToPage(activityClass)
        }
    }

    private fun navigateToPage(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        // Optionally, you can finish() this activity if you don't want it in the back stack
        // finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
