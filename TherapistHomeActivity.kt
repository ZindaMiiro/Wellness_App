package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TherapistHomeActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terapisthomeactivity)

        sessionManager = SessionManager(this)

        // Display therapist's welcome message
        val therapistUsername = sessionManager.getUsername()
        val welcomeText: TextView = findViewById(R.id.welcome_text)
        welcomeText.text = "Welcome, $therapistUsername!"

        findViewById<Button>(R.id.button_therapy_work).setOnClickListener {
            val intent = Intent(this@TherapistHomeActivity, TherapyWorkActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_knowledge).setOnClickListener {
            val intent = Intent(this@TherapistHomeActivity, AddKnowledgeActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_viewtherapists).setOnClickListener {
            val intent = Intent(this@TherapistHomeActivity, ViewTherapistsActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.button_home).setOnClickListener {
            // Navigate to Home page
            navigateToPage(HomePageActivity::class.java)
        }

        findViewById<ImageButton>(R.id.button_about_us).setOnClickListener {
            // Navigate to About Us page
            navigateToPage(PeerSupportActivity::class.java)
        }

        findViewById<ImageButton>(R.id.button_bio_data).setOnClickListener {
            // Navigate to Bio Data page
            navigateToPage(BioActivity::class.java)
        }
    }

    private fun navigateToPage(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}


