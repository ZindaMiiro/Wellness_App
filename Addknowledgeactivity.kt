package com.example.wellness_app

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddKnowledgeActivity : AppCompatActivity() {
    private lateinit var departmentLayout: LinearLayout
    private lateinit var contentEditText: EditText
    private lateinit var addButton: Button
    private var selectedDepartmentName: String? = null
    private val departmentCheckboxes = mutableMapOf<String, CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addknowledgeactivity)

        departmentLayout = findViewById(R.id.department_layout)
        contentEditText = findViewById(R.id.content_edit_text)
        addButton = findViewById(R.id.add_button)

        loadDepartments()

        addButton.setOnClickListener {
            val content = contentEditText.text.toString()
            if (selectedDepartmentName != null && content.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val departmentId = withContext(Dispatchers.IO) {
                        DatabaseHelper.getInstance(applicationContext).getDepartmentIdByName(selectedDepartmentName!!)
                    }
                    if (departmentId != null) {
                        addKnowledgeEntry(departmentId, content)
                    } else {
                        Toast.makeText(this@AddKnowledgeActivity, "Invalid department selected", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please select a department and enter content", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDepartments() {
        CoroutineScope(Dispatchers.Main).launch {
            val departments = withContext(Dispatchers.IO) {
                DatabaseHelper.getInstance(applicationContext).getAllDepartments()
            }

            departments.forEach { department ->
                val checkBox = CheckBox(this@AddKnowledgeActivity).apply {
                    text = department.D_name
                    setOnClickListener { onCheckboxClicked(department.D_name) }
                }
                departmentCheckboxes[department.D_name] = checkBox
                departmentLayout.addView(checkBox)
            }
        }
    }

    private fun onCheckboxClicked(departmentName: String) {
        selectedDepartmentName = if (selectedDepartmentName == departmentName) {
            null
        } else {
            departmentName
        }
        updateCheckboxStates()
    }

    private fun updateCheckboxStates() {
        departmentCheckboxes.forEach { (name, checkBox) ->
            checkBox.isChecked = name == selectedDepartmentName
        }
    }

    private fun addKnowledgeEntry(dId: Int, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseHelper.getInstance(applicationContext).addKnowledgeEntry(dId, content)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AddKnowledgeActivity, "Knowledge entry added successfully", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
