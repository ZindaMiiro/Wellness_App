package com.example.wellness_app

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.content.Intent
import androidx.core.content.ContextCompat
import android.view.Gravity




class PatientViewAppointmentsActivity : AppCompatActivity() {

    private lateinit var tableLayoutAppointments: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientviewappointments)

        tableLayoutAppointments = findViewById(R.id.tableLayoutAppointments)

        // Retrieve patient ID from session manager
        val sessionManager = SessionManager(this)
        val patientId = sessionManager.getUserId()

        if (patientId != -1) {
            val appointments = DatabaseHelper.getInstance(this).getAppointmentsForPatientFromToday(patientId)

            populateAppointmentsTable(appointments)
        } else {
            // Handle scenario where patient ID is not valid
            // For example, redirect to login screen or display an error
            Toast.makeText(this, "Invalid patient ID. Please log in again.", Toast.LENGTH_SHORT).show()
            sessionManager.logoutSession()
            startActivity(Intent(this, LoginActivityPatient::class.java))
            finish()
        }
    }

    private fun populateAppointmentsTable(appointments: List<Appointment>) {
        for (appointment in appointments) {
            val row = TableRow(this)

            val dateTextView = TextView(this)
            dateTextView.apply {
                text = appointment.A_date
                setTextColor(ContextCompat.getColor(this@PatientViewAppointmentsActivity, android.R.color.white))
                textSize = 16f
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            val timeTextView = TextView(this)
            timeTextView.apply {
                text = appointment.A_time
                setTextColor(ContextCompat.getColor(this@PatientViewAppointmentsActivity, android.R.color.white))
                textSize = 16f
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            val therapistTextView = TextView(this)
            therapistTextView.apply {
                text = appointment.therapistName
                setTextColor(ContextCompat.getColor(this@PatientViewAppointmentsActivity, android.R.color.white))
                textSize = 16f
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            val patientNameTextView = TextView(this)
            patientNameTextView.apply {
                text = appointment.patientName
                setTextColor(ContextCompat.getColor(this@PatientViewAppointmentsActivity, android.R.color.white))
                textSize = 16f
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            row.addView(dateTextView)
            row.addView(timeTextView)
            row.addView(therapistTextView)
            row.addView(patientNameTextView)

            tableLayoutAppointments.addView(row)
        }
    }

}



