package com.example.wellness_app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PatientInReview : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var editTextPatientUsername: EditText
    private lateinit var linearLayoutTherapists: LinearLayout
    private lateinit var editTextReview: EditText
    private lateinit var buttonSubmitReview: Button

    private lateinit var therapists: List<Therapist>
    private var selectedTherapistId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientreview)

        dbHelper = DatabaseHelper(this)

        editTextPatientUsername = findViewById(R.id.editTextPatientUsername)
        linearLayoutTherapists = findViewById(R.id.linearLayout_Therapists)
        editTextReview = findViewById(R.id.editText_Review)
        buttonSubmitReview = findViewById(R.id.buttonSubmit_Review)

        loadTherapists()

        buttonSubmitReview.setOnClickListener {
            val patientUsername = editTextPatientUsername.text.toString().trim()
            val reviewText = editTextReview.text.toString().trim()

            if (validateInput(patientUsername, reviewText)) {
                lifecycleScope.launch {
                    val patient = dbHelper.getPatientByUsername(patientUsername)
                    if (patient != null && selectedTherapistId != null) {
                        val review = Reviews(
                            R_id = 0,
                            R_P_id = patient.P_id,
                            R_T_id = selectedTherapistId!!,
                            reviewText = reviewText
                        )

                        val result = dbHelper.insertReview(review)
                        if (result != -1L) {
                            Toast.makeText(this@PatientInReview, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                            clearInputFields()
                        } else {
                            Toast.makeText(this@PatientInReview, "Failed to submit review", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@PatientInReview, "Patient or Therapist not found", Toast.LENGTH_SHORT).show()
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
                val checkBox = CheckBox(this@PatientInReview).apply {
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

    private fun validateInput(patientUsername: String, reviewText: String): Boolean {
        return patientUsername.isNotEmpty() && reviewText.isNotEmpty() && selectedTherapistId != null
    }

    private fun clearInputFields() {
        editTextPatientUsername.text.clear()
        editTextReview.text.clear()
        selectedTherapistId = null
        for (i in 0 until linearLayoutTherapists.childCount) {
            val checkBox = linearLayoutTherapists.getChildAt(i) as CheckBox
            checkBox.isChecked = false
        }
    }
}



