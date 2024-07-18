package com.example.wellness_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivityTherapist : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapistloginactivity)

        dbHelper = DatabaseHelper.getInstance(this) // Using singleton instance
        sessionManager = SessionManager(this)

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)


        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            validateCredentials(email, password)
        }


    }

    private fun validateCredentials(email: String, password: String) {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_THERAPISTS} WHERE ${DatabaseHelper.COLUMN_T_EMAIL} = ? AND ${DatabaseHelper.COLUMN_T_PASSWORD} = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        if (cursor != null && cursor.moveToFirst()) {
            // Ensure cursor is not null and move to first row
            val therapistIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_ID)
            val usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_USERNAME)
            val nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_NAME)
            val contactIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_CONTACT)
            val passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_PASSWORD)
            val ninIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_T_NIN) // Add this line for T_NIN

            if (therapistIdIndex != -1 && usernameIndex != -1 && nameIndex != -1 && contactIndex != -1 && passwordIndex != -1) {
                // Columns exist, retrieve data
                val therapistId = cursor.getInt(therapistIdIndex)
                val username = cursor.getString(usernameIndex)
                val name = cursor.getString(nameIndex)
                val storedPassword = cursor.getString(passwordIndex)
                val contact = cursor.getString(contactIndex)
                val nin = cursor.getString(ninIndex) // Retrieve T_NIN value

                if (password == storedPassword) {
                    sessionManager.createLoginSession("therapist", therapistId, username, name, email, storedPassword, contact, nin)

                    val intent = Intent(this@LoginActivityTherapist, TherapistHomeActivity::class.java)
                    startActivity(intent)
                    finish() // Finish LoginActivity to prevent going back to it when pressing back button from TherapistHomeActivity
                } else {
                    // Passwords do not match
                    Toast.makeText(this@LoginActivityTherapist, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
                }
            } else {
                // Columns do not exist or indexes are invalid
                Toast.makeText(this@LoginActivityTherapist, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
            }
        } else {
            // Cursor is null or empty
            Toast.makeText(this@LoginActivityTherapist, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
    }
}



