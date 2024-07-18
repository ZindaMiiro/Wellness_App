package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BioActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var ninEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bioactivity)

        sessionManager = SessionManager(this)

        usernameEditText = findViewById(R.id.bio_username)
        passwordEditText = findViewById(R.id.bio_password)
        nameEditText = findViewById(R.id.bio_name)
        emailEditText = findViewById(R.id.bio_email)
        contactEditText = findViewById(R.id.bio_contact)
        ninEditText = findViewById(R.id.bio_nin)
        updateButton = findViewById(R.id.bio_update_button)
        logoutButton = findViewById(R.id.logout_button)

        // Load current user data
        loadUserData()

        updateButton.setOnClickListener {
            updateUserData()
        }

        logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadUserData() {
        usernameEditText.setText(sessionManager.getUsername())
        passwordEditText.setText(sessionManager.getPassword())
        nameEditText.setText(sessionManager.getName())
        emailEditText.setText(sessionManager.getEmail())
        contactEditText.setText(sessionManager.getContact())
        ninEditText.setText(sessionManager.getNIN())
    }

    private fun updateUserData() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val contact = contactEditText.text.toString()
        val nin = ninEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && contact.isNotEmpty() && nin.isNotEmpty()) {
            sessionManager.updateUserSession(username, password, name, email, contact, nin)
            Toast.makeText(this, getString(R.string.bio_update_success), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.bio_update_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun logoutUser() {
        sessionManager.logoutSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this@BioActivity, WellnessAppMainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
