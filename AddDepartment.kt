package com.example.wellness_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddDepartmentActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var editTextDepartmentName: EditText
    private lateinit var buttonAddDepartment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adddepartmentactivity)

        dbHelper = DatabaseHelper(this)

        editTextDepartmentName = findViewById(R.id.editText_DepartmentName)
        buttonAddDepartment = findViewById(R.id.button_AddDepartment)

        buttonAddDepartment.setOnClickListener {
            val departmentName = editTextDepartmentName.text.toString().trim()

            if (validateInput(departmentName)) {
                val department = Department(D_id = 0, D_name = departmentName)

                lifecycleScope.launch {
                    val result = dbHelper.insertDepartment(department)
                    if (result != -1L) {
                        Toast.makeText(this@AddDepartmentActivity, "Department added successfully!", Toast.LENGTH_SHORT).show()
                        // Optionally, navigate to another activity or perform other actions upon successful department addition
                    } else {
                        Toast.makeText(this@AddDepartmentActivity, "Failed to add department", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(departmentName: String): Boolean {
        return departmentName.isNotEmpty()
    }
}
