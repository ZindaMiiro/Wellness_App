package com.example.wellness_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterTherapistActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var editTextUsername: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextNIN: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextContact: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapistregisteractivity)

        dbHelper = DatabaseHelper(this)

        editTextUsername = findViewById(R.id.editText_Username)
        editTextName = findViewById(R.id.editText_Name)
        editTextNIN = findViewById(R.id.editText_NIN)
        editTextEmail = findViewById(R.id.editText_Email)
        editTextPassword = findViewById(R.id.editText_Password)
        editTextContact = findViewById(R.id.editText_Contact)
        buttonRegister = findViewById(R.id.button_Register)

        buttonRegister.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val nin = editTextNIN.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val contact = editTextContact.text.toString().trim()

            if (validateInput(username, name, nin, email, password, contact)) {
                val therapist = Therapist(
                    T_id = 0, // T_id is null because it's auto-generated
                    T_username = username,
                    T_name = name,
                    T_nin = nin,
                    T_email = email,
                    T_password = password,
                    T_contact = contact
                )

                val result = dbHelper.insertTherapist(therapist)
                if (result != -1L) {
                    Toast.makeText(this, "Therapist registered successfully!", Toast.LENGTH_SHORT).show()
                    // Optionally, navigate to another activity or perform other actions upon successful registration
                } else {
                    Toast.makeText(this, "Failed to register therapist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(
        username: String,
        name: String,
        nin: String,
        email: String,
        password: String,
        contact: String
    ): Boolean {
        return username.isNotEmpty() && name.isNotEmpty() && nin.isNotEmpty() &&
                email.isNotEmpty() && password.isNotEmpty() && contact.isNotEmpty()
    }
}
