package com.example.wellness_app

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.ArrayAdapter

class TherapistChooseDepartmentActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var departmentSpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapistchoosedepartment)

        dbHelper = DatabaseHelper.getInstance(this)
        departmentSpinner = findViewById(R.id.spinner_departments)
        addButton = findViewById(R.id.addButton)
        sessionManager = SessionManager(this)

        setupSpinner()

        addButton.setOnClickListener {
            val therapistId = sessionManager.getUserId()

            if (therapistId != -1) {
                lifecycleScope.launch(Dispatchers.Main) {
                    val selectedDepartments = getSelectedDepartments()

                    if (selectedDepartments.isNotEmpty()) {
                        dbHelper.insertTherapistCategories(therapistId, selectedDepartments)
                        Toast.makeText(applicationContext, "Therapist categories added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Please select departments", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Error: Therapist ID not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner() {
        lifecycleScope.launch(Dispatchers.Main) {
            val departments = dbHelper.getAllDepartments()
            val departmentNames = departments.map { it.D_name }
            val adapter = ArrayAdapter(this@TherapistChooseDepartmentActivity, android.R.layout.simple_spinner_item, departmentNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            departmentSpinner.adapter = adapter
        }
    }

    private suspend fun getSelectedDepartments(): List<Int> {
        val selectedDepartments = mutableListOf<Int>()
        val selectedDepartmentName = departmentSpinner.selectedItem.toString()

        val selectedDepartmentId = withContext(Dispatchers.IO) {
            dbHelper.getDepartmentIdByName(selectedDepartmentName)
        }
        selectedDepartmentId?.let {
            selectedDepartments.add(it)
        }

        return selectedDepartments
    }
}
