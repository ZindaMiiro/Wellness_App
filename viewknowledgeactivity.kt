package com.example.wellness_app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewKnowledgeActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var departmentSpinner: Spinner
    private lateinit var searchButton: Button
    private lateinit var knowledgeContent: TextView
    private lateinit var departmentCheckboxesLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewknowledgeactivity)

        dbHelper = DatabaseHelper.getInstance(this)
        departmentSpinner = findViewById(R.id.department_spinner)
        searchButton = findViewById(R.id.search_button)
        knowledgeContent = findViewById(R.id.knowledge_content)
        departmentCheckboxesLayout = findViewById(R.id.department_checkboxes_layout)

        // Load department names into spinner and checkboxes
        loadDepartmentNames()

        // Handle search button click
        searchButton.setOnClickListener {
            lifecycleScope.launch {
                val selectedDepartmentName = departmentSpinner.selectedItem.toString()
                val selectedDepartmentId = dbHelper.getDepartmentIdByName(selectedDepartmentName) ?: -1
                if (selectedDepartmentId != -1) {
                    searchKnowledgeByDepartment(selectedDepartmentId)
                } else {
                    knowledgeContent.text = getString(R.string.no_department_selected)
                }
            }
        }
    }

    private fun loadDepartmentNames() {
        lifecycleScope.launch {
            val departments = dbHelper.getAllDepartments()
            val departmentNames = departments.map { it.D_name }
            val adapter = ArrayAdapter<String>(this@ViewKnowledgeActivity, android.R.layout.simple_spinner_item, departmentNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            departmentSpinner.adapter = adapter

            // Add checkboxes dynamically for each department
            for (department in departments) {
                val checkBox = CheckBox(this@ViewKnowledgeActivity)
                checkBox.text = department.D_name
                checkBox.setTextColor(ContextCompat.getColor(this@ViewKnowledgeActivity, android.R.color.holo_orange_light))
                departmentCheckboxesLayout.addView(checkBox)
            }
        }
    }

    private suspend fun searchKnowledgeByDepartment(departmentId: Int) {
        val knowledge = dbHelper.getKnowledgeByDepartmentId(departmentId)
        withContext(Dispatchers.Main) {
            knowledgeContent.text = knowledge ?: getString(R.string.no_knowledge_found)
        }
    }
}
