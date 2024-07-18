package com.example.wellness_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TherapistViewPatientsActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var patientsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapistviewpatients)

        sessionManager = SessionManager(this)
        dbHelper = DatabaseHelper.getInstance(this)
        patientsText = findViewById(R.id.patients_text)

        fetchPatientsForTherapist()
    }

    private fun fetchPatientsForTherapist() {
        val therapistId = sessionManager.getUserId() // Assuming getUserId returns therapist ID

        CoroutineScope(Dispatchers.IO).launch {
            val patients = dbHelper.getPatientsForTherapist(therapistId)

            val patientsInfo = StringBuilder()
            for (patient in patients) {
                patientsInfo.append("Patient Name: ${patient.P_name}\n")
            }

            withContext(Dispatchers.Main) {
                patientsText.text = patientsInfo.toString()
            }
        }
    }
}
