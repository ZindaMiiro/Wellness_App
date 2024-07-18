package com.example.wellness_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TherapyWorkActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapisttherapywork)

        // Initialize buttons
        val buttonViewAppointments = findViewById<Button>(R.id.button_view_appointments)
       val choicecar =findViewById<Button>(R.id.button_departmentchoice)
        val buttonViewviewcategories = findViewById<Button>(R.id.button_add_to_knowledge_page)
        val categories =findViewById<Button>(R.id.button_view_Departments)
        val pat =findViewById<Button>(R.id.button_view_personal_patients)
        // Set button listeners
        buttonViewAppointments.setOnClickListener {
            val intent = Intent(this@TherapyWorkActivity, TherapistViewAppointmentsActivity::class.java)
            startActivity(intent)  // Handle view appointments
        }

        choicecar.setOnClickListener{
            val intent = Intent(this@TherapyWorkActivity, TherapistChooseDepartmentActivity::class.java)
            startActivity(intent)

        }
        buttonViewviewcategories.setOnClickListener{
            val intent = Intent(this@TherapyWorkActivity, AddKnowledgeActivity::class.java)
            startActivity(intent)
        }

        categories.setOnClickListener{
            val intent = Intent(this@TherapyWorkActivity, DepartmentsActivity::class.java)
            startActivity(intent)
        }
        pat.setOnClickListener{
            val intent = Intent(this@TherapyWorkActivity, TherapistViewPatientsActivity::class.java)
            startActivity(intent)

        }
    }
}

