package com.example.wellness_app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PatientsMakeAppointments : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var editTextPatientName: EditText
    private lateinit var linearLayoutTherapists: LinearLayout
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var buttonAddAppointment: Button

    private lateinit var therapists: List<Therapist>
    private var selectedTherapistId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientmakeappointment)

        dbHelper = DatabaseHelper(this)

        editTextPatientName = findViewById(R.id.editText_PatientName)
        linearLayoutTherapists = findViewById(R.id.linearLayout_Therapists)
        editTextDate = findViewById(R.id.editText_Date)
        editTextTime = findViewById(R.id.editText_Time)
        buttonAddAppointment = findViewById(R.id.button_AddAppointment)

        loadTherapists()

        buttonAddAppointment.setOnClickListener {
            val patientName = editTextPatientName.text.toString().trim()
            val date = editTextDate.text.toString().trim()
            val time = editTextTime.text.toString().trim()

            if (validateInput(patientName, date, time)) {
                lifecycleScope.launch {
                    val patient = dbHelper.getPatientByName(patientName)
                    if (patient != null && selectedTherapistId != null) {
                        val appointmentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
                        if (appointmentDate != null && appointmentDate.before(Date())) {
                            Toast.makeText(this@PatientsMakeAppointments, "Appointment date cannot be in the past", Toast.LENGTH_SHORT).show()
                        } else {
                            val appointment = Appointment(
                                A_id = 0,
                                A_P_id = patient.P_id,
                                A_T_id = selectedTherapistId!!,
                                A_date = date,
                                A_time = time
                            )

                            val result = dbHelper.insertAppointment(appointment)
                            if (result != -1L) {
                                Toast.makeText(this@PatientsMakeAppointments, "Appointment added successfully!", Toast.LENGTH_SHORT).show()
                                clearInputFields()
                            } else {
                                Toast.makeText(this@PatientsMakeAppointments, "Failed to add appointment", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@PatientsMakeAppointments, "Patient or Therapist not found", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTherapists() {
        lifecycleScope.launch {
            therapists = dbHelper.getAllTherapists()
            linearLayoutTherapists.removeAllViews()
            therapists.forEach { therapist ->
                val checkBox = CheckBox(this@PatientsMakeAppointments).apply {
                    text = therapist.T_name
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            selectedTherapistId = therapist.T_id
                            uncheckOtherTherapists(this)
                        }
                    }
                }
                linearLayoutTherapists.addView(checkBox)
            }
        }
    }

    private fun uncheckOtherTherapists(selectedCheckBox: CheckBox) {
        for (i in 0 until linearLayoutTherapists.childCount) {
            val checkBox = linearLayoutTherapists.getChildAt(i) as CheckBox
            if (checkBox != selectedCheckBox) {
                checkBox.isChecked = false
            }
        }
    }

    private fun validateInput(patientName: String, date: String, time: String): Boolean {
        return patientName.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && selectedTherapistId != null
    }

    private fun clearInputFields() {
        editTextPatientName.text.clear()
        editTextDate.text.clear()
        editTextTime.text.clear()
        selectedTherapistId = null
        for (i in 0 until linearLayoutTherapists.childCount) {
            val checkBox = linearLayoutTherapists.getChildAt(i) as CheckBox
            checkBox.isChecked = false
        }
    }
}
