package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivityPatient : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity)

        dbHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            validateCredentials(email, password)
        }

        registerButton.setOnClickListener {
            // Navigate to RegistrationActivity
            val intent = Intent(this@LoginActivityPatient, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateCredentials(email: String, password: String) {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_PATIENTS} WHERE ${DatabaseHelper.COLUMN_P_EMAIL} = ? AND ${DatabaseHelper.COLUMN_P_PASSWORD} = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        if (cursor != null && cursor.moveToFirst()) {
            // Ensure cursor is not null and move to first row
            val patientIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_ID)
            val usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_USERNAME)
            val nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_NAME)
            val contactIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_CONTACT)
            val passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_PASSWORD)
            val ninIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_P_NIN)

            if (patientIdIndex != -1 && usernameIndex != -1 && nameIndex != -1 && contactIndex != -1 && passwordIndex != -1&& ninIndex!= -1) {
                // Columns exist, retrieve data
                val patientId = cursor.getInt(patientIdIndex)
                val username = cursor.getString(usernameIndex)
                val name = cursor.getString(nameIndex)
                val storedPassword = cursor.getString(passwordIndex)
                val contact = cursor.getString(contactIndex)
                val nin = cursor.getString(ninIndex)


                if (password == storedPassword) {
                    sessionManager.createLoginSession("patient", patientId, username, name, email, storedPassword ,contact , nin)

                    val intent = Intent(this@LoginActivityPatient, HomePageActivity::class.java)
                    startActivity(intent)
                    finish() // Finish LoginActivity to prevent going back to it when pressing back button from HomePageActivity
                } else {
                    // Passwords do not match
                    Toast.makeText(this@LoginActivityPatient, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
                }
            } else {
                // Columns do not exist or indexes are invalid
                Toast.makeText(this@LoginActivityPatient, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
            }
        } else {
            // Cursor is null or empty
            Toast.makeText(this@LoginActivityPatient, getString(R.string.Invaliddetails), Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
    }
}






