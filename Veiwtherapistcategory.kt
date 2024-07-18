package com.example.wellness_app

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewTherapistCategoriesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.veiwtherapistcategory)

        dbHelper = DatabaseHelper.getInstance(this)
        tableLayout = findViewById(R.id.tableLayout_TherapistCategories)

        loadTherapistCategories()
    }

    private fun loadTherapistCategories() {
        lifecycleScope.launch {
            val therapistCategories = withContext(Dispatchers.IO) {
                dbHelper.getTherapistCategoriesWithDetails()
            }

            for (category in therapistCategories) {
                val tableRow = TableRow(this@ViewTherapistCategoriesActivity)
                tableRow.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )

                val therapistNameTextView = TextView(this@ViewTherapistCategoriesActivity).apply {
                    text = category.therapistName
                    setTextColor(resources.getColor(android.R.color.black))
                    setPadding(8, 8, 8, 8)
                }

                val departmentNameTextView = TextView(this@ViewTherapistCategoriesActivity).apply {
                    text = category.departmentName
                    setTextColor(resources.getColor(android.R.color.black))
                    setPadding(8, 8, 8, 8)
                }

                tableRow.addView(therapistNameTextView)
                tableRow.addView(departmentNameTextView)

                tableLayout.addView(tableRow)
            }
        }
    }
}
