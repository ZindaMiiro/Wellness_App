package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView

class WellnessAppMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wellnessappmainactivity)

        // Find views by ID
        val buttonPatient: ImageButton = findViewById(R.id.button_patient)
        val buttonTherapist: ImageButton = findViewById(R.id.button_therapist)
        val adminLoginLink: TextView = findViewById(R.id.admin_login_link)

        // Set click listeners for buttons
        buttonPatient.setOnClickListener {
            // Navigate to Patient Activity
            val intent = Intent(this@WellnessAppMainActivity, LoginActivityPatient::class.java)
            startActivity(intent)
        }

        buttonTherapist.setOnClickListener {
            // Navigate to Therapist Activity
            val intent = Intent(this@WellnessAppMainActivity, LoginActivityTherapist::class.java)
            startActivity(intent)
        }

        adminLoginLink.setOnClickListener {
            // Navigate to Admin Login Activity
            val intent = Intent(this@WellnessAppMainActivity, AdminLoginActivity::class.java)
            startActivity(intent)
        }
    }
}
