package com.example.wellness_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TherapistViewAppointmentsActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var appointmentsText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapistviewappointments)

        sessionManager = SessionManager(this)
        dbHelper = DatabaseHelper.getInstance(this)
        appointmentsText = findViewById(R.id.appointments_text)

        fetchAppointmentsForTherapist()
    }

    private fun fetchAppointmentsForTherapist() {
        val therapistId = sessionManager.getUserId() // Assuming getUserId returns therapist ID

        CoroutineScope(Dispatchers.IO).launch {
            val appointments = dbHelper.getAppointmentsByTherapistId(therapistId)

            val appointmentsInfo = StringBuilder()
            for (appointment in appointments) {
                appointmentsInfo.append("Patient Name: ${appointment.patientName}\n")
                appointmentsInfo.append("Date: ${appointment.date}\n")
                appointmentsInfo.append("Time: ${appointment.time}\n\n")
            }

            withContext(Dispatchers.Main) {
                appointmentsText.text = appointmentsInfo.toString()
            }
        }
    }
}


