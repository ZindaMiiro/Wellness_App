// AdminHomeActivity.kt
package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adminhomeactivity)

        // Button to add to Departments
        val addDepartmentButton = findViewById<Button>(R.id.addDepartmentButton)
        addDepartmentButton.setOnClickListener {
            val intent = Intent(this, AddDepartmentActivity::class.java)
            startActivity(intent)
        }

        // Button to add to Therapists
        val addTherapistButton = findViewById<Button>(R.id.addTherapistButton)
        addTherapistButton.setOnClickListener {
            val intent = Intent(this, RegisterTherapistActivity::class.java)
            startActivity(intent)
        }

        // Button to add to Patients
        val addPatientButton = findViewById<Button>(R.id.addPatientButton)
        addPatientButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}


