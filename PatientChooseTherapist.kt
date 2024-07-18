package com.example.wellness_app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PatientChooseTherapist : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var dbHelper: DatabaseHelper

    private lateinit var spinnerTherapists: Spinner
    private lateinit var btnChooseTherapist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientchoosetherapist)

        sessionManager = SessionManager(this)
        dbHelper = DatabaseHelper.getInstance(this)

        spinnerTherapists = findViewById(R.id.spinner_therapists)
        btnChooseTherapist = findViewById(R.id.btn_choose_therapist)

        loadTherapists()

        btnChooseTherapist.setOnClickListener {
            chooseTherapist()
        }
    }

    private fun loadTherapists() {
        lifecycleScope.launch {
            val therapists = dbHelper.getAllTherapists()
            val therapistNames = therapists.map { it.T_name }
            val adapter = ArrayAdapter(this@PatientChooseTherapist, android.R.layout.simple_spinner_item, therapistNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTherapists.adapter = adapter
        }
    }

    private fun chooseTherapist() {
        val therapistName = spinnerTherapists.selectedItem.toString()

        lifecycleScope.launch {
            try {
                val therapist = dbHelper.getTherapistByName(therapistName)

                if (therapist != null) {
                    val patientId = sessionManager.getUserId()  // Assuming getUserId returns P_id
                    val therapistId = therapist.T_id

                    if (patientId != -1) {
                        dbHelper.addPatientTherapistRelation(patientId, therapistId)
                        showToast("Therapist chosen successfully")
                        // Optionally, you can finish() this activity or navigate elsewhere
                    } else {
                        showToast("Error: Patient ID not found")
                    }
                } else {
                    showToast("Error: Therapist not found")
                }
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@PatientChooseTherapist, message, Toast.LENGTH_SHORT).show()
    }
}
