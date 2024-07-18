package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

class UserTherapyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usertherapyactivity)

        findViewById<Button>(R.id.button_makeappointment).apply {
            ViewCompat.setTooltipText(this, "This section is for making appointments")
            setOnClickListener {
                val intent = Intent(this@UserTherapyActivity, PatientsMakeAppointments::class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.button_veiwappointments).apply {
            ViewCompat.setTooltipText(this, "This is used to check and confirm appointments")
            setOnClickListener {
                val intent = Intent(this@UserTherapyActivity, PatientViewAppointmentsActivity::class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.button_reveiws).apply {
            ViewCompat.setTooltipText(this, "This is used to give therapists reviews")
            setOnClickListener {
                val intent = Intent(this@UserTherapyActivity, ReviewsActivity::class.java)
                startActivity(intent)
            }

        }

        findViewById<Button>(R.id.button_chooseD).apply {
            ViewCompat.setTooltipText(this, "This can help you pick a personal therapist")
            setOnClickListener {
                val intent = Intent(this@UserTherapyActivity, ChooseActivity::class.java)
                startActivity(intent)
            }
        }



        findViewById<Button>(R.id.button_viewTherapists).apply {
            ViewCompat.setTooltipText(this, "This section is to view details of all therapists in different categories")
            setOnClickListener {
                val intent = Intent(this@UserTherapyActivity, ViewTherapistsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
