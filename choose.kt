package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose)

        val buttonChooseTherapist: Button = findViewById(R.id.buttonChooseTherapist)
        val buttonCheckTherapistDepartment: Button = findViewById(R.id.buttonCheckTherapistDepartment)

        buttonChooseTherapist.setOnClickListener {
            // Handle buttonChooseTherapist click event
            val intent = Intent(this@ChooseActivity, PatientChooseTherapist::class.java)
            startActivity(intent)
        }

        buttonCheckTherapistDepartment.setOnClickListener {
            // Handle buttonCheckTherapistDepartment click event
            val intent = Intent(this@ChooseActivity, ViewTherapistCategoriesActivity::class.java)
            startActivity(intent)
        }
    }
}


