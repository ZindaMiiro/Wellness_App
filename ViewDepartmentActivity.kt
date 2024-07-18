package com.example.wellness_app

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DepartmentsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.veiwdepartments)

        dbHelper = DatabaseHelper.getInstance(this)

        // Fetch department names from the database
        val departmentNames = dbHelper.getAllDepartments()

        // Display department names in a table layout
        val tableLayout = findViewById<TableLayout>(R.id.table_layout_departments)

        // Add header row for Categories
        val headerRow = TableRow(this)
        val headerTextView = TextView(this)
        headerTextView.text = "Categories"
        headerTextView.setPadding(10, 10, 10, 10)
        headerRow.addView(headerTextView)
        tableLayout.addView(headerRow)

        // Add rows for each department name
        for (D_name in departmentNames) {
            val row = TableRow(this)

            val textView = TextView(this)
            textView.text = D_name.toString()
            textView.setPadding(10, 10, 10, 10)

            row.addView(textView)
            tableLayout.addView(row)
        }
    }
}
