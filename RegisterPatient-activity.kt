package com.example.wellness_app
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RegistrationActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var editText_Username: EditText
    private lateinit var editText_Name: EditText
    private lateinit var editText_NIN: EditText
    private lateinit var editText_Email: EditText
    private lateinit var editText_Password: EditText
    private lateinit var editText_Contact: EditText
    private lateinit var button_Register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientregisteractivity)

        dbHelper = DatabaseHelper(this)

        editText_Username = findViewById(R.id.editText_Username)
        editText_Name = findViewById(R.id.editText_Name)
        editText_NIN = findViewById(R.id.editText_NIN)
        editText_Email = findViewById(R.id.editText_Email)
        editText_Password = findViewById(R.id.editText_Password)
        editText_Contact = findViewById(R.id.editText_Contact)
        button_Register = findViewById(R.id.button_Register)

        button_Register.setOnClickListener {
            val username = editText_Username.text.toString().trim()
            val name = editText_Name.text.toString().trim()
            val nin = editText_NIN.text.toString().trim()
            val email = editText_Email.text.toString().trim()
            val password = editText_Password.text.toString().trim()
            val contact = editText_Contact.text.toString().trim()

            if (validateInput(username, name, nin, email, password, contact)) {
                val patient = Patient(
                    P_id = 0, // P_id is null because it's auto-generated
                    P_Username = username,
                    P_name = name,
                    P_nin = nin,
                    P_email = email,
                    P_password = password,
                    P_contact = contact
                )

                val result = dbHelper.insertPatient(patient)
                if (result != -1L) {
                    Toast.makeText(this, "Patient registered successfully!", Toast.LENGTH_SHORT).show()
                    // Optionally, navigate to another activity or perform other actions upon successful registration
                } else {
                    Toast.makeText(this, "Failed to register patient", Toast.LENGTH_SHORT).show()
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
